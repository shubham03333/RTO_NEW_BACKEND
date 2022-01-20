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

import com.sunbeam.daos.PucDao;
import com.sunbeam.dtos.Response;
import com.sunbeam.entities.Puc;
import com.sunbeam.services.PucServiceImpl;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/puc/")
public class PucController {

	@Autowired
	private PucDao pucDao;

	@Autowired
	private PucServiceImpl pucServiceImpl;

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

	@PostMapping("/add_puc")
	public ResponseEntity<?> addPuc(@RequestBody Puc pollutionControl) {
		Puc puc = pucServiceImpl.savePuc(pollutionControl);
//		System.out.println(result);
		if (puc == null)
			return Response.error("Puc is empty");
		return Response.success(puc);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deletePuc(@PathVariable int id) {
		Puc puc = pucServiceImpl.findBYId(id);
		if (puc == null) {
			return (ResponseEntity<Map<String, Boolean>>) Response
					.error("Puc not exist with puc_id :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("Puc not exist with puc_id :" + id));

		pucDao.delete(puc);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Puc> updateUser(@PathVariable int id, @RequestBody Puc pucDetails) {
		Puc puc = pucServiceImpl.findBYId(id);
		if (puc == null) {
			return (ResponseEntity<Puc>) Response.error("Puc not exist with puc_id :" + id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));

		puc.setPuc_no(pucDetails.getPuc_no());
		puc.setFrom_date(pucDetails.getFrom_date());
		puc.setTo_date(pucDetails.getTo_date());
		puc.setCo2(pucDetails.getCo2());
		puc.setHc(pucDetails.getHc());
		puc.setUser(puc.getUser());
		puc.setVehicleRegistration(pucDetails.getVehicleRegistration());
		puc.setPayment(pucDetails.getPayment());

		Puc updatedPuc = pucServiceImpl.savePuc(puc);
		return ResponseEntity.ok(updatedPuc);
	}
}
