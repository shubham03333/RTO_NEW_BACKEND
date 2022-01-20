package com.sunbeam.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbeam.entities.LearningLicence;

public interface LlDao extends JpaRepository<LearningLicence,Integer> {
	LearningLicence findByid(int id);

}
