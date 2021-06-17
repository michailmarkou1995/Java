package com.userfrontend.security;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Role {
	
	@Id
  private int roleId;

  private String name;

  @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<PatientRole> patientRoles = new HashSet<>();

  public Role() {

  }

  public int getRoleId() {
      return roleId;
  }

  public void setRoleId(int roleId) {
      this.roleId = roleId;
  }

  public String getName() {
      return name;
  }

  public void setName(String name) {
      this.name = name;
  }

  public Set<PatientRole> getPatientRoles() {
      return patientRoles;
  }

  public void setPatientRoles(Set<PatientRole> patientRoles) {
      this.patientRoles = patientRoles;
  }

}
