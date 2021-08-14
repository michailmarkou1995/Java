package com.userfrontend.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.userfrontend.dao.*;
import com.userfrontend.domain.*;
import com.userfrontend.service.DatesDoctorAvailableService;
import com.userfrontend.service.PatientService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@RestController
@RequestMapping("/apiDoctor")
//@CrossOrigin("http://localhost:4200")
@PreAuthorize("hasRole('DOCTOR')")
public class DoctorResource {

    @Autowired
    DatesDoctorAvailableService datesDoctorAvailableService;

    @Autowired
    DatesDoctorAvailableDao datesDoctorAvailableDao;

    @Autowired
    PatientService patientService;

    @Autowired
    DoctorDao doctorDao;

    @Autowired
    PatientDao patientDao;

    @Autowired
    AppointmentDao appDao;

    @Autowired
    TreatmentGuideDao tG;

    @Autowired
    PatientHealthDao patDao;

    @RequestMapping(value = "/doctor/{username}/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DatesDoctorAvailable> getDatesDoctorAvailable(@PathVariable("username") String username) {
        Patient p = patientDao.findByUsername(username);
        return datesDoctorAvailableDao.findAllDoctor(p.getPatientID());
    }

    @RequestMapping(value = "/doctor/{username}/{app_id}/fetch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPatientAvailable(@PathVariable("username") String username, @PathVariable("app_id") String patId) throws JSONException {
        Patient p = patientDao.findByUsername(username);

        Appointment appPat = appDao.findBasedOnPatienthealth(Long.parseLong(patId));
        Optional<TreatmentGuide> tGI = tG.findById(appPat.getTreatmentGuide().getId());
        TreatmentGuide tGuideEntity = tGI.get();
        TreatmentGuide tGuide = tGuideEntity;
        Optional<PatientHealth> pTI = patDao.findById(appPat.getPatienthealth().getId());
        PatientHealth patHEntity = pTI.get();
        PatientHealth patH = patHEntity;
        HashMap<String, String> hm1
                = new LinkedHashMap<String, String>();
        System.out.println();
        JSONArray obj = new JSONArray();
        JSONObject jsonObject3 = new JSONObject();
        hm1.put("presription", tGuide.getPrescritpionDirections());
        hm1.put("diagnosed", patH.getPatientDiagnosed());
        Iterator it = hm1.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            jsonObject3.put(String.valueOf(pairs.getKey()), String.valueOf(pairs.getValue()));
        }
        obj.put(jsonObject3);

        return obj.toString();
    }

    @RequestMapping(value = "/doctor/{username}/inline1", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void getPatientInline1(@PathVariable("username") String username) {
        Patient p = patientDao.findByUsername(username);
        List<String> d = appDao.findDoctorInline(p.getPatientID());

    }

    @RequestMapping(value = "/doctor/{username}/inline", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPatientInline(@PathVariable("username") String username) throws JSONException {

        Patient p = patientDao.findByUsername(username);
        List<Appointment> d = appDao.findDoctorInline1(p.getPatientID());

        HashMap<String, String> hm
                = new HashMap<String, String>();


        HashMap<String, String> hm1
                = new LinkedHashMap<String, String>();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();

        JSONArray obj = new JSONArray();


        for (int i = 0; i < d.size(); i++) {
            JSONObject jsonObject2 = new JSONObject();
            hm1.put("email", d.get(i).getPatientAccount().getEmail());
            hm1.put("treatmentguide_id", d.get(i).getTreatmentGuide().getId().toString());
            hm1.put("medication_account_id", d.get(i).getTreatmentGuide().getMedicationAccount().getId().toString());
            hm1.put("username",
                    d.get(i).getPatientAccount().getUsername());
            hm1.put("phone",
                    d.get(i).getPatientAccount().getPhone());
            hm1.put("time", d.get(i).getDatesDoctorAvailable().getTimeAvailable());
            hm1.put("date", d.get(i).getDatesDoctorAvailable().getDateAvailable().toString());
            hm1.put("time", d.get(i).getDatesDoctorAvailable().getTimeAvailable());
            hm1.put("app_id", d.get(i).getPatientAccount().getAppointmentList().toString());
            hm1.put("app_id", d.get(i).getPatienthealth().getId().toString());


            Iterator it = hm1.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();
                jsonObject2.put(String.valueOf(pairs.getKey()), String.valueOf(pairs.getValue()));
            }
            obj.put(jsonObject2);
        }

        String content = hm.toString();
        List<String> sendJ = new ArrayList<String>();
        sendJ.add(content);

        return obj.toString();
    }

    @RequestMapping(value = "/doctor/setDirections", method = RequestMethod.POST, consumes = "application/json")
    public void setPatientDirections(@RequestBody String localAssocArray) throws JSONException {
        JSONObject jsonObject = new JSONObject(localAssocArray);
        String diagnosed = (jsonObject.get("diagnosed").toString());
        String prescriptions = (jsonObject.get("prescriptions").toString());
        Long patientHealthId = Long.parseLong(jsonObject.get("patientHealth").toString());
        System.out.println(patientHealthId);

        Appointment appPat = appDao.findBasedOnPatienthealth(patientHealthId);
        Optional<TreatmentGuide> tGI = tG.findById(appPat.getTreatmentGuide().getId());
        TreatmentGuide tGuideEntity = tGI.get();
        TreatmentGuide tGuide = tGuideEntity;
        Optional<PatientHealth> pTI = patDao.findById(appPat.getPatienthealth().getId());
        PatientHealth patHEntity = pTI.get();
        PatientHealth patH = patHEntity;
        tGuide.setPrescritpionDirections(prescriptions);
        patH.setPatientDiagnosed(diagnosed);
        patDao.save(patH);
        tG.save(tGuide);
    }


    @RequestMapping(value = "/doctor/setAppointment", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void setDatesDoctorAvailable(@RequestBody String datesAvailable) throws JSONException {
        JSONObject jsonObject = new JSONObject(datesAvailable);
        System.out.println(jsonObject);
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) -> {

            try {
                return LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                return LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }

        }).create();
        DatesDoctorAvailable datesClass = gson.fromJson(datesAvailable, DatesDoctorAvailable.class);//new Gson().fromJson  if not ovveride <--
        String l = (jsonObject.getJSONObject("doc").get("doctorAccount").toString());
        Patient p = patientDao.findByUsername(l);
        Optional<Doctor> doctor = doctorDao.findById(p.getPatientID());
        Doctor doctorEntity = doctor.get();
        DatesDoctorAvailable datesAvailable1 = new DatesDoctorAvailable(doctorEntity);
        datesAvailable1.setTimeAvailable(jsonObject.get("timeAvailable").toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        LocalDate dateDoctor = LocalDate.parse(jsonObject.get("dateAvailable").toString(), formatter);
        datesAvailable1.setDateAvailable(dateDoctor);
        datesDoctorAvailableDao.save(datesAvailable1);
    }

}
