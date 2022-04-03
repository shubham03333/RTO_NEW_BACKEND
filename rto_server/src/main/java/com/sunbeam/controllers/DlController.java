package com.sunbeam.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

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

import com.sunbeam.daos.DlDao;
import com.sunbeam.daos.LlDao;
import com.sunbeam.dtos.DrivingLicenceDTO;
import com.sunbeam.dtos.Response;
import com.sunbeam.entities.DrivingLicence;
import com.sunbeam.entities.LearningLicence;
import com.sunbeam.entities.Payment;
import com.sunbeam.entities.User;
import com.sunbeam.services.DlServiceImpl;
import com.sunbeam.services.EmailSenderServiceImpl;
import com.sunbeam.services.LlServiceImpl;
import com.sunbeam.services.UserServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dl/")
public class DlController {

	@Autowired
	private DlDao dlDao;

	@Autowired
	private DlServiceImpl dlServiceImpl;

	@Autowired
	private LlDao llDao;
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	private EmailSenderServiceImpl emailSenderService;
	
	@Autowired
	private LlServiceImpl llServiceImpl;
	
	
	@GetMapping("/search")
	public ResponseEntity<?> findDl() {
		List<DrivingLicence> result = new ArrayList<>();
		result = dlServiceImpl.findAllDls();
		return Response.success(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DrivingLicence> getDrivingLicenceById(@PathVariable int id) {
		DrivingLicence dl = dlServiceImpl.findBYId(id);

		if (dl == null) {
			return (ResponseEntity<DrivingLicence>) Response.error("DrivingLicence not exist with dl_id :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("DrivingLicence not exist with dl_id :" + id));
		return ResponseEntity.ok(dl);
	}
	
	
	@GetMapping("/byUserId1/{id}")
	public ResponseEntity<Optional<DrivingLicence>> getLearningLicenceByUserId(@PathVariable int id) {
		
		
		try {
			Optional<DrivingLicence> dl = dlServiceImpl.findDLBYUserId(id);
			

			System.out.println(dl);
			if (dl == null) {
				return (ResponseEntity<Optional<DrivingLicence>>) Response.error("LearningLicence not exist with temp_ll_id :" + id);
			}
//					.orElseThrow(() -> new ResourceNotFoundException("DrivingLicence not exist with temp_ll_id :" + id));
			return ResponseEntity.ok(dl);
		}catch(Exception e) {
			return (ResponseEntity<Optional<DrivingLicence>>) Response.error("LearningLicence not exist with");
		}
	
	}
	
	//############################################## UNDER TESTING ###############################################
	
	@GetMapping("/byUserId/{id}")
	public ResponseEntity<DrivingLicenceDTO> getDrivingLicenceById1(@PathVariable int id) {
		
	
//		DrivingLicence drivingLicence=new DrivingLicence();
		
		
		DrivingLicenceDTO drivingLicence=new DrivingLicenceDTO();
		
		System.out.println(dlDao.pendingCountInDl());
		
		drivingLicence.setCount(dlDao.dLCount());
		
		System.out.println(drivingLicence.getCount());
		
		if(dlDao.pendingCountInDl()!=null) {
			
			drivingLicence.setPendingCount(dlDao.pendingCountInDl());
		}

		return ResponseEntity.ok(drivingLicence);
	}

	//############################################## UNDER TESTING ###############################################
	
	
	
	
	
	@PostMapping("/add_dl")
	public ResponseEntity<?> addRc(@RequestBody DrivingLicence drivingLicence) {
		
		try {
			int llid=llDao.findIdByLLNo(drivingLicence.getTempLLNo());
			
			LearningLicence ll = llServiceImpl.findBYId(llid);
			
			if(ll != null) {
				
				DrivingLicence dl = dlServiceImpl.saveDl(drivingLicence);
				dl.setLearningLicence(ll);
//				System.out.println(result);
				if (dl == null)
					return Response.error("DrivingLicence is empty");
				
				return Response.success(dl);
			}
		} catch (Exception e) {
			
			return (ResponseEntity<Optional<DrivingLicence>>) Response.error("Please Apply for Learning Licnce first and then Apply for DL" );
			
		}
		return Response.error("DrivingLicence is empty") ;
		
		

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteDl(@PathVariable int id) {
		DrivingLicence dl = dlServiceImpl.findBYId(id);
		if (dl == null) {
			return (ResponseEntity<Map<String, Boolean>>) Response.error("DrivingLicence not exist with dl_id :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("DrivingLicence not exist with dl_id :" + id));

		dlDao.delete(dl);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<DrivingLicence> updateDL(@PathVariable int id, @RequestBody DrivingLicence dlDetails) throws MessagingException {
		DrivingLicence dl = dlServiceImpl.findBYId(id);
		if (dl == null) {
			return (ResponseEntity<DrivingLicence>) Response.error("DrivingLicence not exist with dl_id :" + id);
		}
		
//		User user=userServiceImpl.findUserFromdbById(dl.getUser_id());
//				.orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));
		User user =userServiceImpl.findUserFromdbById(dl.getUser_id());
		dl.setUser(user);
		dl.setDl_no(dlDetails.getDl_no());
		dl.setDl_issue_date(dlDetails.getDl_issue_date());
		dl.setDl_expiry_date(dlDetails.getDl_expiry_date());
		dl.setStatus(dlDetails.getStatus());
		
		dlServiceImpl.updateDl(dl.getDl_no(), dl.getDl_issue_date(),dl.getDl_expiry_date(),dl.getStatus(),dl.getId());
		

if (dl.getStatus().equalsIgnoreCase("Approved")) {
			// if approved then sends the mail to the applicant
			emailSenderService.sendSimpleEmail(user.getEmail(), "Dear " + user.getName() + ",\n\n"
					+ "Congratulations, Your Driving Licence is Approved  .\n"
					+ "You can Check status of it from RTO MANAGEMENT WEBSITE  and also Your Driving Licence will be Delivered On Your Registered Address .\n"
					+ "\n" + "Warm Regards,\n" + "RTO Info Group,\n" + "\n" + "Thank You for Using our services",
					"Your Driving Licence request is approved");
		}
	
		return ResponseEntity.ok(dl);
	}
	
	
	
	@GetMapping("/byUserIdforcert/{id}")
	public ResponseEntity<DrivingLicence> getDrivingLicenceByUserIdforcerti(@PathVariable int id) {
		try {
			
			List<Integer> dlids = dlDao.findIdfrommulByUserId(id);
//			System.out.println(ll);
			
			for (Integer integer : dlids) {
				
					id=integer;
			}
			
			System.out.println("dlid "+id);
			
			DrivingLicence dl = dlServiceImpl.findBYId(id);

			return ResponseEntity.ok(dl);
			
		} catch (Exception e) {
			return (ResponseEntity<DrivingLicence>) Response.error("You have not applied for LL Yet !");
		}
		
	
	}
}
