package com.sunbeam.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbeam.entities.VehicleRegistration;
import com.sunbeam.entities.VehicleTransfer;

public interface VehicleTransferDao extends JpaRepository<VehicleTransfer, Integer>{
	VehicleTransfer findByid(int id);
}
