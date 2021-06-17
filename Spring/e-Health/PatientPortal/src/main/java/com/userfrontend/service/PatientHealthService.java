package com.userfrontend.service;

import java.util.List;

import com.userfrontend.domain.PatientHealth;

public interface PatientHealthService {
	
	void createPatientHealth(PatientHealth patientHealth);
	List<PatientHealth> findAll();

}
