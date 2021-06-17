package com.userfrontend.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.userfrontend.security.PatientRole;

@Entity
public class Doctor {//extends User or why not cant patient ?
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long doctorID;
    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(name = "doctor_id")
    @MapsId
    private Patient patientid;
    
    @OneToOne(cascade = CascadeType.ALL,  orphanRemoval = true, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="patient_role_id_fk")
	private PatientRole patientroles;
    

	private String firstName;
	private String lastName;

	private String city;

	private String categoryDoctor;
	private String rank;

	
	@OneToMany(mappedBy = "doctorAccount", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<DatesDoctorAvailable> datesDoctorAvailable;
	
	@OneToMany(mappedBy = "doctorAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List <TreatmentGuide> treatmentguide;
	
	@OneToMany(mappedBy = "doctorAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List <Appointment> appointmnetList;




	public Long getDoctorID() {
		return doctorID;
	}

	public void setDoctorID(Long doctorID) {
		this.doctorID = doctorID;
	}

	public Patient getPatientid() {
		return patientid;
	}

	public void setPatientid(Patient patientid) {
		this.patientid = patientid;
	}

	public PatientRole getPatientroles() {
		return patientroles;
	}

	public void setPatientroles(PatientRole patientroles) {
		this.patientroles = patientroles;
	}

	public List<Appointment> getAppointmnetList() {
		return appointmnetList;
	}

	public void setAppointmnetList(List<Appointment> appointmnetList) {
		this.appointmnetList = appointmnetList;
	}

	public Doctor() {
		super();
	}


	public Doctor(String username, String password, String firstName, String lastName, String email, String phone,
			String city, String streetAddress, LocalDate dateOfBirth, String categoryDoctor, String rank,
			boolean enabled, List<DatesDoctorAvailable> datesDoctorAvailable, List<TreatmentGuide> treatmentguide) {
		super();

		this.firstName = firstName;
     	this.lastName = lastName;

		this.city = city;

		this.categoryDoctor = categoryDoctor;
		this.rank = rank;
		this.datesDoctorAvailable = datesDoctorAvailable;
		this.treatmentguide = treatmentguide;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Doctor [doctorID=" + doctorID + ", patientid=" + patientid + ", patientroles=" + patientroles
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", city=" + city + ", categoryDoctor="
				+ categoryDoctor + ", rank=" + rank + ", datesDoctorAvailable=" + datesDoctorAvailable
				+ ", treatmentguide=" + treatmentguide + ", appointmnetList=" + appointmnetList + "]";
	}

	public Doctor(Long doctorID, String username, String password, String firstName, String lastName, String email,
			String phone, String city, String streetAddress, LocalDate dateOfBirth, String categoryDoctor, String rank,
			boolean enabled, List<DatesDoctorAvailable> datesDoctorAvailable, List<TreatmentGuide> treatmentguide) {
		super();
//		this.doctorID = doctorID;
//		this.username = username;
//		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
//		this.email = email;
//		this.phone = phone;
		this.city = city;
//		this.streetAddress = streetAddress;
//		this.dateOfBirth = dateOfBirth;
		this.categoryDoctor = categoryDoctor;
		this.rank = rank;
//		this.enabled = enabled;
		this.datesDoctorAvailable = datesDoctorAvailable;
		this.treatmentguide = treatmentguide;
	}


	public List<DatesDoctorAvailable> getDatesDoctorAvailable() {
		return datesDoctorAvailable;
	}

	public void setDatesDoctorAvailable(List<DatesDoctorAvailable> datesDoctorAvailable) {
		this.datesDoctorAvailable = datesDoctorAvailable;
	}

	public String getCategoryDoctor() {
		return categoryDoctor;
	}

	public void setCategoryDoctor(String categoryDoctor) {
		this.categoryDoctor = categoryDoctor;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public List<TreatmentGuide> getTreatmentguide() {
		return treatmentguide;
	}

	public void setTreatmentguide(List<TreatmentGuide> treatmentguide) {
		this.treatmentguide = treatmentguide;
	}
	
}
