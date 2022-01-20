package com.sunbeam.daos;


import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbeam.entities.Permit;

public interface PermitDao extends JpaRepository<Permit, Integer> {
	Permit findByid(int id);
}
