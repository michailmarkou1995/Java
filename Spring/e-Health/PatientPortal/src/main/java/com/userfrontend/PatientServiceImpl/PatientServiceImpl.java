package com.userfrontend.PatientServiceImpl;

import com.userfrontend.dao.DoctorDao;
import com.userfrontend.dao.PatientDao;
import com.userfrontend.dao.PatientRoleDao;
import com.userfrontend.dao.RoleDao;
import com.userfrontend.domain.Doctor;
import com.userfrontend.domain.Patient;
import com.userfrontend.security.PatientRole;
import com.userfrontend.security.Role;
import com.userfrontend.service.AccountService;
import com.userfrontend.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private static final Logger LOG = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AccountService accountService;

    @Autowired
    private DoctorDao doctorDao;

    @Autowired
    private PatientRoleDao pRoleDao;

    public void save(Patient patient) {
        patientDao.save(patient);
    }

    public Patient findByUsername(String username) {
        return patientDao.findUsernameN(username);
    }

    public Patient findByEmail(String email) {
        return patientDao.findByEmail(email);
    }


    public void createUser(Patient patient, Set<PatientRole> patientRoles) {
        Patient localUser = patientDao.findUsernameN(patient.getUsername());

        if (localUser != null) {
            LOG.info("User with username {} already exist. Nothing will be done. ", patient.getUsername());
        } else {
            String encryptedPassword = passwordEncoder.encode(patient.getPassword());
            patient.setPassword(encryptedPassword);

            for (PatientRole ur : patientRoles) {
                roleDao.save(ur.getRole());
            }

            patient.getPatientRoles().addAll(patientRoles);
            patient.setMedicationAccount(accountService.createMedicationAccount());
            patientDao.save(patient);
        }
    }

    public boolean checkPatientExists(String username, String email) {
        return checkUsernameExists(username) || checkEmailExists(username);
    }

    public boolean checkUsernameExists(String username) {
        return null != findByUsername(username);
    }

    public boolean checkEmailExists(String email) {
        return null != findByEmail(email);
    }

    public List<Patient> findPatientList() {
        return patientDao.findAll();
    }


    @Override
    public void enablePatient(String username) {
        Patient patient = findByUsername(username);
        patient.setEnabled(true);
        patientDao.save(patient);
    }

    @Override
    public void disablePatient(String username) {

        Patient patient = findByUsername(username);
        patient.setEnabled(false);
        System.out.println(patient.isEnabled());
        patientDao.save(patient);
    }

    @Override
    public void enableDoctor(String username, String catD) {
        Patient patient = findByUsername(username);
        Doctor doctor = new Doctor();
        doctor.setPatientid(patient);
        PatientRole patrol;
        Role role = new Role();
        role.setRoleId(1);
        patrol = pRoleDao.findBypatientAccount(patient);
        patrol.setRole(role);
        doctor.setPatientroles(patrol);
        doctor.setCity(patient.getCity());
        doctor.setFirstName(patient.getFirstName());
        doctor.setLastName(patient.getLastName());
        doctor.setCategoryDoctor(catD);
        doctorDao.save(doctor);
        patient.setDoctorIs(true);
        patientDao.save(patient);
    }

    @Transactional
    @Override
    public void disableDoctor(String username) {
        Patient patient = findByUsername(username);
        Doctor doctor;
        doctor = doctorDao.findBydoctorID(patient.getPatientID());
        PatientRole patrol;
        Role role = new Role();
        role.setRoleId(0);
        patrol = pRoleDao.findBypatientAccount(patient);
        patrol.setRole(role);
        doctor.setPatientroles(patrol);
        doctorDao.deleteDoctor(doctor.getDoctorID());
        patient.setDoctorIs(false);
    }

    @Override
    public Patient findById(Long id) {
        return patientDao.findBypatientID(id);

    }

    @Override
    public boolean deletePatientQ(Patient patient) {
        boolean status = false;
        try {
            patientDao.deleteByPatientIDQ(patient.getPatientID());
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public List<Patient> findAll() {
        return patientDao.findAll();

    }

}
