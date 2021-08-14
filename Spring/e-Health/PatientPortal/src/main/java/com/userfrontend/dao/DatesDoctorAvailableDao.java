package com.userfrontend.dao;

import com.userfrontend.domain.Appointment;
import com.userfrontend.domain.DatesDoctorAvailable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DatesDoctorAvailableDao extends CrudRepository<DatesDoctorAvailable, Long> {

    List<DatesDoctorAvailable> findAll();

    @Query(value = "Select * FROM dates_doctor_available WHERE doctor_account_doctor_id = :id ", nativeQuery = true)
    List<DatesDoctorAvailable> findAllDoctor(@Param("id") Long id);

    DatesDoctorAvailable findByDateID(Long dateID);

    List<DatesDoctorAvailable> findByAppointmentAndAppointmentIsNull(Appointment appointment);

    void save(Appointment appointment);


    @Query(value = "Select * FROM dates_doctor_available a  LEFT join appointment b on a.date_id=b.date_time_available_fk inner join doctor c on a.doctor_account_doctor_id=c.doctor_id join patient p on p.patient_id=c.doctor_id WHERE b.date_time_available_fk IS  NULL AND a.date_available BETWEEN :dateFrom and :dateTo AND c.city= :cityL", nativeQuery = true)
    List<DatesDoctorAvailable> FindAllWithDescriptionQuery(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo, @Param("cityL") String cityL);


    @Query(value = " Select DISTINCT c.city FROM dates_doctor_available a  LEFT join appointment b on a.date_id=b.date_time_available_fk inner join doctor c on a.doctor_account_doctor_id=c.doctor_id WHERE b.date_time_available_fk IS NULL", nativeQuery = true)
    List<Object[]> FindAllWithDescriptionQuery();

    @Query(value = "Select * FROM dates_doctor_available a  LEFT join appointment b on a.date_id=b.date_time_available_fk WHERE b.date_time_available_fk IS NOT NULL AND b.patient_id_fk= :patient_id", nativeQuery = true)
    List<DatesDoctorAvailable> FindAllNotWithDescriptionQuery(@Param("patient_id") long patient_id);


    @Query(value = "Select * FROM dates_doctor_available where doctor_account_doctor_id = :patient_id", nativeQuery = true)
    List<DatesDoctorAvailable> findHimId(@Param("patient_id") long id);
}
