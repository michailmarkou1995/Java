package com.userfrontend.service;

import com.userfrontend.domain.Appointment;
import com.userfrontend.domain.DatesDoctorAvailable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentService {
    void createAppointment(Appointment appointment);

    List<Appointment> findAll();

    Appointment findAppointment(Long id);

    void confirmAppointment(Long id);


    Appointment deleteByDatesDoctorAvailable(DatesDoctorAvailable datesDoctorAvailable);

    Appointment findByDatesDoctorAvailable(DatesDoctorAvailable datesDoctorAvailable);


    Appointment findByDatesDoctorAvailable1(Long id);

    List<Appointment> deleteById(String id);

    void delete(Appointment appointment);

    void deleteScheduleById(@Param("id") Long id);

    void delete(Long id);
}
