package com.userfrontend.controller;

import com.userfrontend.dao.TreatmentGuideDao;
import com.userfrontend.domain.*;
import com.userfrontend.service.AppointmentService;
import com.userfrontend.service.DatesDoctorAvailableService;
import com.userfrontend.service.PatientHealthService;
import com.userfrontend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.transaction.Transactional;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Transactional
@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    final
    TreatmentGuideDao treatmentGuideDao;
    private final PatientHealthService patientHealthService;
    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final DatesDoctorAvailableService datesDoctorAvailableService;

    @Autowired
    public AppointmentController(TreatmentGuideDao treatmentGuideDao, PatientHealthService patientHealthService,
                                 AppointmentService appointmentService, PatientService patientService,
                                 DatesDoctorAvailableService datesDoctorAvailableService) {
        this.treatmentGuideDao = treatmentGuideDao;
        this.patientHealthService = patientHealthService;
        this.appointmentService = appointmentService;
        this.patientService = patientService;
        this.datesDoctorAvailableService = datesDoctorAvailableService;
    }

    // get main Page of Patient Appointments already scheduled
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String getAppointment(Principal principal, Model model) {
        model.addAttribute("MyName", principal.getName());
        Patient patient = patientService.findByUsername(principal.getName());
        model.addAttribute("datesav", datesDoctorAvailableService.FindAllNotWithDescriptionQuery(patient.getPatientID()));

        return "myappointment";
    }

    // delete scheduled appointments
    @RequestMapping(value = "/show/scheduled/{id}", method = RequestMethod.GET)
    public String getAppointmentDel(@PathVariable String id, @ModelAttribute("appointment") Appointment appointment,
                                    @ModelAttribute("datess.dateID") DatesDoctorAvailable datesDoctoravailable) {

        Appointment appointment1 = appointmentService.findByDatesDoctorAvailable(datesDoctorAvailableService.findByDateID(Long.parseLong(id)));
        TreatmentGuide tGuide;
        tGuide = appointment1.getTreatmentGuide();
        treatmentGuideDao.deleteTguideById(tGuide.getId());
        appointmentService.deleteScheduleById(appointment1.getId());

        return "redirect:/appointment/show";
    }

    // Page [pre-search submit] Available Appointments to load Data
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String searchAppointment(Principal principal, Model model) {
        model.addAttribute("MyName", principal.getName());
        Appointment appointment = new Appointment();
        model.addAttribute("appointment", appointment);
        model.addAttribute("dateString", "");

        List<Object[]> uniqueMsgs = new ArrayList<Object[]>(datesDoctorAvailableService.FindAllWithDescriptionQuery());

        model.addAttribute("locations", uniqueMsgs);
        return "appointment";
    }


    // see available Appointments
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String searchAppointmentPost(@ModelAttribute("appointment") Appointment appointment,
                                        @ModelAttribute("dateStringFrom") String dateFrom,
                                        @ModelAttribute("dateStringTo") String dateTo, @ModelAttribute("location") String location,
                                        @ModelAttribute("gender") String gen, Model model, Principal principal) throws ParseException {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = format1.parse(dateFrom);
        Date d2 = format1.parse(dateTo);
        DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String d11 = outputFormatter.format(d1);
        String d22 = outputFormatter.format(d2);
        LocalDate fd1 = LocalDate.parse(d11);
        LocalDate fd2 = LocalDate.parse(d22);
        model.addAttribute("datesav", datesDoctorAvailableService.FindAllWithDescriptionQuery(fd1, fd2, location));
        model.addAttribute("MyName", principal.getName());

        return "appointment";
    }

    // make an appointment scheduled
    @RequestMapping(value = "/create/scheduled/{id}", method = RequestMethod.GET)
    public String doSchedule(@PathVariable String id, Principal principal, @ModelAttribute("appointment") Appointment appointment,
                             @ModelAttribute("datess.dateID") DatesDoctorAvailable datesDoctoravailable) {

        Patient patient = patientService.findByUsername(principal.getName());
        datesDoctorAvailableService.findAll();

        appointment.setPatientAccount(patient);

        Doctor doctor;
        doctor = datesDoctorAvailableService.findByDateID(Long.parseLong(id)).getDoctorAccount();

        appointment.setDoctorAccount(doctor);
        appointment.setDatesDoctorAvailable(datesDoctorAvailableService.findByDateID(Long.parseLong(id)));

        PatientHealth patientHealth = new PatientHealth();
        patientHealth.setPatientAccount(patient);
        patientHealth.setAppointment(appointment);
        patientHealthService.createPatientHealth(patientHealth);
        TreatmentGuide tGuide = new TreatmentGuide();
        tGuide.setDoctorAccount(doctor);
        tGuide.setMedicationAccount(patient.getMedicationAccount());
        appointment.setTreatmentGuide(tGuide);
        treatmentGuideDao.save(tGuide);
        appointment.setPatienthealth(patientHealth);
        appointmentService.createAppointment(appointment);

        return "redirect:/appointment/create";
    }


}
