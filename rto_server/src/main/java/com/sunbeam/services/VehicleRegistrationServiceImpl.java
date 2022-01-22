package com.sunbeam.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunbeam.daos.VehicleRegistrationDao;
import com.sunbeam.entities.VehicleRegistration;

@Transactional
@Service
public class VehicleRegistrationServiceImpl {
	
	@Autowired
	VehicleRegistrationDao vehicleRegistrationDao;
	
	public VehicleRegistration findBYId(int reg_id) {
		VehicleRegistration vehicleRegistration=vehicleRegistrationDao.findByid(reg_id);
		return vehicleRegistration;
	}
	
	public List<VehicleRegistration> findAllVehicleReg(){
		List<VehicleRegistration>vehicleRegList=vehicleRegistrationDao.findAll();
		return vehicleRegList;
	}
	
	public VehicleRegistration saveVehicleReg(VehicleRegistration vehicleRegistration) {
		VehicleRegistration newVehicleReg=findBYId(vehicleRegistration.getId());
		if(newVehicleReg != null)
			return null;
		VehicleRegistration vehicleRegistration2=vehicleRegistrationDao.save(vehicleRegistration);
		return vehicleRegistration;
	}
	
	public void deleteRC(VehicleRegistration RC) {
		
		vehicleRegistrationDao.delete(RC);
	}
	
	public VehicleRegistration findByregistration_no(String reg_no) {
		
		int vehicle_id= vehicleRegistrationDao.findIdByregistration_no(reg_no);
		VehicleRegistration vehiclereg=vehicleRegistrationDao.findByid(vehicle_id);
		return vehiclereg;
	}
}
