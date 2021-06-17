package com.userfrontend.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import com.userfrontend.domain.Patient;
import com.userfrontend.security.PatientRole;

public interface PatientService {
	Patient findByUsername(String username);
	
	Patient findByUsernameN(String username);
	
	Patient findPatientUsername(String username);

	Patient findByEmail(String email);

    boolean checkPatientExists(String username, String email);

    boolean checkUsernameExists(String username);

    boolean checkEmailExists(String email);
    
    void save (Patient patient);
    
    Patient createUser(Patient patient, Set<PatientRole> patientRoles);
    
    Patient savePatient(Patient patient); 
    
    List<Patient> findPatientList();

    void enablePatient (String username);

    void disablePatient (String username);
    
    void enableDoctor (String username);
    
    void enableDoctorM (String username, String catD);
    
    void disableDoctor(String username);
    
    Patient findById(Long id);

    public boolean deletePatient(Patient patient);
    
    public boolean deletePatientQ(Patient patient);

    List<Patient> findAll();
    
    
}
