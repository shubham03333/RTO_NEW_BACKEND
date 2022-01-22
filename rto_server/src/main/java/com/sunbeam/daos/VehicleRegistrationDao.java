package com.sunbeam.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sunbeam.entities.VehicleRegistration;
@Repository
public interface VehicleRegistrationDao extends JpaRepository<VehicleRegistration,Integer> {
  VehicleRegistration findByid(int id);
  
  
  @Query ("select v.id from VehicleRegistration v WHERE v.registration_no = ?1")
  int findIdByregistration_no(String id);

}
