package com.userfrontend.controller;

import com.userfrontend.dao.AppointmentDao;
import com.userfrontend.domain.Patient;
import com.userfrontend.service.AccountService;
import com.userfrontend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.transaction.Transactional;
import java.security.Principal;


@Transactional
@Controller
@RequestMapping("/account")
public class AccountController {

    final
    AccountService accountService;

    final
    PatientService patientService;

    final
    AppointmentDao appDao;

    Patient patient;

    @Autowired
    public AccountController(AccountService accountService,
                             PatientService patientService,
                             AppointmentDao appDao) {
        this.accountService = accountService;
        this.patientService = patientService;
        this.appDao = appDao;
    }

    // get Medical Template
    @RequestMapping(value = "/medicationAccount", method = RequestMethod.GET)
    public String medicationAccountFetch(Principal principal, Model model) {
        Patient patient = patientService.findByUsername(principal.getName());
        model.addAttribute("medsav", appDao.findAllforPatient(patient.getPatientID()));
        model.addAttribute("MyName", principal.getName());
        return "medical";
    }

    // get personalinformation Template
    @RequestMapping("/personalAccount")
    public String personalAccount(Principal principal, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        patient = (Patient) auth.getPrincipal();

        Patient patientUser = patientService.findById(patient.getPatientID());

        model.addAttribute("FirstName", patientUser.getFirstName());
        model.addAttribute("LastName", patientUser.getLastName());
        model.addAttribute("UserName", patientUser.getUsername());
        model.addAttribute("Phone", patientUser.getPhone());
        model.addAttribute("Email", patientUser.getEmail());
        model.addAttribute("City", patientUser.getCity());
        model.addAttribute("Street", patientUser.getStreetAddress());
        model.addAttribute("Date", patientUser.getDateOfBirth());

        model.addAttribute("MyName", principal.getName());

        return "personalinformation";
    }

    // Post Form User Account Update all fields (update user) .. div input "name" must match with below ModeAttr
    @RequestMapping(value = "/personalAccount", method = RequestMethod.POST)
    public String setUpdatePersonal(
            @ModelAttribute("firstNameU") String firstname,
            @ModelAttribute("lastNameU") String lastname,
            @ModelAttribute("cityU") String city,
            @ModelAttribute("phoneU") String phone,
            @ModelAttribute("streetU") String street,
            Principal principal, Model model) {

        Patient patientUser = patient;

        // if its not number input fields AND everything is not empty on POST
        if (!AccountService.isNumeric(firstname) && firstname != null && !firstname.isEmpty()
                && !AccountService.isNumeric(lastname) && lastname != null && !lastname.isEmpty()
                && !AccountService.isNumeric(city) && city != null && !city.isEmpty()
                && AccountService.isNumeric(phone) && phone != null && !phone.isEmpty()) {
            accountService.patientAccountUpdate(firstname, lastname, city, phone, street, patientUser.getPatientID());
        } else {
            // do stuff e.g. send to Javascript alert message
            System.out.println("information not updated");
        }

        model.addAttribute("MyName", principal.getName());

        patientUser = patientService.findById(patient.getPatientID());

        model.addAttribute("FirstName", patientUser.getFirstName());
        model.addAttribute("LastName", patientUser.getLastName());
        model.addAttribute("UserName", patientUser.getUsername());
        model.addAttribute("Phone", patientUser.getPhone());
        model.addAttribute("Email", patientUser.getEmail());
        model.addAttribute("City", patientUser.getCity());
        model.addAttribute("Street", patientUser.getStreetAddress());
        model.addAttribute("Date", patientUser.getDateOfBirth());
        model.addAttribute("firstNameU", "");
        model.addAttribute("lastNameU", "");
        model.addAttribute("phoneU", "");
        model.addAttribute("cityU", "");
        model.addAttribute("streetU", "");

        return "personalinformation";
    }

}
