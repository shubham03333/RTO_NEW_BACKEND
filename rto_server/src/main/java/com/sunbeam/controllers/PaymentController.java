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

import com.sunbeam.daos.DlDao;
import com.sunbeam.daos.LlDao;
import com.sunbeam.daos.PaymentDao;
import com.sunbeam.daos.PermitDao;
import com.sunbeam.daos.PucDao;
import com.sunbeam.daos.VehicleRegistrationDao;
import com.sunbeam.daos.VehicleTransferDao;
import com.sunbeam.dtos.Response;
import com.sunbeam.entities.DrivingLicence;
import com.sunbeam.entities.LearningLicence;
import com.sunbeam.entities.Payment;
import com.sunbeam.entities.Permit;
import com.sunbeam.entities.Puc;
import com.sunbeam.entities.User;
import com.sunbeam.entities.VehicleRegistration;
import com.sunbeam.entities.VehicleTransfer;
import com.sunbeam.services.DlServiceImpl;
import com.sunbeam.services.LlServiceImpl;
import com.sunbeam.services.PaymentServiceImpl;
import com.sunbeam.services.PermitServiceImpl;
import com.sunbeam.services.PucServiceImpl;
import com.sunbeam.services.UserServiceImpl;
import com.sunbeam.services.VehicleRegistrationServiceImpl;
import com.sunbeam.services.VehicleTransferServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/payment/")
public class PaymentController {

	@Autowired
	private PaymentDao paymentDao;

	@Autowired
	private PaymentServiceImpl paymentServiceImpl;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private VehicleRegistrationServiceImpl vehicleRegistrationServiceImpl;

	@Autowired
	private VehicleTransferServiceImpl vehicleTransferServiceImpl;

	@Autowired
	private PucServiceImpl pucServiceImpl;

	@Autowired
	private PermitServiceImpl permitServiceImpl;

	@Autowired
	private DlServiceImpl dlServiceImpl;

	@Autowired
	private LlServiceImpl llServiceImpl;
	
	@Autowired
	private LlDao llDao;
	
	@Autowired
	private DlDao dlDao;

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
		User user = userServiceImpl.findUserFromdbById(payment.getUser_id());
		payment.setUser(user);
		System.out.println(payment.getUser());
//				.orElseThrow(() -> new ResourceNotFoundException("Payment not exist with payment_refno :" + id));
		return ResponseEntity.ok(payment);
	}

	@GetMapping("/byUserId/{id}")
	public ResponseEntity<Payment> getPaymentById1(@PathVariable int id) {

		Payment p = paymentServiceImpl.findLLBYUserId(id);
		System.out.println(p);
		if (p == null) {
			return (ResponseEntity<Payment>) Response.error("Payment not exist with user_id  :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("DrivingLicence not exist with temp_ll_id :" + id));
		return ResponseEntity.ok(p);
	}

	@PostMapping("/add_payment")
	public ResponseEntity<?> addPayment(@RequestBody Payment pay) {

		Payment payment = paymentServiceImpl.savePayment(pay);

		// #############################################
		int userId = 0;

		userId = payment.getUser_id();
		
		try {
			
		

		if (payment.getPayment_for().equals("RC")) {

			List<VehicleRegistration> vr = new ArrayList<>();
			vr = vehicleRegistrationServiceImpl.findAllVehicleReg();
			for (VehicleRegistration vehiclereg : vr) {

				if (vehiclereg.getUser_id() == userId && vehiclereg.getId() == payment.getVehicleRegistration_id()) {
					vehiclereg.setPayment(payment);
					System.out.println(vehiclereg.getPayment());
					vehiclereg.setTransaction_id(payment.getId());

				}
			}

		} else if (payment.getPayment_for().equals("Vehicle transfer")) {

			List<VehicleTransfer> vt = new ArrayList<>();
			vt = vehicleTransferServiceImpl.findAllVehicleTransfer();
			for (VehicleTransfer vehicleTran : vt) {

				if (vehicleTran.getUser_id() == userId
						&& vehicleTran.getRegistration_id() == payment.getVehicleRegistration_id()) {
					vehicleTran.setPayment(payment);
					System.out.println(vehicleTran.getPayment());

				}
			}
		} else if (payment.getPayment_for().equals("puc")) {
			List<Puc> pu = new ArrayList<>();
			pu = pucServiceImpl.findAllPucs();
			for (Puc puc : pu) {

				if (puc.getUser_id() == userId && puc.getRegistration_id() == payment.getVehicleRegistration_id()) {
					puc.setPayment(payment);
					System.out.println(puc.getPayment());

				}
			}
		} else if (payment.getPayment_for().equals("Permit")) {
			List<Permit> per = new ArrayList<>();
			per = permitServiceImpl.findAllPermits();
			for (Permit permit : per) {

				if (permit.getUser_id() == userId
						&& permit.getRegistration_id() == payment.getVehicleRegistration_id()) {
					permit.setPayment(payment);
					System.out.println(permit.getPayment());

				}
			}
		} else if (payment.getPayment_for().equals("DL")) {
//			List<DrivingLicence> dl = new ArrayList<>();
//			dl = dlServiceImpl.findAllDls();
//			for (DrivingLicence drivingl : dl) {
//
//				if (drivingl.getUser_id() == userId && drivingl.getL_category() == payment.getLcategory()) {
//					drivingl.setPayment(payment);
//					System.out.println(drivingl.getPayment());
//
//				}
//			}
			
			
			DrivingLicence dl= dlDao.findById(payment.getdrivingLicence_id());
			dl.setPayment(payment);
			
		} else if (payment.getPayment_for().equals("LL")) {
//			List<LearningLicence> ll = new ArrayList<>();
//			ll = llServiceImpl.findAllLls();
//			for (LearningLicence learnli : ll) {
//
//				if (learnli.getUser_id() == userId && learnli.getL_category() == payment.getLcategory()) {
//					learnli.setPayment(payment);
//					System.out.println(learnli.getPayment());
//
//				}
//			}
			
			LearningLicence ll= llDao.findByid(payment.getLearningLicence_id());
			ll.setPayment(payment);
		}

		// #############################################

		payment.setUser(userServiceImpl.findUserFromdbById(payment.getUser_id()));
//		System.out.println(result);
		if (payment == null)
			return Response.error("Payment is empty");
		return Response.success(payment);
		} catch (Exception e) {
			return (ResponseEntity<Payment>) Response.error("Something went wrong please check your payment for option!");
		
		}
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

//		payment.setPayment_id(paymentDetails.getPayment_id());
		payment.setPayment_mode(paymentDetails.getPayment_mode());
		payment.setAmount(paymentDetails.getAmount());
		payment.setPayment_date(paymentDetails.getPayment_date());

		Payment updatedPayment = paymentServiceImpl.savePayment(payment);
		return ResponseEntity.ok(updatedPayment);
	}

}
