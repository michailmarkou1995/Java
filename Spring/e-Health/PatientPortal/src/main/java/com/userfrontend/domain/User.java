package com.userfrontend.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@MappedSuperclass
public abstract class User {

	@Column(unique=true, nullable = false)
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	

	@Column(name = "email", nullable = false, unique = true)
	private String email;
	private String phone;
	private String city;
	private String streetAddress;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private LocalDate dateOfBirth;

	private boolean enabled=true;
	private boolean doctorIs=false;
	
	
	public User() {
		super();
	}
	
	public User(String username, String password, String firstName, String lastName, String email, String phone,
			String city, String streetAddress, LocalDate dateOfBirth, boolean enabled, boolean doctorIs) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.city = city;
		this.streetAddress = streetAddress;
		this.dateOfBirth = dateOfBirth;
		this.enabled = enabled;
		this.doctorIs = doctorIs;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isDoctorIs() {
		return doctorIs;
	}

	public void setDoctorIs(boolean doctorIs) {
		this.doctorIs = doctorIs;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", phone=" + phone + ", city=" + city + ", streetAddress="
				+ streetAddress + ", dateOfBirth=" + dateOfBirth + ", enabled=" + enabled + ", doctorIs=" + doctorIs
				+ "]";
	}

	
}
