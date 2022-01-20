package com.sunbeam.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbeam.entities.Puc;

public interface PucDao extends JpaRepository<Puc,Integer> {
	Puc findById(int id);

}
