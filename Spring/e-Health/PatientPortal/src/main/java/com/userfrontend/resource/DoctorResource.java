package com.userfrontend.resource;

import com.userfrontend.dao.*;
import com.userfrontend.domain.*;
import com.userfrontend.service.DatesDoctorAvailableService;
import com.userfrontend.service.PatientService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/apiDoctor")
//@CrossOrigin("http://localhost:4200")
@PreAuthorize("hasRole('DOCTOR')")
public class DoctorResource {

    final
    DatesDoctorAvailableService datesDoctorAvailableService;

    final
    DatesDoctorAvailableDao datesDoctorAvailableDao;

    final
    PatientService patientService;

    final
    DoctorDao doctorDao;

    final
    PatientDao patientDao;

    final
    AppointmentDao appDao;

    final
    TreatmentGuideDao tG;

    final
    PatientHealthDao patDao;

    public DoctorResource(DatesDoctorAvailableService datesDoctorAvailableService, DatesDoctorAvailableDao datesDoctorAvailableDao, PatientService patientService, DoctorDao doctorDao, PatientDao patientDao, AppointmentDao appDao, TreatmentGuideDao tG, PatientHealthDao patDao) {
        this.datesDoctorAvailableService = datesDoctorAvailableService;
        this.datesDoctorAvailableDao = datesDoctorAvailableDao;
        this.patientService = patientService;
        this.doctorDao = doctorDao;
        this.patientDao = patientDao;
        this.appDao = appDao;
        this.tG = tG;
        this.patDao = patDao;
    }

    // get Dates of patients
    @RequestMapping(value = "/doctor/{username}/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DatesDoctorAvailable> getDatesDoctorAvailable(@PathVariable("username") String username) {
        Patient p = patientDao.findByUsername(username);
        return datesDoctorAvailableDao.findAllDoctor(p.getPatientID());
    }

    // Patient Health View in the List View "when presses edit element"
    @RequestMapping(value = "/doctor/{username}/{app_id}/fetch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPatientAvailable(@PathVariable("app_id") String patId) throws JSONException {

        Appointment appPat = appDao.findBasedOnPatienthealth(Long.parseLong(patId));

        Optional<TreatmentGuide> tGI = tG.findById(appPat.getTreatmentGuide().getId());
        TreatmentGuide tGuide = new TreatmentGuide();
        if (tGI.isPresent()) {
            tGuide = tGI.get();
        }

        Optional<PatientHealth> pTI = patDao.findById(appPat.getPatienthealth().getId());
        PatientHealth patH = new PatientHealth();
        if (pTI.isPresent()) {
            patH = pTI.get();
        }

        HashMap<String, String> patientDetailsHashMap
                = new LinkedHashMap<String, String>();

        // []
        JSONArray jsonArrayOfObjsPatientDetails = new JSONArray();
        // {}
        JSONObject jsonObjectBuild = new JSONObject();

        patientDetailsHashMap.put("presription", tGuide.getPrescritpionDirections());
        patientDetailsHashMap.put("diagnosed", patH.getPatientDiagnosed());

        // build Obj of the Array jsonArrayOfObjsPatientDetails
        for (Map.Entry<String, String> stringStringEntry : patientDetailsHashMap.entrySet()) {
            jsonObjectBuild.put(String.valueOf(((Map.Entry) stringStringEntry).getKey()), String.valueOf(((Map.Entry) stringStringEntry).getValue()));
        }
        // {} to -> [{}]
        jsonArrayOfObjsPatientDetails.put(jsonObjectBuild);

        // [0:{},1:{[]},...]
        return jsonArrayOfObjsPatientDetails.toString();
    }

    // List View
    // get Patients that have scheduled an Appointment and send them to View of DoctorPortal Inline-Appointment endpoint
    @RequestMapping(value = "/doctor/{username}/inline", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPatientInline(@PathVariable("username") String username) throws JSONException {

        // get Doctor Account
        Patient doctorAccountAppointments = patientDao.findByUsername(username);
        List<Appointment> appointmentsList = appDao.findDoctorInline1(doctorAccountAppointments.getPatientID());

        HashMap<String, String> patientDetailsHashMap
                = new LinkedHashMap<String, String>();

        // {}
        //JSONObject jsonObjectTest = new JSONObject();
        // []
        JSONArray jsonArrayOfObjsPatientDetails = new JSONArray();


        // per patient Details get list of scheduled appointment
        for (Appointment appointment : appointmentsList) {
            // obj of the Outter Array Json
            JSONObject jsonObjectBuild = new JSONObject();
            patientDetailsHashMap.put("email", appointment.getPatientAccount().getEmail());
            patientDetailsHashMap.put("treatmentguide_id", appointment.getTreatmentGuide().getId().toString());
            patientDetailsHashMap.put("medication_account_id", appointment.getTreatmentGuide().getMedicationAccount().getId().toString());
            patientDetailsHashMap.put("username",
                    appointment.getPatientAccount().getUsername());
            patientDetailsHashMap.put("phone",
                    appointment.getPatientAccount().getPhone());
            patientDetailsHashMap.put("time", appointment.getDatesDoctorAvailable().getTimeAvailable());
            patientDetailsHashMap.put("date", appointment.getDatesDoctorAvailable().getDateAvailable().toString());
            //patientDetailsHashMap.put("app_id", appointment.getPatientAccount().getAppointmentList().toString()); // appointment id
            patientDetailsHashMap.put("app_id", appointment.getPatienthealth().getId().toString()); // patient-health id


            // build Obj of the Array jsonArrayOfObjsPatientDetails
            for (Map.Entry<String, String> stringStringEntry : patientDetailsHashMap.entrySet()) {
                jsonObjectBuild.put(String.valueOf(((Map.Entry) stringStringEntry).getKey()), String.valueOf(((Map.Entry) stringStringEntry).getValue()));
            }
            jsonArrayOfObjsPatientDetails.put(jsonObjectBuild);
        }
        // [0:{},1:{[]},...]
        return jsonArrayOfObjsPatientDetails.toString();
    }

    // Doctor Writes to patient prescription Directions in inline-appointment (Component) DoctorPortal Endpoint
    @RequestMapping(value = "/doctor/setDirections", method = RequestMethod.POST, consumes = "application/json")
    public void setPatientDirections(@RequestBody String localAssocArray) throws JSONException {
        JSONObject jsonObject = new JSONObject(localAssocArray);
        String diagnosed = (jsonObject.get("diagnosed").toString());
        String prescriptions = (jsonObject.get("prescriptions").toString());
        Long patientHealthId = Long.parseLong(jsonObject.get("patientHealth").toString());
        System.out.println(patientHealthId);

        Appointment appPat = appDao.findBasedOnPatienthealth(patientHealthId);

        Optional<TreatmentGuide> tGI = tG.findById(appPat.getTreatmentGuide().getId());
        TreatmentGuide tGuide = new TreatmentGuide();
        if (tGI.isPresent()) {
            tGuide = tGI.get();
        }

        Optional<PatientHealth> pTI = patDao.findById(appPat.getPatienthealth().getId());
        PatientHealth patH = new PatientHealth();
        if (pTI.isPresent()) {
            patH = pTI.get();
        }

        tGuide.setPrescritpionDirections(prescriptions);
        patH.setPatientDiagnosed(diagnosed);
        patDao.save(patH);
        tG.save(tGuide);
    }

    // Doctor Creates Free appointment
    @RequestMapping(value = "/doctor/setAppointment", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void setDatesDoctorAvailable(@RequestBody String datesAvailable) throws JSONException {
        JSONObject datesTimeAvailableDoctor = new JSONObject(datesAvailable);
        String doctorAcc = (datesTimeAvailableDoctor.getJSONObject("doc").get("doctorAccount").toString());
        Patient p = patientDao.findByUsername(doctorAcc);
        Optional<Doctor> doctor = doctorDao.findById(p.getPatientID());
        Doctor doctorEntity = new Doctor();
        if (doctor.isPresent()) {
            doctorEntity = doctor.get();
        }
        DatesDoctorAvailable datesAvailableNew = new DatesDoctorAvailable(doctorEntity);
        datesAvailableNew.setTimeAvailable(datesTimeAvailableDoctor.get("timeAvailable").toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        LocalDate dateDoctor = LocalDate.parse(datesTimeAvailableDoctor.get("dateAvailable").toString(), formatter);
        datesAvailableNew.setDateAvailable(dateDoctor);
        datesDoctorAvailableDao.save(datesAvailableNew);
    }

}
