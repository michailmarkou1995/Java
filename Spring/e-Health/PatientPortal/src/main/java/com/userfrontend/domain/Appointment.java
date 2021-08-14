package com.userfrontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private boolean confirmed;


    @OneToOne(optional = false)
    @JoinColumn(name = "date_time_available_fk", nullable = true)
    private DatesDoctorAvailable datesDoctorAvailable;


    @ManyToOne(optional = false)
    @JsonIgnore
    @JoinColumn(name = "doctor_id_fk")
    private Doctor doctorAccount;


    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id_fk")
    private Patient patientAccount;

    @OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true)//
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PatientHealth patienthealth;

    @OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true)//
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TreatmentGuide treatmentGuide;


    public Appointment(String description, boolean confirmed, DatesDoctorAvailable datesDoctorAvailable,
                       Doctor doctorAccount, Patient patientAccount, PatientHealth patienthealth, TreatmentGuide treatmentGuide) {
        super();
        this.description = description;
        this.confirmed = confirmed;
        this.datesDoctorAvailable = datesDoctorAvailable;
        this.doctorAccount = doctorAccount;
        this.patientAccount = patientAccount;
        this.patienthealth = patienthealth;
        this.treatmentGuide = treatmentGuide;
    }

    public Appointment(Long id, String description, boolean confirmed, DatesDoctorAvailable datesDoctorAvailable,
                       Doctor doctorAccount, Patient patientAccount, PatientHealth patienthealth, TreatmentGuide treatmentGuide) {
        super();
        this.id = id;
        this.description = description;
        this.confirmed = confirmed;
        this.datesDoctorAvailable = datesDoctorAvailable;
        this.doctorAccount = doctorAccount;
        this.patientAccount = patientAccount;
        this.patienthealth = patienthealth;
        this.treatmentGuide = treatmentGuide;
    }

    public Appointment() {
        super();
    }

    @Override
    public String toString() {
        return "Appointment [id=" + id + ", description=" + description + ", confirmed=" + confirmed
                + "]";
    }

    public TreatmentGuide getTreatmentGuide() {
        return treatmentGuide;
    }

    public void setTreatmentGuide(TreatmentGuide treatmentGuide) {
        this.treatmentGuide = treatmentGuide;
    }

    public DatesDoctorAvailable getDatesDoctorAvailable() {
        return datesDoctorAvailable;
    }

    public void setDatesDoctorAvailable(DatesDoctorAvailable datesDoctorAvailable) {
        this.datesDoctorAvailable = datesDoctorAvailable;
    }

    public DatesDoctorAvailable getDatesDoctorAvailable(DatesDoctorAvailable datesDoctorAvailable) {
        return datesDoctorAvailable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Doctor getDoctorAccount() {
        return doctorAccount;
    }

    public void setDoctorAccount(Doctor doctorAccount) {
        this.doctorAccount = doctorAccount;
    }

    public Patient getPatientAccount() {
        return patientAccount;
    }

    public void setPatientAccount(Patient patientAccount) {
        this.patientAccount = patientAccount;
    }

    public Patient getPatientAccount(Patient patientAccount) {
        return patientAccount;
    }

    public PatientHealth getPatienthealth() {
        return patienthealth;
    }

    public void setPatienthealth(PatientHealth patienthealth) {
        this.patienthealth = patienthealth;
    }


}
