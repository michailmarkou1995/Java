package com.userfrontend.dao;

import com.userfrontend.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

@Transactional
public interface DoctorDao extends JpaRepository<Doctor, Long> {//JpaRepository  CrudRepository

    Doctor findBydoctorID(Long id);

    @Modifying
    @Transactional
    @Query("DELETE  FROM Doctor p WHERE  p.doctorID = ?1")
    void deleteDoctor(Long id);

}
