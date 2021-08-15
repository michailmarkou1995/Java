package com.userfrontend.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.userfrontend.dao.MedicationAccountDao;
import com.userfrontend.dao.RoleDao;
import com.userfrontend.domain.MedicationAccount;
import com.userfrontend.domain.Patient;
import com.userfrontend.security.PatientRole;
import com.userfrontend.service.AccountService;
import com.userfrontend.service.PatientService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
public class PatientResource {

    @Autowired
    private PatientService patientService;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AccountService medaccount;

    @Autowired
    private MedicationAccountDao medaccount1;

    @Autowired
    private MedicationAccountDao medicationD;


    @PostMapping(path = "/patient/set", consumes = "application/json", produces = "application/json")
    void addUserPatient(@RequestBody Patient patient) {

        if (patientService.checkPatientExists(patient.getUsername(), patient.getEmail())) {

            if (patientService.checkEmailExists(patient.getEmail())) {
//                model.addAttribute("emailExists", true);
            }

            if (patientService.checkUsernameExists(patient.getUsername())) {
//                model.addAttribute("usernameExists", true);
            }
//            return "signup";
        } else {
            Set<PatientRole> patientRoles = new HashSet<>();
            patientRoles.add(new PatientRole(patient, roleDao.findByName("ROLE_USER")));

            patientService.createUser(patient, patientRoles);
            MedicationAccount medication;
            medication = medaccount.findByAccountNumberA(patient.getMedicationAccount().getAccountNumber());
            medication.setPatient(patient);
            medicationD.save(medication);
        }
        // patientService.save(patient);
    }

    @PostMapping(path = "/patient/doctor/set", consumes = "application/json", produces = "application/json")
    void addUserDoctor(@RequestBody String patient) throws JSONException {//string

        JSONObject jsonObject = new JSONObject(patient);
        String catD = jsonObject.getJSONObject("category").get("categoryDoctor").toString();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) -> {

            try {
                return LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                return LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }

        }).create();
        Patient patientClass = gson.fromJson(patient, Patient.class);
        if (patientService.checkPatientExists(patientClass.getUsername(), patientClass.getEmail())) {

            if (patientService.checkEmailExists(patientClass.getEmail())) {
//                model.addAttribute("emailExists", true);
            }

            if (patientService.checkUsernameExists(patientClass.getUsername())) {
//                model.addAttribute("usernameExists", true);
            }
//            return "signup";
        } else {
            Set<PatientRole> patientRoles = new HashSet<>();
            patientRoles.add(new PatientRole(patientClass, roleDao.findByName("ROLE_USER")));

            patientService.createUser(patientClass, patientRoles);
            MedicationAccount medication;
            Optional<MedicationAccount> med = medaccount1.findById(patientClass.getMedicationAccount().getId());
            MedicationAccount medEntity = new MedicationAccount();
            if (med.isPresent()){
                medEntity = med.get();
            }

            medication = medEntity;
            medication.setPatient(patientClass);
            medicationD.save(medication);
        }
        patientService.enableDoctor(patientClass.getUsername(), catD);
    }

    @RequestMapping(value = "/patient/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Patient> patientListGet() {
        return patientService.findPatientList();
    }

    @RequestMapping(value = "/patient/all", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Patient> patientListPost() {
        return patientService.findPatientList();
    }


    @RequestMapping("/patient/{username}/enable")
    public void enableUser(@PathVariable("username") String username) {
        patientService.enablePatient(username);
    }

    @RequestMapping("/patient/{username}/disable")
    public void disableUser(@PathVariable("username") String username) {
        patientService.disablePatient(username);
    }

    @DeleteMapping("/delete-patient/{patientID}")
    public boolean deletePatient(@PathVariable("patientID") long patient_id, Patient patient) {

        patient.setPatientID(patient_id);
        return patientService.deletePatientQ(patient);
    }

    @RequestMapping("/patient/{username}/{category_doctor}/doctor/enable")
    public void enableDoctor(@PathVariable("username") String username, @PathVariable("category_doctor") String catD) {
        patientService.enableDoctor(username, catD);
    }

    @RequestMapping("/patient/{username}/doctor/disable")
    public void disableDoctor(@PathVariable("username") String username) {
        patientService.disableDoctor(username);
    }

}

