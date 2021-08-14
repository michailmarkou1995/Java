package com.userfrontend.security;

import com.userfrontend.domain.Patient;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;


@Entity
@Table(name = "patient_role")
@SequenceGenerator(name = "seqRole", initialValue = 4, allocationSize = 1)
public class PatientRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqRole")
    private long patientRoleId;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "patient_id")
    private Patient patientAccount;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    public PatientRole(Patient patientAccount, Role role) {
        this.patientAccount = patientAccount;
        this.role = role;
    }

    public PatientRole() {
    }

    public long getPatientRoleId() {
        return patientRoleId;
    }

    public void setPatientRoleId(long patientRoleId) {
        this.patientRoleId = patientRoleId;
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
