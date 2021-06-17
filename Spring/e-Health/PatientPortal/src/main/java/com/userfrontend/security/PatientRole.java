package com.userfrontend.security;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.userfrontend.domain.Doctor;
import com.userfrontend.domain.Patient;


@Entity
@Table(name="patient_role")
public class PatientRole {

	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private long patientRoleId;

	    public PatientRole(Patient patientAccount, Role role) {
	        this.patientAccount = patientAccount;
	        this.role = role;
	    }


	    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	    @OnDelete(action = OnDeleteAction.CASCADE)
	    @JoinColumn(name = "patient_id")
	    private Patient patientAccount;

	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "role_id")
	    private Role role;

	    public PatientRole() {}

	    public long getPatientRoleId() {
	        return patientRoleId;
	    }

	    public void setPatientRoleId(long patientRoleId) {
	        this.patientRoleId= patientRoleId;
	    }

	    public Patient getPatientAccount() {
			return patientAccount;
		}

		public void setPatientAccount(Patient patientAccount) {
			this.patientAccount = patientAccount;
		}

		public Patient getPatient() {
	        return patientAccount;
	    }

	    public void setPatient(Patient patientAccount) {
	        this.patientAccount = patientAccount;
	    }

	    public Role getRole() {
	        return role;
	    }

	    public void setRole(Role role) {
	        this.role = role;
	    }

	    
}
