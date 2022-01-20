package com.sunbeam.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunbeam.daos.PaymentDao;
import com.sunbeam.entities.Payment;

@Transactional
@Service
public class PaymentServiceImpl {

	@Autowired
	PaymentDao paymentDao;

	public Payment findBYId(int payment_refno) {
		Payment payment = paymentDao.findById(payment_refno);
		return payment;
	}

	public List<Payment> findAllPayments() {
		List<Payment> paymentList = paymentDao.findAll();
		return paymentList;
	}

	public Payment savePayment(Payment payment) {
		Payment newPayment = findBYId(payment.getId());
//		Payment newPayment = paymentDao.save(payment);
//		if (payment == null)
//			return null;
		if (newPayment != null)
			return null;
		
		Payment payment2 = paymentDao.save(payment);
		return payment2;
		
	}

}
