package com.userfrontend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.userfrontend.domain.Appointment;
import com.userfrontend.domain.DatesDoctorAvailable;
import com.userfrontend.domain.Patient;

public interface DatesDoctorAvailableService {
	
	public List<DatesDoctorAvailable> findAll_id(Long id);
	
	DatesDoctorAvailable fetchDatesDoctorAvailable(DatesDoctorAvailable  datesDoctorAvailable);
	
    List<DatesDoctorAvailable> findAll();
    
	 DatesDoctorAvailable findByDateID(Long  dateID);
    
    List<DatesDoctorAvailable>  findByAppointmentAndAppointmentIsNull(Appointment appointment);
    
    DatesDoctorAvailable findDatesDoctorAvailable(Long id);
    void save (Appointment appointment);
    
    public List<DatesDoctorAvailable>FindAllWithDescriptionQuery(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo, @Param("cityL") String cityL);
    public List<Object[]>FindAllWithDescriptionQuery();
    
    public List<DatesDoctorAvailable> FindAllNotWithDescriptionQuery(long patient_id);

}
