package com.userfrontend.service;

import com.userfrontend.domain.Patient;
import com.userfrontend.security.PatientRole;

import java.util.List;
import java.util.Set;

public interface PatientService {
    Patient findByUsername(String username);

    Patient findByEmail(String email);

    boolean checkPatientExists(String username, String email);

    boolean checkUsernameExists(String username);

    boolean checkEmailExists(String email);

    void save(Patient patient);

    void createUser(Patient patient, Set<PatientRole> patientRoles);

    List<Patient> findPatientList();

    void enablePatient(String username);

    void disablePatient(String username);

    void enableDoctor(String username, String catD);

    void disableDoctor(String username);

    Patient findById(Long id);

    boolean deletePatientQ(Patient patient);

    List<Patient> findAll();


}
