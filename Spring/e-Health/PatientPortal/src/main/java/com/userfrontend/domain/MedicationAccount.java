package com.userfrontend.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class MedicationAccount {
	
		@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name="medication_account_id")
		private Long id;
		private int accountNumber;
		private int totalMedicationsOverYear;
		
		@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)//mappedBy="medicationAccount",

		@OnDelete(action = OnDeleteAction.CASCADE)
		Patient patient;
		
		public Patient getPatient() {
			return patient;
		}


		public void setPatient(Patient patient) {
			this.patient = patient;
		}


		public List<TreatmentGuide> getTreatmentGuide() {
			return treatmentGuide;
		}


		public void setTreatmentGuide(List<TreatmentGuide> treatmentGuide) {
			this.treatmentGuide = treatmentGuide;
		}


		@OneToMany(mappedBy = "medicationAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    @JsonIgnore
		private List<TreatmentGuide> treatmentGuide;//when appointment again and for how long


		public MedicationAccount() {
			super();
		}


		public MedicationAccount(int accountNumber, int totalMedicationsOverYear, List<TreatmentGuide> treatmentGuide) {
			super();
			this.accountNumber = accountNumber;
			this.totalMedicationsOverYear = totalMedicationsOverYear;
		}


		public MedicationAccount(Long id, int accountNumber, int totalMedicationsOverYear,
				List<TreatmentGuide> treatmentGuide) {
			super();
			this.id = id;
			this.accountNumber = accountNumber;
			this.totalMedicationsOverYear = totalMedicationsOverYear;
		}


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public int getAccountNumber() {
			return accountNumber;
		}


		public void setAccountNumber(int accountNumber) {
			this.accountNumber = accountNumber;
		}


		public int getTotalMedicationsOverYear() {
			return totalMedicationsOverYear;
		}


		public void setTotalMedicationsOverYear(int totalMedicationsOverYear) {
			this.totalMedicationsOverYear = totalMedicationsOverYear;
		}
		
}
