package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        Reservation reservation = reservationRepository2.findById(reservationId).get();
        int bill = reservation.getNumberOfHours()*reservation.getSpot().getPricePerHour();
        if(amountSent<bill){
            throw new Exception("Insufficient Amount");
        }
        String paymentMode = mode.toUpperCase();
        Payment payment = new Payment();

        switch (paymentMode) {
            case "CASH":
                payment.setPaymentMode(PaymentMode.CASH);
                break;
            case "CARD":
                payment.setPaymentMode(PaymentMode.CARD);
                break;
            case "UPI":
                payment.setPaymentMode(PaymentMode.UPI);
                break;
            default:
                throw new Exception("Payment mode not detected");
        }

        payment.setPaymentCompleted(true);
        payment.setReservation(reservation);
        reservation.setPayment(payment);

       reservation.getSpot().setOccupied(true);

       reservationRepository2.save(reservation);
//        paymentRepository2.save(payment);
        return payment;
    }
}
