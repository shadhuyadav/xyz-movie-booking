package com.xyz.booking.booking.service.impl;

import com.xyz.booking.booking.dto.*;
import com.xyz.booking.booking.model.Booking;
import com.xyz.booking.booking.model.BookingStatus;
import com.xyz.booking.booking.model.Payment;
import com.xyz.booking.booking.repository.BookingRepository;
import com.xyz.booking.booking.repository.PaymentRepository;
import com.xyz.booking.booking.service.BookingService;
import com.xyz.booking.booking.service.PaymentGateway;
import com.xyz.booking.common.exception.BusinessException;
import com.xyz.booking.common.service.IdempotencyService;
import com.xyz.booking.pricing.*;
import com.xyz.booking.seat.dto.SeatLockRequest;
import com.xyz.booking.seat.service.SeatService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private static final String IDEM_API = "BOOK_TICKET";

    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final SeatService seatService;
    private final PricingService pricingService;
    private final IdempotencyService idempotencyService;

    public BookingServiceImpl(
            BookingRepository bookingRepository,
            PaymentRepository paymentRepository,
            SeatService seatService,
            PricingService pricingService,
            IdempotencyService idempotencyService
    ) {
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
        this.seatService = seatService;
        this.pricingService = pricingService;
        this.idempotencyService = idempotencyService;
    }


    @Override
    @Transactional
    public BookingResponse bookTickets(
            String idempotencyKey,
            BookingRequest request
    ) {


        return idempotencyService
                .getResponse(idempotencyKey, IDEM_API, BookingResponse.class)
                .orElseGet(() -> {

                    BookingResponse response = processBooking(request);


                    idempotencyService.saveResponse(
                            idempotencyKey,
                            IDEM_API,
                            response
                    );

                    return response;
                });
    }


    @Override
    public BulkBookingResponse bulkBook(BulkBookingRequest request) {

        if (request.bookings() == null || request.bookings().isEmpty()) {
            throw new BusinessException("No bookings provided");
        }

        List<BulkBookingResponse.BookingResult> results =
                request.bookings()
                        .stream()
                        .map(bookingRequest -> {
                            try {
                                BookingResponse response =
                                        processBooking(bookingRequest);

                                return new BulkBookingResponse.BookingResult(
                                        response.bookingId(),
                                        response.status(),
                                        "SUCCESS"
                                );
                            } catch (Exception ex) {
                                return new BulkBookingResponse.BookingResult(
                                        null,
                                        "FAILED",
                                        ex.getMessage()
                                );
                            }
                        })
                        .toList();

        return new BulkBookingResponse(results);
    }

    @Override
    @Transactional
    public void cancelBooking(Long bookingId) {

        Booking booking =
                bookingRepository.findById(bookingId)
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Booking not found: " + bookingId
                                )
                        );

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            return; // idempotent cancel
        }

        booking.cancel();
    }


    private BookingResponse processBooking(BookingRequest request) {

        validate(request);

        try {

            seatService.lockSeats(
                    new SeatLockRequest(
                            request.showId(),
                            request.userId(),
                            request.seatIds()
                    )
            );


            PricingResponse pricing =
                    pricingService.calculate(
                            new PricingRequest(
                                    request.seatIds().size(),
                                    250,
                                    LocalTime.of(15, 0)
                            )
                    );


            Booking booking =
                    new Booking(
                            request.userId(),
                            request.showId(),
                            pricing.finalAmount()
                    );

            bookingRepository.save(booking);


            Payment payment =
                    new Payment(
                            booking.getId(),
                            pricing.finalAmount()
                    );

            paymentRepository.save(payment);


            boolean paymentSuccess =
                    PaymentGateway.charge(pricing.finalAmount());

            if (!paymentSuccess) {
                payment.markFailed();
                paymentRepository.save(payment);

                seatService.releaseSeats(
                        request.showId(),
                        request.seatIds()
                );

                booking.fail();
                throw new BusinessException("Payment failed");
            }


            payment.markSuccess();
            paymentRepository.save(payment);


            seatService.confirmSeats(
                    request.showId(),
                    request.seatIds()
            );

            booking.confirm();

            return new BookingResponse(
                    booking.getId(),
                    booking.getStatus().name(),
                    pricing.finalAmount()
            );

        } catch (Exception ex) {


            seatService.releaseSeats(
                    request.showId(),
                    request.seatIds()
            );

            throw ex;
        }
    }


    private void validate(BookingRequest request) {

        if (request.userId() == null)
            throw new BusinessException("UserId is required");

        if (request.showId() == null)
            throw new BusinessException("ShowId is required");

        if (request.seatIds() == null || request.seatIds().isEmpty())
            throw new BusinessException("SeatIds are required");
    }
}
