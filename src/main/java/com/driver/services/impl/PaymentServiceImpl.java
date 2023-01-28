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
    Set<String> modeSet = new HashSet<>() ;
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
//        cashh
        String paymentMode = mode.toUpperCase();
        if(modeSet.isEmpty()){
            modeSet.add("CASH");
            modeSet.add("CARD");
            modeSet.add("UPI");
        }
        if(!modeSet.contains(paymentMode)){
            throw new Exception("Payment mode not detected");
        }
        Payment payment =new Payment();
        if(paymentMode=="CASH"){
        payment.setPaymentMode(PaymentMode.CASH);
        } else if (paymentMode=="CARD") {
            payment.setPaymentMode(PaymentMode.CARD);
        }else{
            payment.setPaymentMode(PaymentMode.UPI);
        }
        payment.setPaymentCompleted(true);
        payment.setReservation(reservation);
        reservation.setPayment(payment);
//        occupid = true for that particular spot
       reservation.getSpot().setOccupied(true);

       reservationRepository2.save(reservation);
//        paymentRepository2.save(payment);
        return payment;
    }
}
