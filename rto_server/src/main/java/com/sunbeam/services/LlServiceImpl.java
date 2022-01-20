package com.sunbeam.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunbeam.daos.LlDao;
import com.sunbeam.entities.LearningLicence;

@Transactional
@Service
public class LlServiceImpl {

	@Autowired
	LlDao llDao;

	public LearningLicence findBYId(int temp_ll_id) {
		LearningLicence ll = llDao.findByid(temp_ll_id);
		return ll;
	}

	public List<LearningLicence> findAllLls() {
		List<LearningLicence> llList = llDao.findAll();
		return llList;
	}

	public LearningLicence saveLl(LearningLicence ll) {
		LearningLicence newLl = findBYId(ll.getId());
		if (newLl != null)
			return null;
		LearningLicence ll2 = llDao.save(ll);
		return ll;
	}
}
