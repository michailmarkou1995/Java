package com.userfrontend.service;

import com.userfrontend.domain.Appointment;
import com.userfrontend.domain.DatesDoctorAvailable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DatesDoctorAvailableService {

    List<DatesDoctorAvailable> findAll_id(Long id);

    DatesDoctorAvailable fetchDatesDoctorAvailable(DatesDoctorAvailable datesDoctorAvailable);

    List<DatesDoctorAvailable> findAll();

    DatesDoctorAvailable findByDateID(Long dateID);

    List<DatesDoctorAvailable> findByAppointmentAndAppointmentIsNull(Appointment appointment);

    DatesDoctorAvailable findDatesDoctorAvailable(Long id);

    void save(Appointment appointment);

    List<DatesDoctorAvailable> FindAllWithDescriptionQuery(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo, @Param("cityL") String cityL);

    List<Object[]> FindAllWithDescriptionQuery();

    List<DatesDoctorAvailable> FindAllNotWithDescriptionQuery(long patient_id);

}
