package com.sunbeam.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbeam.entities.DrivingLicence;

public interface DlDao extends JpaRepository<DrivingLicence,Integer> {
	DrivingLicence findById(int id);
}
