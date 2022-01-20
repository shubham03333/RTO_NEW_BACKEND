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

import com.sunbeam.daos.PermitDao;
import com.sunbeam.dtos.Response;
import com.sunbeam.entities.Permit;
import com.sunbeam.services.PermitServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/permit/")
public class PermitController {

	@Autowired
	private PermitDao permitDao;

	@Autowired
	private PermitServiceImpl permitServiceImpl;

	@GetMapping("/search")
	public ResponseEntity<?> findPermit() {
		List<Permit> result = new ArrayList<>();
		result = permitServiceImpl.findAllPermits();
		return Response.success(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Permit> getPermitById(@PathVariable int id) {
		Permit permit = permitServiceImpl.findBYId(id);
		if (permit == null) {
			return (ResponseEntity<Permit>) Response.error("Permit not exist with id :" + id);
		}
//		.orElseThrow(() -> new ResourceNotFoundException("Permit not exist with id :" + id));
		return ResponseEntity.ok(permit);
	}

	@PostMapping("/add_permit")
	public ResponseEntity<?> addPermit(@RequestBody Permit permit) {
		Permit permission = permitServiceImpl.savePermit(permit);
//		System.out.println(result);
		if (permission == null)
			return Response.error("permit not is empty");
		return Response.success(permission);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deletePermit(@PathVariable int id) {
		Permit permit = permitServiceImpl.findBYId(id);
		if (permit == null) {
			return (ResponseEntity<Map<String, Boolean>>) Response.error("Permit not exist with id :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("Permit not exist with id :" + id));

		permitDao.delete(permit);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Permit> updateUser(@PathVariable int id, @RequestBody Permit permission) {
		Permit permit = permitServiceImpl.findBYId(id);
		if (permit == null) {
			return (ResponseEntity<Permit>) Response.error("Permit not exist with id :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));

		permit.setPermit_no(permission.getPermit_no());
		permit.setFrom_state(permission.getFrom_state());
		permit.setTo_state(permission.getTo_state());
		permit.setFrom_date(permission.getFrom_date());
		permit.setTo_date(permission.getTo_date());
		Permit updatedPermit = permitServiceImpl.savePermit(permit);
		return ResponseEntity.ok(updatedPermit);
	}
}
