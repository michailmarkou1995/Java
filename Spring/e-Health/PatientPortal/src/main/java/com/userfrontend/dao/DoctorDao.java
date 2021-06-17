package com.userfrontend.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.userfrontend.domain.Doctor;

@Transactional
public interface DoctorDao extends JpaRepository<Doctor, Long> {//JpaRepository  CrudRepository
	
	Doctor findBydoctorID(Long id);
	
	  @Modifying
	  @Transactional
	  @Query("DELETE  FROM Doctor p WHERE  p.doctorID = ?1")//make update query
	   void deleteDoctor(Long id);

}
