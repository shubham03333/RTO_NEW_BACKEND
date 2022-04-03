package com.sunbeam.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunbeam.daos.ComplainDao;
import com.sunbeam.entities.Complain;
import com.sunbeam.entities.Payment;

@Transactional
@Service
public class ComplainServiceImpl {

	@Autowired
	ComplainDao complainDao;

	public Complain findBYId(int complain_id) {
		Complain complain = complainDao.findById(complain_id);
		return complain;
	}

	public List<Complain> findAllComplains() {
		List<Complain> complainList = complainDao.findAll();
		return complainList;
	}

	public Complain saveComplain(Complain complain) {
		Complain newComplain = findBYId(complain.getId());
		if (newComplain != null)
			return null;
		Complain complain2 = complainDao.save(complain);
		return complain;
	}

	public void updateComplain(String complain_no,int id)
	{
		complainDao.updateComplain(complain_no, id);
	}
	
	
	public Complain findComplainBYUserId(int user_id) {

		int cId = complainDao.findIdByUserId(user_id);

		Complain c =complainDao.findById(cId);
		return c;

	}
}
