package com.userfrontend.service;

import com.userfrontend.domain.PatientHealth;

import java.util.List;

public interface PatientHealthService {

    void createPatientHealth(PatientHealth patientHealth);

    List<PatientHealth> findAll();

}
