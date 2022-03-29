package com.sunbeam.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.sunbeam.daos.DlDao;
import com.sunbeam.entities.DrivingLicence;
import com.sunbeam.entities.LearningLicence;

@Transactional
@Service
public class DlServiceImpl {

	@Autowired
	DlDao dlDao;

	@Autowired
	private UserServiceImpl userServiceImpl;

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
		dl.setUser(userServiceImpl.findUserFromdbById(dl.getUser_id()));
		System.out.println(dl.getUser());
		DrivingLicence dl2 = dlDao.save(dl);
		return dl2;
	}

	public void updateDl(String tempLLNo, Date issue_date, Date expiry_date, String status, int id) {
		try {

			dlDao.updateDl(tempLLNo, issue_date, expiry_date, status, id);
		} catch (DataIntegrityViolationException e) {

		}
	}

	public Optional<DrivingLicence> findLLBYUserId(int user_id) {

		Integer dId = dlDao.findIdByUserId(user_id);
		
		Optional<DrivingLicence> dl=null;
		try {
			if(dId!=null)
			 dl = dlDao.findById(dId);
			if (dl == null)
				return null;
			return dl;
		} catch (Exception e) {
			
		}
		return dl;
	}

}
