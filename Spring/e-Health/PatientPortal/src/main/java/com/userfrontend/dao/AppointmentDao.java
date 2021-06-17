package com.userfrontend.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.userfrontend.domain.Appointment;
import com.userfrontend.domain.DatesDoctorAvailable;

public interface AppointmentDao extends CrudRepository<Appointment, Long> {

    List<Appointment> findAll();
    
    
    Appointment deleteByDatesDoctorAvailable(DatesDoctorAvailable datesDoctorAvailable);
    void delete(Appointment appointment);
    
    @Query(value = "SELECT * FROM appointment WHERE patient_id_fk = :id ",  nativeQuery = true)
    List<Appointment> findAllforPatient(@Param("id") Long id);
    
    
    @Query(value = "SELECT * FROM appointment WHERE doctor_id_fk = :id ",  nativeQuery = true)
	 List<String> findDoctorInline(@Param("id") Long id);
    
    @Query(value = "SELECT * FROM appointment WHERE doctor_id_fk = :id ",  nativeQuery = true)
	 List<Appointment> findDoctorInline1(@Param("id") Long id);
    
    @Query(value = "SELECT * FROM appointment ap join patient p on ap.patient_id_fk=p.patient_id join treatment_guide tg on p.medication_account_fk=tg.medication_account_id_fk join dates_doctor_available da on ap.date_time_available_fk=da.date_id WHERE ap.doctor_id_fk = :id ",  nativeQuery = true)
	 List<Appointment> findDoctorInline2(@Param("id") Long id);
    
    @Query(value = "SELECT * FROM appointment  WHERE patienthealth_id = :id ",  nativeQuery = true)
	 Appointment findBasedOnPatienthealth(@Param("id") Long id);
    

    Appointment findByDatesDoctorAvailable(DatesDoctorAvailable datesDoctorAvailable);
    
    
    Appointment findByDatesDoctorAvailable(Long id);

    List<Appointment> deleteById(String id);
    
    void deleteById(Long id);
    
    @Modifying
    @Transactional
    @Query("delete from Appointment where id = :id")
    void deleteScheduleById(@Param("id") Long id);
    
}
