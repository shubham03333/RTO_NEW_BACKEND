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

import com.sunbeam.daos.VehicleTransferDao;
import com.sunbeam.daos.VehicleTransferDao;
import com.sunbeam.dtos.Response;
import com.sunbeam.entities.VehicleTransfer;
import com.sunbeam.entities.VehicleTransfer;
import com.sunbeam.services.VehicleTransferServiceImpl;
import com.sunbeam.services.VehicleTransferServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/vehicle_transfer/")
public class VehicleTransferController {
	@Autowired
	private VehicleTransferDao transferDao;
	
	@Autowired
	private VehicleTransferServiceImpl vehicleTransferServiceImpl;

	@GetMapping("/search")
	public ResponseEntity<?> findVehicletransfer() {
		List<VehicleTransfer> result = new ArrayList<>();
			result = vehicleTransferServiceImpl.findAllVehicleTransfer();
		return Response.success(result);
	}
	@GetMapping("/{id}")
	public ResponseEntity<VehicleTransfer> getVehicleTransferById(@PathVariable int id) {
		VehicleTransfer vehicleTransfer = vehicleTransferServiceImpl.findById(id);
				if(vehicleTransfer==null) {
					return (ResponseEntity<VehicleTransfer>) Response.error("Transfer not exist with id :"+id);
				}
//				.orElseThrow(() -> new ResourceNotFoundException("Transfer not exist with id :" + id));
		return ResponseEntity.ok(vehicleTransfer);
	}
	
	@PostMapping("/add_vehicleTransfer")
	public ResponseEntity<?> addRc(@RequestBody VehicleTransfer vehicleTransfer) {
		VehicleTransfer transfer = vehicleTransferServiceImpl.saveVehicleTransfer(vehicleTransfer);
//		System.out.println(result);
		if(transfer==null)
			return Response.error("vehicle_transfer  is not empty");
		return Response.success(transfer);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteVehicleTransfer(@PathVariable int id){
		VehicleTransfer  transfer= vehicleTransferServiceImpl.findById(id);
		if( transfer==null) {
			return (ResponseEntity<Map<String, Boolean>>) Response.error("Vehicle not  exist for transfer id :"+id);
		}
//				.orElseThrow(() -> new ResourceNotFoundException("Transfer not exist with id :" + id));
		
		transferDao.delete( transfer);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<VehicleTransfer> updateVehicleTransfer(@PathVariable int id, @RequestBody VehicleTransfer transferDetails){
		VehicleTransfer  transfer = vehicleTransferServiceImpl.findById(id);
		if( transfer==null) {
			return (ResponseEntity<VehicleTransfer>) Response.error("vehicle transfer not exist with id :"+id);
		}
		
		transfer.setTransfer_no(transferDetails.getTransfer_no());
		transfer.setNew_owner_aadhar(transferDetails.getNew_owner_aadhar());
		transfer.setNew_owner(transferDetails.getNew_owner());
		
		
		VehicleTransfer updatedTransfer = vehicleTransferServiceImpl.saveVehicleTransfer(transfer);
		return ResponseEntity.ok(updatedTransfer);
	}
}
