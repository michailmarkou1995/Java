package com.userfrontend.dao;

import com.userfrontend.domain.Patient;
import com.userfrontend.security.PatientRole;
import org.springframework.data.repository.CrudRepository;

public interface PatientRoleDao extends CrudRepository<PatientRole, Long> {
    PatientRole findBypatientAccount(Patient patient);

}
