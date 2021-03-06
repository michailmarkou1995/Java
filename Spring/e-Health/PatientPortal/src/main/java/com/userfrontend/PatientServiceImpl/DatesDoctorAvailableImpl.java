package com.userfrontend.PatientServiceImpl;

import com.userfrontend.dao.AppointmentDao;
import com.userfrontend.dao.DatesDoctorAvailableDao;
import com.userfrontend.dao.DoctorDao;
import com.userfrontend.domain.Appointment;
import com.userfrontend.domain.DatesDoctorAvailable;
import com.userfrontend.domain.Doctor;
import com.userfrontend.service.DatesDoctorAvailableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DatesDoctorAvailableImpl implements DatesDoctorAvailableService {


    @Autowired
    DoctorDao doctorDao;
    @Autowired
    private AppointmentDao appointmentDao;
    @Autowired
    private DatesDoctorAvailableDao datesDoctorAvailableDao;

    public List<DatesDoctorAvailable> findAll() {
        return datesDoctorAvailableDao.findAll();
    }

    @Override
    public DatesDoctorAvailable fetchDatesDoctorAvailable(DatesDoctorAvailable datesDoctorAvailable) {
        return datesDoctorAvailableDao.save(datesDoctorAvailable);
    }

    @Override
    public DatesDoctorAvailable findDatesDoctorAvailable(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DatesDoctorAvailable> findByAppointmentAndAppointmentIsNull(Appointment appointment) {
        return datesDoctorAvailableDao.findByAppointmentAndAppointmentIsNull(appointment);
    }

    public void save(Appointment appointment) {
        appointmentDao.save(appointment);
    }

    public List<DatesDoctorAvailable> FindAllWithDescriptionQuery(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo, @Param("cityL") String cityL) {
        return datesDoctorAvailableDao.FindAllWithDescriptionQuery(dateFrom, dateTo, cityL);
    }

    public List<Object[]> FindAllWithDescriptionQuery() {
        return datesDoctorAvailableDao.FindAllWithDescriptionQuery();
    }

    @Override
    public DatesDoctorAvailable findByDateID(Long dateID) {
        return datesDoctorAvailableDao.findByDateID(dateID);
    }

    @Override
    public List<DatesDoctorAvailable> FindAllNotWithDescriptionQuery(long patient_id) {
        return datesDoctorAvailableDao.FindAllNotWithDescriptionQuery(patient_id);
    }

    @Override
    public List<DatesDoctorAvailable> findAll_id(Long id) {
        Doctor doctor;
        doctor = doctorDao.findBydoctorID(id);
        Long dId = doctor.getDoctorID();
        return datesDoctorAvailableDao.findHimId(dId);
    }

}
