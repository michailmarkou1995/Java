package com.userfrontend.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.userfrontend.domain.PatientHealth;

public interface PatientHealthDao extends CrudRepository<PatientHealth, Long> {
	
	List<PatientHealth> findAll();
	void delete(PatientHealth patientHealth);

}
