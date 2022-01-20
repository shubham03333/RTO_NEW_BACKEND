package com.sunbeam.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunbeam.daos.DlDao;
import com.sunbeam.entities.DrivingLicence;

@Transactional
@Service
public class DlServiceImpl {

	@Autowired
	DlDao dlDao;

	public DrivingLicence findBYId(int dl_id) {
		DrivingLicence dl = dlDao.findById(dl_id);
		return dl;
	}

	public List<DrivingLicence> findAllDls() {
		List<DrivingLicence> dlList = dlDao.findAll();
		return dlList;
	}

	public DrivingLicence saveDl(DrivingLicence dl) {
		DrivingLicence newDl = findBYId(dl.getId());
		if (newDl != null)
			return null;
		DrivingLicence dl2 = dlDao.save(dl);
		return dl;
	}
}
