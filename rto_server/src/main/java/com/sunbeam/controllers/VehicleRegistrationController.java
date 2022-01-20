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

import com.sunbeam.daos.VehicleRegistrationDao;
import com.sunbeam.dtos.Response;
import com.sunbeam.entities.VehicleRegistration;
import com.sunbeam.services.VehicleRegistrationServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rc/")
public class VehicleRegistrationController {
	
	@Autowired
	private VehicleRegistrationDao registrationDao;
	
	@Autowired
	private VehicleRegistrationServiceImpl registrationServiceImpl;

	@GetMapping("/search")
	public ResponseEntity<?> findVehicleRegistration() {
		List<VehicleRegistration> result = new ArrayList<>();
			result = registrationServiceImpl.findAllVehicleReg();
		return Response.success(result);
	}
	@GetMapping("/{id}")
	public ResponseEntity<VehicleRegistration> getVehicleRegById(@PathVariable int id) {
		VehicleRegistration vehicleReg = registrationServiceImpl.findBYId(id);
				if(vehicleReg==null) {
					return (ResponseEntity<VehicleRegistration>) Response.error("RC not exist with id :"+id);
				}
//				.orElseThrow(() -> new ResourceNotFoundException("Rc not exist with id :" + id));
		return ResponseEntity.ok(vehicleReg);
	}
	
	@PostMapping("/add_rc")
	public ResponseEntity<?> addRc(@RequestBody VehicleRegistration vehiclereg) {
		VehicleRegistration rc = registrationServiceImpl.saveVehicleReg(vehiclereg);
//		System.out.println(result);
		if(rc==null)
			return Response.error("rc not is empty");
		return Response.success(rc);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteRc(@PathVariable int id){
		VehicleRegistration rc = registrationServiceImpl.findBYId(id);
		if(rc==null) {
			return (ResponseEntity<Map<String, Boolean>>) Response.error("RC not exist with id :"+id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("Rc not exist with id :" + id));
		else{
			
		registrationServiceImpl.deleteRC(rc);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
		}
	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<VehicleRegistration> updateRC(@PathVariable int id, @RequestBody VehicleRegistration rcDetails){
		VehicleRegistration rc = registrationServiceImpl.findBYId(id);
		if(rc==null) {
			return (ResponseEntity<VehicleRegistration>) Response.error("RC not exist with id :"+id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));
		
		rc.setRegistration_no(rcDetails.getRegistration_no());
		rc.setOwner(rcDetails.getOwner());
		rc.setHypothecated_to(rcDetails.getHypothecated_to());
		rc.setInsurance_status(rcDetails.getInsurance_status());
		rc.setPuc_status(rcDetails.getPuc_status());
		
		VehicleRegistration updatedRC = registrationServiceImpl.saveVehicleReg(rc);
		return ResponseEntity.ok(updatedRC);
	}
	
	
	
	
}
