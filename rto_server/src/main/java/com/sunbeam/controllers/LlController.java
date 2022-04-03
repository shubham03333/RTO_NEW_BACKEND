package com.sunbeam.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import com.sunbeam.daos.LlDao;
import com.sunbeam.dtos.LearningLicenceDTO;
import com.sunbeam.dtos.Response;
import com.sunbeam.entities.LearningLicence;
import com.sunbeam.entities.User;
import com.sunbeam.services.EmailSenderServiceImpl;
import com.sunbeam.services.LlServiceImpl;
import com.sunbeam.services.UserServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ll/")
public class LlController {

	@Autowired
	private LlDao llDao;

	@Autowired
	private LlServiceImpl llServiceImpl;
	
	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private EmailSenderServiceImpl emailSenderService;
	

	
	@GetMapping("/search")
	public ResponseEntity<?> findLl() {
		List<LearningLicence> result = new ArrayList<>();
		result = llServiceImpl.findAllLls();
		
//		Collections.sort(result,Collections.reverseOrder());
		return Response.success(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<LearningLicence> getLearningLicenceById(@PathVariable int id) {
		LearningLicence ll = llServiceImpl.findBYId(id);
		if (ll == null) {
			return (ResponseEntity<LearningLicence>) Response.error("LearningLicence not exist with temp_ll_id :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("DrivingLicence not exist with temp_ll_id :" + id));
		return ResponseEntity.ok(ll);
	}
	
	@GetMapping("/byUserId1/{id}")
	public ResponseEntity<LearningLicence> getLearningLicenceByUserId(@PathVariable int id) {
		try {
			
			LearningLicence ll = llServiceImpl.findLLBYUserId(id);
			System.out.println(ll);
			if (ll == null) {
				return (ResponseEntity<LearningLicence>) Response.error("LearningLicence not exist with temp_ll_id :" + id);
			}
//					.orElseThrow(() -> new ResourceNotFoundException("DrivingLicence not exist with temp_ll_id :" + id));
			return ResponseEntity.ok(ll);
		

			
		} catch (Exception e) {
			return (ResponseEntity<LearningLicence>) Response.error("You have not applied for LL Yet !");
		}
		
	
	}

	// this is for count off LL in admin dashbord and for certificate
	@GetMapping("/byUserId/{id}")
	public ResponseEntity<LearningLicenceDTO> getLearningById1(@PathVariable int id) {
		
	
		LearningLicenceDTO learningLicence=new LearningLicenceDTO();
		
		System.out.println(llDao.pendingCountInLl());
		
		if(llDao.pendingCountInLl()!=null) {
			learningLicence.setPendingCount(llDao.pendingCountInLl());
		}

		return ResponseEntity.ok(learningLicence);
	}

	
	
	
	@PostMapping("/add_ll")
	public ResponseEntity<?> addRc(@RequestBody LearningLicence learningLicence) {
		LearningLicence ll = llServiceImpl.saveLl(learningLicence);
		
//		System.out.println(result);
		if (ll == null)
			return Response.error("LearningLicence is empty");
		return Response.success(ll);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteLl(@PathVariable int id) {
		LearningLicence ll = llServiceImpl.findBYId(id);
		if (ll == null) {
			return (ResponseEntity<Map<String, Boolean>>) Response
					.error("LearningLicence not exist with temp_ll_id :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("LearningLicence not exist with temp_ll_id :" + id));

		llDao.delete(ll);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<LearningLicence> updateLL(@PathVariable int id, @RequestBody LearningLicence llDetails) throws MessagingException {
		LearningLicence ll = llServiceImpl.findBYId(id);
		if (ll == null) {
			return (ResponseEntity<LearningLicence>) Response.error("LearningLicence not exist with temp_ll_id :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));
//
//		User user=userServiceImpl.findUserFromdbById(ll.getUser_id());
//			System.out.println(user);

		User user =userServiceImpl.findUserFromdbById(ll.getUser_id());
		ll.setUser(user);
		
		ll.setTempLLNo(llDetails.getTempLLNo());
//		ll.setRto(llDetails.getRto());
		ll.setIssue_date((llDetails.getIssue_date()));
		ll.setExpiry_date(llDetails.getExpiry_date());
		ll.setStatus(llDetails.getStatus());
		llServiceImpl.updateLl(ll.getTempLLNo(), ll.getIssue_date(), ll.getExpiry_date(), ll.getStatus(), ll.getId());
		System.out.println(ll.getTempLLNo());
		

if (ll.getStatus().equalsIgnoreCase("Approved")) {
			// if approved then sends the mail to the applicant
			emailSenderService.sendSimpleEmail(user.getEmail(), "Dear " + user.getName() + ",\n\n"
					+ "Congratulations, Your Learning Licence is Approved  .\n"
					+ "You can Check status of it from RTO MANAGEMENT WEBSITE  and also Your Learning Licence will be delevered On registerd address.\n"
					+ "\n" + "Warm Regards,\n" + "RTO Info Group,\n" + "\n" + "Thank You for Using our services",
					"Your Learning Licence is approved");
//					 emailSenderService.sendSimpleEmail("shubhamja3333@gmail.com", "This is the mail from Spring boot app", "spring email testing");
		}
		
//		System.out.println(llServiceImpl.findBytempLLNo(ll.getTempLLNo()));
//		LearningLicence updatedLl = llServiceImpl.saveLl(ll);
		return ResponseEntity.ok(ll);
	}
	
	
	@GetMapping("/byUserIdforcert/{id}")
	public ResponseEntity<LearningLicence> getLearningLicenceByUserIdforcerti(@PathVariable int id) {
		
		try {
			
			List<Integer> llids = llDao.findIdfrommulByUserId(id);
			
			for (Integer integer : llids) {
				
					id=integer;
			}
			
			System.out.println("llid "+id);
			
			LearningLicence ll = llServiceImpl.findBYId(id);

			return ResponseEntity.ok(ll);
			
		} catch (Exception e) {
			
			return (ResponseEntity<LearningLicence>) Response.error("You have not applied for LL Yet !");
			
		}

	}
	
	

}
