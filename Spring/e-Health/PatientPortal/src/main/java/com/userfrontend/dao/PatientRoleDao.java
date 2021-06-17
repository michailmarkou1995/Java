package com.userfrontend.dao;

import org.springframework.data.repository.CrudRepository;

import com.userfrontend.domain.Patient;
import com.userfrontend.security.PatientRole;

public interface PatientRoleDao extends CrudRepository<PatientRole, Long> {
	PatientRole findBypatientAccount(Patient patient);

}
