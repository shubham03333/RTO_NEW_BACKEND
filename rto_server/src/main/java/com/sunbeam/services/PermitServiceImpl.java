package com.sunbeam.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sunbeam.entities.Permit;

import com.sunbeam.daos.PermitDao;

@Transactional
@Service
public class PermitServiceImpl {

	@Autowired
	PermitDao permitDao;

	public Permit findBYId(int permit_id) {
		Permit permit = permitDao.findByid(permit_id);
		return permit;
	}

	public List<Permit> findAllPermits() {
		List<Permit> permitList = permitDao.findAll();
		return permitList;
	}

	public Permit savePermit(Permit permit) {
		Permit newPermit = findBYId(permit.getId());
		if (newPermit != null)
			return null;
		Permit permit2 = permitDao.save(permit);
		return permit;
	}

}
