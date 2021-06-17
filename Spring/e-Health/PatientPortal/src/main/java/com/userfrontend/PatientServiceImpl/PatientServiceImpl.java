package com.userfrontend.PatientServiceImpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Service
@Transactional
public class PatientServiceImpl implements PatientService{
	
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
    
    
    public Patient createUser(Patient patient, Set<PatientRole> patientRoles) {
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
           // patient.setSavingsAccount(accountService.createSavingsAccount());
            System.out.println("medacc");    
System.out.println(patient.getMedicationAccount().getAccountNumber());
            localUser = patientDao.save(patient);//save
//            MedicationAccount medicationAccountP = new MedicationAccount();
//            medicationAccountP = accountService.findByAccountNumber(patient.getMedicationAccount().getAccountNumber());
//            medicationAccountP.setPatient(patient);
            
        }

        return localUser;
    }
    
    public boolean checkPatientExists(String username, String email){
        if (checkUsernameExists(username) || checkEmailExists(username)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUsernameExists(String username) {
        if (null != findByUsername(username)) {
            return true;
        }

        return false;
    }
    
    public boolean checkEmailExists(String email) {
        if (null != findByEmail(email)) {
            return true;
        }

        return false;
    }

    public Patient savePatient (Patient patient) {
        return patientDao.save(patient);
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
        System.out.println(username + " is disabled.");
	}
	
	@Override
	public void enableDoctor (String username) {
		Patient patient = findByUsername(username);
		//Patient doctor = new Doctor();
		Doctor doctor = new Doctor();
		doctor.setPatientid(patient);
		PatientRole patrol = new PatientRole();
		Role role = new Role();
		role.setRoleId(1);
		patrol = pRoleDao.findBypatientAccount(patient);
//		patrol.setPatient(patient);
//		patrol.setPatientAccount(patient);
//		patrol.setPatientRoleId(patient.getPatientRoles());
		//doctor.setPatientroles(patrol.setRole(role.setRoleId(1)));
		patrol.setRole(role);
		doctor.setPatientroles(patrol);
		doctor.setCity(patient.getCity());
		doctor.setFirstName(patient.getFirstName());
		doctor.setLastName(patient.getLastName());
		doctorDao.save(doctor);
		patient.setDoctorIs(true);
		patientDao.save(patient);		
	}
	
	@Override
	public void enableDoctorM (String username, String catD) {
		Patient patient = findByUsername(username);
		//Patient doctor = new Doctor();
		Doctor doctor = new Doctor();
		doctor.setPatientid(patient);
		PatientRole patrol = new PatientRole();
		Role role = new Role();
		role.setRoleId(1);
		patrol = pRoleDao.findBypatientAccount(patient);
//		patrol.setPatient(patient);
//		patrol.setPatientAccount(patient);
//		patrol.setPatientRoleId(patient.getPatientRoles());
		//doctor.setPatientroles(patrol.setRole(role.setRoleId(1)));
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
	public void disableDoctor (String username) {
		Patient patient = findByUsername(username);
		//Doctor doctor = new Doctor();
		//Doctor doctor = doctorDao.findBydoctorId(patient); edw tha thele service me return Doctor types
		Doctor doctor = new Doctor();
		doctor = doctorDao.findBydoctorID(patient.getPatientID());
		System.out.println(doctor);
		//doctor.getPatientid();
//		doctor.setPatientid(patient);
//		//Doctor doctor = new Doctor().getPatientid(patient.getPatientID());
//		PatientRole patrol = new PatientRole();
//		Role role = new Role();
//		role.setRoleId(0);
//		patrol.setRole(role);
//		//doctor.setPatientroles(patrol.setRole(role.setRoleId(1)));
//		doctor.setPatientroles(patrol);
//		doctorDao.save(doctor);
		PatientRole patrol = new PatientRole();
		Role role = new Role();
		role.setRoleId(0);
		patrol = pRoleDao.findBypatientAccount(patient);
		patrol.setRole(role);
		doctor.setPatientroles(patrol);
		//doctorDao.saveAndFlush(doctor);//save
		//doctorDao.delete(doctor);
		//doctorDao.deleteById(doctor.getDoctorID());
		//doctorDao.flush(); //<==mazi me delete paei to flush
		doctorDao.deleteDoctor(doctor.getDoctorID());
		//doctorDao.deleteById(patient.getPatientID());
		//doctorDao.saveAndFlush(doctor);
		
		System.out.println(doctor);
		patient.setDoctorIs(false);
		//patientDao.saveAndFlush(patient);
		System.out.println(patient);
		//patientDao.save(patient);		
	}

	@Override
	public Patient findById(Long id) {
		return patientDao.findBypatientID(id);
		
	}

	@Override
	public boolean deletePatient(Patient patient) {
		boolean status=false;  
		try {  
            patientDao.deleteByPatientID(patient.getPatientID());
            status=true;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return status;  
	}

	@Override
	public boolean deletePatientQ(Patient patient) {
		boolean status=false;  
		try {  
            patientDao.deleteByPatientIDQ(patient.getPatientID());
            status=true;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return status;  
	}

	@Override
	public List<Patient> findAll() {
        return patientDao.findAll();

	}

	@Override
	public Patient findPatientUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Patient findByUsernameN(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
