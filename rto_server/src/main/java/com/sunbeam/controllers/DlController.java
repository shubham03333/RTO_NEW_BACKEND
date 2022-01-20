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
import com.sunbeam.dtos.Response;
import com.sunbeam.entities.DrivingLicence;
import com.sunbeam.services.DlServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dl/")
public class DlController {

	@Autowired
	private DlDao dlDao;

	@Autowired
	private DlServiceImpl dlServiceImpl;

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

	@PostMapping("/add_dl")
	public ResponseEntity<?> addRc(@RequestBody DrivingLicence drivingLicence) {
		DrivingLicence dl = dlServiceImpl.saveDl(drivingLicence);
//		System.out.println(result);
		if (dl == null)
			return Response.error("DrivingLicence is empty");
		return Response.success(dl);
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
	public ResponseEntity<DrivingLicence> updateUser(@PathVariable int id, @RequestBody DrivingLicence dlDetails) {
		DrivingLicence dl = dlServiceImpl.findBYId(id);
		if (dl == null) {
			return (ResponseEntity<DrivingLicence>) Response.error("DrivingLicence not exist with dl_id :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));

		dl.setDl_no(dlDetails.getDl_no());
		dl.setDl_issue_date(dlDetails.getDl_issue_date());
		dl.setDl_expiry_date(dlDetails.getDl_expiry_date());

		DrivingLicence updatedDl = dlServiceImpl.saveDl(dl);
		return ResponseEntity.ok(updatedDl);
	}
}
