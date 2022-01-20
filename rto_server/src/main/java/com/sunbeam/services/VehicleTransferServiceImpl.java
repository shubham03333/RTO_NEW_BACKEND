package com.sunbeam.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunbeam.daos.VehicleTransferDao;
import com.sunbeam.entities.VehicleRegistration;
import com.sunbeam.entities.VehicleTransfer;

@Transactional
@Service
public class VehicleTransferServiceImpl {
	@Autowired
	VehicleTransferDao transferDao;
	
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

}
