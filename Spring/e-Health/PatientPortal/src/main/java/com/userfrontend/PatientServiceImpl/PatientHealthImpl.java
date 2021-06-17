package com.userfrontend.PatientServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userfrontend.dao.PatientHealthDao;
import com.userfrontend.domain.PatientHealth;
import com.userfrontend.service.PatientHealthService;

@Service
public class PatientHealthImpl implements PatientHealthService{
	
	@Autowired
	private PatientHealthDao patientHealthDao;

	@Override
	public void createPatientHealth(PatientHealth patientHealth) {
		patientHealthDao.save(patientHealth);
		
	}

	@Override
	public List<PatientHealth> findAll() {
		return patientHealthDao.findAll();
	}

}
