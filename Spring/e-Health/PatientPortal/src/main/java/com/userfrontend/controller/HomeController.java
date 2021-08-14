package com.userfrontend.controller;


import com.userfrontend.dao.MedicationAccountDao;
import com.userfrontend.dao.RoleDao;
import com.userfrontend.domain.MedicationAccount;
import com.userfrontend.domain.Patient;
import com.userfrontend.security.PatientRole;
import com.userfrontend.service.AccountService;
import com.userfrontend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {

    private final PatientService patientService;

    private final RoleDao roleDao;

    private final AccountService medaccount;

    private final MedicationAccountDao medicationD;

    @Autowired
    public HomeController(PatientService patientService, RoleDao roleDao,
                          AccountService medaccount, MedicationAccountDao medicationD) {
        this.patientService = patientService;
        this.roleDao = roleDao;
        this.medaccount = medaccount;
        this.medicationD = medicationD;
    }

    // LandPage Login
    @RequestMapping("/")
    public String home() {
        return "redirect:/index";
    }

    // LandPage Login
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * <p>SignUp Form Page Request Register</p>
     *
     * @param model Dependency injection model obj
     */
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        Patient patient = new Patient();

        // pass to Template Page View Data Obj Holder in patient variable
        model.addAttribute("patient", patient);

        return "signup";
    }

    /**
     * <p>SignUp Form Page Submit new User</p>
     *
     * @param patient gets POST Data from "patient" model from view
     * @return if Register OK to Login Page else user Exists
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPost(@ModelAttribute("patient") Patient patient, Model model) throws ParseException {

        if (patientService.checkPatientExists(patient.getUsername(), patient.getEmail())) {

            // generate error message to view by pass back model Data
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
            MedicationAccount medication;
            medication = medaccount.findByAccountNumberA(patient.getMedicationAccount().getAccountNumber());
            medication.setPatient(patient);
            medicationD.save(medication);

            return "redirect:/";
        }
    }

    /**
     * @param principal Logged in Info in memory
     * @return Main page if Successful Login
     */
    @RequestMapping("/userFront")
    public String userFront(Principal principal, Model model) {
        Patient patient = patientService.findByUsername(principal.getName());
        MedicationAccount medicationAccount = patient.getMedicationAccount();
        model.addAttribute("MyName", principal.getName());
        model.addAttribute("medicationAccount", medicationAccount);

        return "userFront";
    }

}
