package com.userfrontend.domain;

import javax.persistence.*;

@Entity
public class PatientHealth {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String patientDiagnosed;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id_fk")
    private Patient patientAccount;


    @OneToOne(mappedBy = "patienthealth")
    private Appointment appointment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientDiagnosed() {
        return patientDiagnosed;
    }

    public void setPatientDiagnosed(String patientDiagnosed) {
        this.patientDiagnosed = patientDiagnosed;
    }

    public Patient getPatientAccount() {
        return patientAccount;
    }

    public void setPatientAccount(Patient patientAccount) {
        this.patientAccount = patientAccount;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }


}
