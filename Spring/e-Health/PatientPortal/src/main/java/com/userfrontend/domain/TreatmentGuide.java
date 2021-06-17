package com.userfrontend.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class TreatmentGuide {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private LocalDate startDate;
	private LocalDate endDate;
	private String prescritpionDirections;
	private String prescritpionSides;
	private String patientComments;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "medication_account_id_fk")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private MedicationAccount medicationAccount;
	
	@ManyToOne
	@JoinColumn(name = "doctor_id_fk")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Doctor doctorAccount;

	
    @ManyToMany(mappedBy="treatmentGuide",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Medication>  medicationList;
    
    @OneToOne(mappedBy="treatmentGuide")//
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Appointment appointment;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getPrescritpionDirections() {
		return prescritpionDirections;
	}

	public void setPrescritpionDirections(String prescritpionDirections) {
		this.prescritpionDirections = prescritpionDirections;
	}

	public String getPrescritpionSides() {
		return prescritpionSides;
	}

	public void setPrescritpionSides(String prescritpionSides) {
		this.prescritpionSides = prescritpionSides;
	}

	public String getPatientComments() {
		return patientComments;
	}

	public void setPatientComments(String patientComments) {
		this.patientComments = patientComments;
	}

	public MedicationAccount getMedicationAccount() {
		return medicationAccount;
	}

	public void setMedicationAccount(MedicationAccount medicationAccount) {
		this.medicationAccount = medicationAccount;
	}

	public Doctor getDoctorAccount() {
		return doctorAccount;
	}

	public void setDoctorAccount(Doctor doctorAccount) {
		this.doctorAccount = doctorAccount;
	}


	public List<Medication> getMedicationList() {
		return medicationList;
	}

	public void setMedicationList(List<Medication> medicationList) {
		this.medicationList = medicationList;
	}

	@Override
	public String toString() {
		return "TreatmentGuide [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", prescritpionDirections=" + prescritpionDirections + ", prescritpionSides=" + prescritpionSides
				+ ", patientComments=" + patientComments + ", medicationList=" + medicationList + "]";
	}
	
}
