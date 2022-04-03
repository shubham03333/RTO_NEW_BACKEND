package com.sunbeam.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunbeam.daos.VehicleTransferDao;
import com.sunbeam.entities.DrivingLicence;
import com.sunbeam.entities.VehicleRegistration;
import com.sunbeam.entities.VehicleTransfer;

@Transactional
@Service
public class VehicleTransferServiceImpl {
	@Autowired
	VehicleTransferDao transferDao;
	
	VehicleRegistration vehicleRegistration;
	
	@Autowired
	VehicleRegistrationServiceImpl vehicleRegistrationServiceImpl; 
	
	public VehicleTransfer findById(int transfer_id)
	{
		VehicleTransfer vehicleTransfer= transferDao.findByid(transfer_id);
		return vehicleTransfer;
	}
	
	public List<VehicleTransfer> findAllVehicleTransfer(){
		List<VehicleTransfer>vehicleTransferList=transferDao.findAll();
		return vehicleTransferList;
	}
	
	public VehicleTransfer saveVehicleTransfer(VehicleTransfer vehicleTransfer) {
		VehicleTransfer newVehicleTransfer=findById(vehicleTransfer.getId());
		if(newVehicleTransfer != null)
			return null;
		VehicleTransfer vehicleTransfer1=transferDao.save(vehicleTransfer);
		return vehicleTransfer;
	}

	public void updateVTransfer(String status,int id) {
		transferDao.updateVTransfer(status, id);
	}
	
	public VehicleRegistration findVRegistrationByReg_no(String reg_no) {
		
		vehicleRegistration=vehicleRegistrationServiceImpl.findByregistration_no(reg_no);
		return vehicleRegistration;
	}
	

	public VehicleTransfer findVTBYUserId(int user_id) {

		int vtId = transferDao.findIdByUserId(user_id);

		VehicleTransfer vt =transferDao.findByid(vtId);

		return vt;
	}

	
}
