package com.userfrontend.PatientServiceImpl;

import com.userfrontend.dao.AppointmentDao;
import com.userfrontend.domain.Appointment;
import com.userfrontend.domain.DatesDoctorAvailable;
import com.userfrontend.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentDao appointmentDao;

    public void createAppointment(Appointment appointment) {
        appointmentDao.save(appointment);
    }

    public List<Appointment> findAll() {
        return appointmentDao.findAll();
    }

    public Appointment findAppointment(Long id) {
        return appointmentDao.findById(id).orElse(null);

    }

    public void confirmAppointment(Long id) {
        Appointment appointment = findAppointment(id);
        appointment.setConfirmed(true);
        appointmentDao.save(appointment);
    }


    @Override
    public Appointment deleteByDatesDoctorAvailable(DatesDoctorAvailable datesDoctorAvailable) {
        return appointmentDao.deleteByDatesDoctorAvailable(datesDoctorAvailable);

    }

    @Override
    public Appointment findByDatesDoctorAvailable(DatesDoctorAvailable datesDoctorAvailable) {
        return appointmentDao.findByDatesDoctorAvailable(datesDoctorAvailable);
    }

    @Override
    public Appointment findByDatesDoctorAvailable1(Long id) {
        return appointmentDao.findByDatesDoctorAvailable(id);
    }

    @Override
    public void delete(Appointment appointment) {
        appointmentDao.delete(appointment);
    }

    @Override
    public List<Appointment> deleteById(String id) {
        return appointmentDao.deleteById(id);
    }

    @Override
    public void deleteScheduleById(Long id) {
        appointmentDao.deleteScheduleById(id);

    }

    @Override
    public void delete(Long id) {
        appointmentDao.deleteById(id);
    }

}

