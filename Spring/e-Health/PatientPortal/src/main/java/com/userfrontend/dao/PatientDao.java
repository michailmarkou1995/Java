package com.userfrontend.dao;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.userfrontend.domain.Patient;

public interface PatientDao extends JpaRepository	<Patient, Long>{
	Patient findFirstByUsername(String username);
	Patient findByUsername(String username);
	
    @Query(value = "INSERT INTO 'patient' (`patient_id`, `city`, `date_of_birth`, `doctor_is`, `email`, `enabled`, `first_name`, `last_name`, `password`, `phone`, `street_address`, `username`, `doctor_familly_fk`, `medication_account_fk`) VALUES ('', NULL, '2021-06-03', b'0', 'a', b'1', 'a', 'a', 'a', '123', NULL, 'a', NULL, '')\r\n"
    		+ "",  nativeQuery = true)
	void saveN(@Param("city") String city,@Param("date") LocalDate date,@Param("doctorIs") boolean doctorIs,@Param("email") String email,@Param("enabled") boolean enabled, 
			@Param("firstName") String firstName,@Param("lastName") String lastName,@Param("password") String password,
			@Param("phone") String phone,@Param("streetAddress") String streetAddress,@Param("username") String username);
	
    @Query(value = "SELECT  * FROM Patient  WHERE  username = :username",  nativeQuery = true)
	Patient findUsernameN(@Param("username") String username);
    
    @Query("SELECT  p FROM Patient p WHERE  p.username = :username")
	public Patient findPatientUsername(String username);
    
	Patient findByEmail(String email);
    List<Patient> findAll();
    Patient findBypatientID(Long id);
    
    @Modifying
    @Transactional
    public void deleteByPatientID(long id);

    
    @Modifying
    @Transactional
    @Query("DELETE  FROM Patient p WHERE  p.patientID = ?1")
    public void deleteByPatientIDQ(long id);
	
  @Modifying
  @Transactional
  @Query("update  Patient p set p.firstName = ?1, p.lastName = ?2, p.city = ?3, p.phone = ?4, p.streetAddress = ?5 Where p.patientID = ?6")//make update query
   void setAccountUpdate(String firstName, String lastName, String city, String phone, String street,Long id);

}
