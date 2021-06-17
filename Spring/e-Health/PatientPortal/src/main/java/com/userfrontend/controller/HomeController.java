package com.userfrontend.controller;


import java.security.Principal;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.userfrontend.dao.MedicationAccountDao;
import com.userfrontend.dao.RoleDao;
import com.userfrontend.domain.MedicationAccount;
import com.userfrontend.domain.Patient;
import com.userfrontend.security.PatientRole;
import com.userfrontend.service.AccountService;
import com.userfrontend.service.PatientService;

@Controller
public class HomeController {
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private AccountService medaccount;
	
	@Autowired
	private MedicationAccountDao medicationD;

	@RequestMapping("/")
	public String home() {
		return "redirect:/index";
	}

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		Patient patient = new Patient();

		model.addAttribute("patient", patient);

		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signupPost(@ModelAttribute("patient") Patient patient, Model model) throws ParseException{

			if(patientService.checkPatientExists(patient.getUsername(), patient.getEmail()))  {
	
	            if (patientService.checkEmailExists(patient.getEmail())) {
	                model.addAttribute("emailExists", true);
	            }
	
	            if (patientService.checkUsernameExists(patient.getUsername())) {
	                model.addAttribute("usernameExists", true);
	            }
	            return "signup";
	        } else {
	        	Set<PatientRole> patientRoles = new HashSet<>();
	        	patientRoles.add(new PatientRole(patient, roleDao.findByName("ROLE_USER")));
	
	        	patientService.createUser(patient, patientRoles);
	        	MedicationAccount medication = new MedicationAccount();
	        	medication=medaccount.findByAccountNumberA(patient.getMedicationAccount().getAccountNumber());
	        	medication.setPatient(patient);
	        	medicationD.save(medication);
	
	            return "redirect:/";
	        }
		}
	
	@RequestMapping("/userFront")
	public String userFront(Principal principal, Model model) {
        Patient patient = patientService.findByUsername(principal.getName());
        MedicationAccount medicationAccount = patient.getMedicationAccount();
        model.addAttribute("MyName", principal.getName());
        model.addAttribute("medicationAccount", medicationAccount);

        return "userFront";
    }
	
}
