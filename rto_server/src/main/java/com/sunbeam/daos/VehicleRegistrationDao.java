package com.sunbeam.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbeam.entities.VehicleRegistration;

public interface VehicleRegistrationDao extends JpaRepository<VehicleRegistration,Integer> {
  VehicleRegistration findByid(int id);

}
