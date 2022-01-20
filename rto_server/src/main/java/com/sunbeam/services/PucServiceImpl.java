package com.sunbeam.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunbeam.daos.PucDao;
import com.sunbeam.entities.Puc;

@Transactional
@Service
public class PucServiceImpl {

	@Autowired
	PucDao pucDao;

	public Puc findBYId(int puc_id) {
		Puc puc = pucDao.findById(puc_id);
		return puc;
	}

	public List<Puc> findAllPucs() {
		List<Puc> pucList = pucDao.findAll();
		return pucList;
	}

	public Puc savePuc(Puc puc) {
		Puc newPuc = findBYId(puc.getId());
		if (newPuc != null)
			return null;
		Puc puc2 = pucDao.save(puc);
		return puc;
	}

}
