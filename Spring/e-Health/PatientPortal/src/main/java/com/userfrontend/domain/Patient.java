package com.userfrontend.domain;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import javax.persistence.PostUpdate;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.userfrontend.security.Authority;
import com.userfrontend.security.PatientRole;

@Entity
public class Patient extends User implements UserDetails{
	 
		@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
    	@Column(name = "patient_id", nullable = false, updatable = false, unique=true)//
		private Long patientID;

		 @OneToOne( mappedBy = "patientid")
		    private Doctor doctorid;
		 
		@OneToOne(optional=false,cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)//
		@OnDelete(action = OnDeleteAction.CASCADE)
		@JoinColumn(name = "medicationAccount_fk")
		@JsonIgnore
		private MedicationAccount medicationAccount;

		
		@OneToMany(mappedBy = "patientAccount", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
		@OnDelete(action = OnDeleteAction.CASCADE)
		@JsonIgnore
		private List<PatientHealth> patienthealth;

		@OneToMany(mappedBy = "patientAccount", cascade = CascadeType.ALL , orphanRemoval = true, fetch = FetchType.EAGER)
		@OnDelete(action = OnDeleteAction.CASCADE)
		@JsonIgnore
		private List<Appointment>  appointmentList;
		
		@OneToMany(mappedBy = "patientAccount", cascade = CascadeType.ALL,  orphanRemoval = true, fetch = FetchType.EAGER)
		@JsonIgnore
		private Set<PatientRole> patientRoles = new HashSet<>();
		

		public Patient(String username, String password, String firstName, String lastName, String email, String phone,
				String city, String streetAddress, LocalDate dateOfBirth, String famillyDoctorAccount, boolean enabled,
				MedicationAccount medicationAccount, List<PatientHealth> patienthealth,
				List<Appointment> appointmentList, Set<PatientRole> patientRoles) {
			super();
//			this.username = username;
//			this.password = password;
//			this.firstName = firstName;
//			this.lastName = lastName;
//			this.email = email;
//			this.phone = phone;
//			this.city = city;
//			this.streetAddress = streetAddress;
//			this.dateOfBirth = dateOfBirth;
//			//this.famillyDoctorAccount = famillyDoctorAccount;
//			this.enabled = enabled;
			this.medicationAccount = medicationAccount;
			//this.doctor = doctor;
			this.patienthealth = patienthealth;
			this.appointmentList = appointmentList;
			this.patientRoles = patientRoles;
		}

		public Patient(Long patientID, String username, String password, String firstName, String lastName,
				String email, String phone, String city, String streetAddress, LocalDate dateOfBirth,
				String famillyDoctorAccount, boolean enabled, MedicationAccount medicationAccount,
				List<PatientHealth> patienthealth, List<Appointment> appointmentList, Set<PatientRole> patientRoles) {
			super();
			this.patientID = patientID;
//			this.username = username;
//			this.password = password;
//			this.firstName = firstName;
//			this.lastName = lastName;
//			this.email = email;
//			this.phone = phone;
//			this.city = city;
//			this.streetAddress = streetAddress;
//			this.dateOfBirth = dateOfBirth;
//			//this.famillyDoctorAccount = famillyDoctorAccount;
//			this.enabled = enabled;
			this.medicationAccount = medicationAccount;
			//this.doctor = doctor;
			this.patienthealth = patienthealth;
			this.appointmentList = appointmentList;
			this.patientRoles = patientRoles;
		}


		public Patient() {
			super();
		}

		public Set<PatientRole> getPatientRoles() {
			return patientRoles;
		}

		public void setPatientRoles(Set<PatientRole> patientRoles) {
			this.patientRoles = patientRoles;
		}

		public MedicationAccount getMedicationAccount() {
			return medicationAccount;
		}

		public void setMedicationAccount(MedicationAccount medicationAccount) {
			this.medicationAccount = medicationAccount;
		}

		public List<PatientHealth> getPatienthealth() {
			return patienthealth;
		}

		public void setPatienthealth(List<PatientHealth> patienthealth) {
			this.patienthealth = patienthealth;
		}

		public List<Appointment> getAppointmentList() {
			return appointmentList;
		}

		public void setAppointmentList(List<Appointment> appointmentList) {
			this.appointmentList = appointmentList;
		}


		public Long getPatientID() {
			return patientID;
		}

		public void setPatientID(Long patientID) {
			this.patientID = patientID;
		}

		@Override
		    public Collection<? extends GrantedAuthority> getAuthorities() {
		        Set<GrantedAuthority> authorities = new HashSet<>();
		        patientRoles.forEach(ur -> authorities.add(new Authority(ur.getRole().getName())));
		        return authorities;
		    }
		

		@Override
		public boolean isAccountNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}
		
}
