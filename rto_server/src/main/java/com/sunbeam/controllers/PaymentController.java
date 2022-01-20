package com.sunbeam.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunbeam.daos.PaymentDao;
import com.sunbeam.dtos.Response;
import com.sunbeam.entities.Payment;
import com.sunbeam.services.PaymentServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/payment/")
public class PaymentController {

	@Autowired
	private PaymentDao paymentDao;

	@Autowired
	private PaymentServiceImpl paymentServiceImpl;

	@GetMapping("/search")
	public ResponseEntity<?> findPayment() {
		List<Payment> result = new ArrayList<>();
		result = paymentServiceImpl.findAllPayments();
		return Response.success(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Payment> getPaymentById(@PathVariable int id) {
		Payment payment = paymentServiceImpl.findBYId(id);
		if (payment == null) {
			return (ResponseEntity<Payment>) Response.error("Payment not exist with payment_refno :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("Payment not exist with payment_refno :" + id));
		return ResponseEntity.ok(payment);
	}

	@PostMapping("/add_payment")
	public ResponseEntity<?> addPayment(@RequestBody Payment pay) {
		Payment payment = paymentServiceImpl.savePayment(pay);
//		System.out.println(result);
		if (payment == null)
			return Response.error("Payment is empty");
		return Response.success(payment);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deletePayment(@PathVariable int id) {
		Payment payment = paymentServiceImpl.findBYId(id);
		if (payment == null) {
			return (ResponseEntity<Map<String, Boolean>>) Response.error("Payment not exist with payment_refno :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("Payment not exist with payment_refno :" + id));

		paymentDao.delete(payment);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Payment> updatePayment(@PathVariable int id, @RequestBody Payment paymentDetails) {
		Payment payment = paymentServiceImpl.findBYId(id);
		if (payment == null) {
			return (ResponseEntity<Payment>) Response.error("Payment not exist with payment_refno :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));

		payment.setPayment_id(paymentDetails.getPayment_id());
		payment.setPayment_mode(paymentDetails.getPayment_mode());
		payment.setAmount(paymentDetails.getAmount());
		payment.setPayment_date(paymentDetails.getPayment_date());

		Payment updatedPayment = paymentServiceImpl.savePayment(payment);
		return ResponseEntity.ok(updatedPayment);
	}

}
