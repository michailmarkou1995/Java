package com.userfrontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@SequenceGenerator(name = "seqDatesDoctor", initialValue = 6, allocationSize = 1)
public class DatesDoctorAvailable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqDatesDoctor")
    @Column(name = "date_id", nullable = false, updatable = false)
    private Long dateID;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateAvailable;
    private String timeAvailable;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "datesDoctorAvailable", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Appointment appointment;


    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Doctor doctorAccount;


    public DatesDoctorAvailable(Long dateID, LocalDate dateAvailable, String timeAvailable, Appointment appointment,
                                Doctor doctorAccount) {
        super();
        this.dateID = dateID;
        this.dateAvailable = dateAvailable;
        this.timeAvailable = timeAvailable;
        this.appointment = appointment;
        this.doctorAccount = doctorAccount;
    }


    public DatesDoctorAvailable(Doctor doctorAccount) {
        super();
        this.doctorAccount = doctorAccount;
    }


    public DatesDoctorAvailable() {
        super();
    }


    public Long getDateID() {
        return dateID;
    }

    public void setDateID(Long dateID) {
        this.dateID = dateID;
    }

    public Long getDateID(Long dateID) {
        return dateID;
    }

    public LocalDate getDateAvailable() {
        return dateAvailable;
    }


    public void setDateAvailable(LocalDate dateAvailable) {
        this.dateAvailable = dateAvailable;
    }


    public String getTimeAvailable() {
        return timeAvailable;
    }


    public void setTimeAvailable(String timeAvailable) {
        this.timeAvailable = timeAvailable;
    }


    public Doctor getDoctorAccount() {
        return doctorAccount;
    }


    public void setDoctorAccount(Doctor doctorAccount) {
        this.doctorAccount = doctorAccount;
    }


    @Override
    public String toString() {
        return "DatesDoctorAvailable [dateID=" + dateID + ", dateAvailable=" + dateAvailable + ", timeAvailable="
                + timeAvailable + ", appointment=" + appointment + "]";
    }

}
