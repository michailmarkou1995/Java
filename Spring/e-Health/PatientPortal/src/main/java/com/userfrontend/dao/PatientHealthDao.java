package com.userfrontend.dao;

import com.userfrontend.domain.PatientHealth;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientHealthDao extends CrudRepository<PatientHealth, Long> {

    List<PatientHealth> findAll();

    void delete(PatientHealth patientHealth);

}
