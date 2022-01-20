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

import com.sunbeam.daos.LlDao;
import com.sunbeam.dtos.Response;
import com.sunbeam.entities.LearningLicence;
import com.sunbeam.services.LlServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ll/")
public class LlController {

	@Autowired
	private LlDao llDao;

	@Autowired
	private LlServiceImpl llServiceImpl;

	@GetMapping("/search")
	public ResponseEntity<?> findLl() {
		List<LearningLicence> result = new ArrayList<>();
		result = llServiceImpl.findAllLls();
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
	public ResponseEntity<LearningLicence> updateUser(@PathVariable int id, @RequestBody LearningLicence llDetails) {
		LearningLicence ll = llServiceImpl.findBYId(id);
		if (ll == null) {
			return (ResponseEntity<LearningLicence>) Response.error("LearningLicence not exist with temp_ll_id :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));

		ll.setTemp_LL_no(llDetails.getTemp_LL_no());
		ll.setRto(llDetails.getRto());
		ll.setIssue_date(llDetails.getIssue_date());
		ll.setExpiry_date(llDetails.getExpiry_date());
		ll.setL_category(llDetails.getL_category());

		LearningLicence updatedLl = llServiceImpl.saveLl(ll);
		return ResponseEntity.ok(updatedLl);
	}

}
