package com.sunbeam.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

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

import com.sunbeam.daos.PucDao;
import com.sunbeam.dtos.PUCDTO;
import com.sunbeam.dtos.Response;
import com.sunbeam.entities.Permit;
import com.sunbeam.entities.Puc;
import com.sunbeam.entities.User;
import com.sunbeam.entities.VehicleTransfer;
import com.sunbeam.services.EmailSenderServiceImpl;
import com.sunbeam.services.PucServiceImpl;
import com.sunbeam.services.UserServiceImpl;
import com.sunbeam.services.VehicleRegistrationServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/puc/")
public class PucController {

	@Autowired
	private PucDao pucDao;

	@Autowired
	private PucServiceImpl pucServiceImpl;

	@Autowired
	private VehicleRegistrationServiceImpl vehicleRegistrationServiceImpl;

	@Autowired
	private UserServiceImpl userServiceImpl;

	private Puc puc;

	@Autowired
	private EmailSenderServiceImpl emailSenderService;

	@GetMapping("/search")
	public ResponseEntity<?> findPuc() {
		List<Puc> result = new ArrayList<>();
		result = pucServiceImpl.findAllPucs();
		return Response.success(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Puc> getPucById(@PathVariable int id) {
		Puc puc = pucServiceImpl.findBYId(id);
		if (puc == null) {
			return (ResponseEntity<Puc>) Response.error("Puc not exist with puc_id :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("Puc not exist with puc_id :" + id));
		return ResponseEntity.ok(puc);
	}

	@GetMapping("/byUserId1/{id}")
	public ResponseEntity<Puc> getPucById11(@PathVariable int id) {

		Puc p = pucServiceImpl.findLLBYUserId(id);
	
		System.out.println(p);
		if (p == null) {
			return (ResponseEntity<Puc>) Response.error("Puc not exist with puc_user_id :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("DrivingLicence not exist with temp_ll_id :" + id));
		return ResponseEntity.ok(p);
	}


	//############################################## UNDER TESTING ###############################################
	
	@GetMapping("/byUserId/{id}")
	public ResponseEntity<PUCDTO> getPucById1(@PathVariable int id) {
		
	
		PUCDTO p=new PUCDTO();
		p.setCount(pucDao.pucCount());
		System.out.println(p.getCount());
		System.out.println(pucDao.pendingCountInPuc());
		
		if(pucDao.pendingCountInPuc()!=null) {
			p.setPendingCount(pucDao.pendingCountInPuc());
		}

		return ResponseEntity.ok(p);
	}

	//############################################## UNDER TESTING ###############################################
	
	
	@GetMapping("/rcNo/{RcNo}")
	public ResponseEntity<Puc> getPucStatusByRcNo(@PathVariable String RcNo) {
		try {
			int p = pucDao.findIdByregistration_no(RcNo);
			System.out.println(p);
			Puc preg = pucServiceImpl.findBYId(p);

			return ResponseEntity.ok(preg);
		} catch (Exception e) {
			return (ResponseEntity<Puc>) Response.error("RC not exist with registration number");
		}

	}
	
	@PostMapping("/add_puc")
	public ResponseEntity<?> addPuc(@RequestBody Puc pollutionControl) {

//		System.out.println("its shubham1 "+vehicleRegistrationServiceImpl.findByregistration_no(pollutionControl.getRegistration_no()));
//		System.out.println("its shubham2222 "+userServiceImpl.findByAadharNo(pollutionControl.getAadhar_no()));

//		System.out.println(registration);
		Puc puc = pucServiceImpl.savePuc(pollutionControl);

		puc.setVehicleRegistration(pucServiceImpl.findVRegistrationByRegId(pollutionControl.getRegistration_id()));
//		System.out.println(result);
		if (puc == null)
			return Response.error("Puc is empty");
		return Response.success(puc);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deletePuc(@PathVariable int id) {
		Puc puc = pucServiceImpl.findBYId(id);
		if (puc == null) {
			return (ResponseEntity<Map<String, Boolean>>) Response.error("Puc not exist with puc_id :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("Puc not exist with puc_id :" + id));

		pucDao.delete(puc);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Puc> updateUser(@PathVariable int id, @RequestBody Puc pucDetails) throws MessagingException {
		Puc puc = pucServiceImpl.findBYId(id);
		if (puc == null) {
			return (ResponseEntity<Puc>) Response.error("Puc not exist with puc_id :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));

		User user = userServiceImpl.findUserFromdbById(puc.getUser_id());
		System.out.println(user);
		puc.setPuc_no(pucDetails.getPuc_no());
//		puc.setFrom_date(pucDetails.getFrom_date());
//		puc.setTo_date(pucDetails.getTo_date());
//		puc.setCo2(pucDetails.getCo2());
//		puc.setHc(pucDetails.getHc());
//		puc.setPayment(pucDetails.getPayment());
		puc.setStatus(pucDetails.getStatus());

		pucServiceImpl.updatePuc(puc.getPuc_no(), puc.getStatus(), puc.getId());

		if (puc.getStatus().equalsIgnoreCase("Approved")) {
			// if approved then sends the mail to the applicant
			emailSenderService.sendSimpleEmail(user.getEmail(), "Dear " + user.getName() + ",\n\n"
					+ "Congratulations, Your PUC is Approved  .\n"
					+ "You can Check status of it from RTO MANAGEMENT WEBSITE  and also Your PUC Certificate will be Available on website.\n"
					+ "\n" + "Warm Regards,\n" + "RTO Info Group,\n" + "\n" + "Thank You for Using our services",
					"Your PUC request is approved");
//					 emailSenderService.sendSimpleEmail("shubhamja3333@gmail.com", "This is the mail from Spring boot app", "spring email testing");
		}
		return ResponseEntity.ok(puc);
	}
}
