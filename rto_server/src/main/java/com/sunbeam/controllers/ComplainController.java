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

import com.sunbeam.daos.ComplainDao;
import com.sunbeam.daos.PucDao;
import com.sunbeam.dtos.Response;
import com.sunbeam.entities.Complain;
import com.sunbeam.entities.Payment;
import com.sunbeam.entities.Permit;
import com.sunbeam.entities.Puc;
import com.sunbeam.entities.VehicleRegistration;
import com.sunbeam.services.ComplainServiceImpl;
import com.sunbeam.services.PucServiceImpl;
import com.sunbeam.services.UserServiceImpl;
import com.sunbeam.services.VehicleRegistrationServiceImpl;
import com.sunbeam.services.VehicleTransferServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/complain/")
public class ComplainController {

	@Autowired
	ComplainDao complainDao;

	@Autowired
	ComplainServiceImpl complainServiceImpl;

	@Autowired
	private UserServiceImpl userServiceImpl;

	private Complain complain;

	@GetMapping("/search")
	public ResponseEntity<?> findComplain() {
		List<Complain> result = new ArrayList<>();
		result = complainServiceImpl.findAllComplains();
		return Response.success(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Complain> getComplainById(@PathVariable int id) {
		Complain complain = complainServiceImpl.findBYId(id);
		if (complain == null) {
			return (ResponseEntity<Complain>) Response.error("Complain not exist with complain_id :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("Complain not exist with complain_id :" + id));
		return ResponseEntity.ok(complain);
	}
	
	@GetMapping("/byUserId/{id}")
	public ResponseEntity<Complain> getComplainById1(@PathVariable int id) {
		
		Complain c = complainServiceImpl.findLLBYUserId(id);
		System.out.println(c);
		if (c == null) {
			return (ResponseEntity<Complain>) Response.error("Complain not exist with user_id :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("DrivingLicence not exist with temp_ll_id :" + id));
		return ResponseEntity.ok(c);
	}

	@PostMapping("/add_complain")
	public ResponseEntity<?> addComplain(@RequestBody Complain complain) {
		Complain complaint = complainServiceImpl.saveComplain(complain);
//		System.out.println(result);
		if (complaint == null)
			return Response.error("complain is empty");
		return Response.success(complaint);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteComplain(@PathVariable int id) {
		Complain complain = complainServiceImpl.findBYId(id);
		if (complain == null) {
			return (ResponseEntity<Map<String, Boolean>>) Response.error("Complain not exist with id :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("Complain not exist with id :" + id));

		complainDao.delete(complain);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Complain> updateComplain(@PathVariable int id, @RequestBody Complain complaint) {
		Complain complain = complainServiceImpl.findBYId(id);
		if (complain == null) {
			return (ResponseEntity<Complain>) Response.error("Complain not exist with id :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));

		complain.setComplain_no(complaint.getComplain_no());
//		complain.setDescription(complaint.getDescription());
//		Complain updatedComplain = complainServiceImpl.saveComplain(complain);
		complainServiceImpl.updateComplain(complain.getComplain_no(), complain.getId());
		return ResponseEntity.ok(complain);
	}

}
