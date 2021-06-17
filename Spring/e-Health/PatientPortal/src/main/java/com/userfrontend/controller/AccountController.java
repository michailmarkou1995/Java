package com.userfrontend.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.userfrontend.dao.AppointmentDao;
import com.userfrontend.domain.Patient;
import com.userfrontend.service.AccountService;
import com.userfrontend.service.PatientService;


@Transactional
@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	//PatientDao patientDao;
	PatientService patientService;
	
	@Autowired
	AppointmentDao appDao;

	@RequestMapping("/medicationAccount")
	public String medicationAccount(Principal principal, Model model) {		
		return "medical";
	}
	
	@RequestMapping(value = "/medicationAccount",method = RequestMethod.GET)
    public String medicationAccountFetch(Principal principal, Model model) {
		Patient patient = patientService.findByUsername(principal.getName());
		System.out.println(patient);
		model.addAttribute("medsav", appDao.findAllforPatient(patient.getPatientID()));
		System.out.println(appDao.findAllforPatient(patient.getPatientID()));
        return "medical";
    }
	
	@RequestMapping("/personalAccount")
	public String personalAccount(Principal principal, Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Patient patient = (Patient)auth.getPrincipal();
	
		Patient patient1 = patientService.findById(patient.getPatientID());
		patient.getEmail();
      
		Map<String, Object> userD =Map.of("patient", patient);
		List<Object> people = new ArrayList<>();
		people.add(userD);

		model.addAttribute("FirstName", patient1.getFirstName());
		model.addAttribute("LastName", patient1.getLastName());
		model.addAttribute("UserName", patient1.getUsername());
		model.addAttribute("Phone", patient1.getPhone());
		model.addAttribute("Email", patient1.getEmail());
		model.addAttribute("City", patient1.getCity());
		model.addAttribute("Street", patient1.getStreetAddress());
		model.addAttribute("Date", patient1.getDateOfBirth());
		
		Authentication authentication = new PreAuthenticatedAuthenticationToken(patient1, patient1.getPassword(), patient1.getAuthorities());//UsernamePasswordAuthenticationToken
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		model.addAttribute("MyName", principal.getName());
		UserDetails userDetails;
		userDetails = patient1;
		userDetails.getUsername();

		return "personalinformation";
	}
	
	@RequestMapping(value = "/personalinformation",method = RequestMethod.GET)
    public String getPatientInfoAndDoctor(Principal principal, Model model) {
		System.out.println(((Patient)principal).getUsername());
        return "personalinformation";
    }
	
	  @RequestMapping(value = "/personalAccount", method = RequestMethod.POST)
	    public String setUpdatePersonal(
	    		@ModelAttribute("firstNameU") String firstname, 
	    		@ModelAttribute("lastNameU") String lastname,
	    		@ModelAttribute("cityU") String city, 
	    		@ModelAttribute("phoneU") String phone, 
	    		@ModelAttribute("streetU") String street, 
	    		Principal principal, Model model) {
		      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		  		Patient patient1 = (Patient)auth.getPrincipal();


		  if(!AccountService.isNumeric(firstname) 
				  && !AccountService.isNumeric(lastname) 
				  && !AccountService.isNumeric(city) 
				  && AccountService.isNumeric(phone)) {
			  System.out.println(firstname);
			  accountService.patientAccountUpdate(firstname, lastname, city, phone,  street, patient1.getPatientID());
		  } else {

			  System.out.println("number");
			  
			  
		  }

	model.addAttribute("MyName", principal.getName());
	Patient patient = (Patient)auth.getPrincipal();
	
	Authentication auth1 = SecurityContextHolder.getContext().getAuthentication();
	List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth1.getAuthorities());
	updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
	Authentication newAuth = new UsernamePasswordAuthenticationToken(auth1.getPrincipal(), auth1.getCredentials(), updatedAuthorities);
	SecurityContextHolder.getContext().setAuthentication(newAuth);

	Authentication authentication = new UsernamePasswordAuthenticationToken(patient, patient.getFirstName(), patient.getAuthorities());
	SecurityContextHolder.getContext().setAuthentication(authentication);

	Patient patient2 = patientService.findById(patient.getPatientID());

	model.addAttribute("FirstName", patient2.getFirstName());
	model.addAttribute("LastName", patient2.getLastName());
	model.addAttribute("UserName", patient2.getUsername());
	model.addAttribute("Phone", patient2.getPhone());
	model.addAttribute("Email", patient2.getEmail());
	model.addAttribute("City", patient2.getCity());
	model.addAttribute("Street", patient2.getStreetAddress());
	model.addAttribute("Date", patient2.getDateOfBirth());
	model.addAttribute("firstNameU", "");
	model.addAttribute("lastNameU", "");
	model.addAttribute("phoneU", "");
	model.addAttribute("cityU", "");
	model.addAttribute("streetU", "");

	        return "personalinformation";
	    }

}
