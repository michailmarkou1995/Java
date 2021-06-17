package com.userfrontend.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.userfrontend.dao.AppointmentDao;
import com.userfrontend.dao.TreatmentGuideDao;
import com.userfrontend.domain.Appointment;
import com.userfrontend.domain.DatesDoctorAvailable;
import com.userfrontend.domain.Doctor;
import com.userfrontend.domain.Patient;
import com.userfrontend.domain.PatientHealth;
import com.userfrontend.domain.TreatmentGuide;
import com.userfrontend.service.AppointmentService;
import com.userfrontend.service.DatesDoctorAvailableService;
import com.userfrontend.service.PatientHealthService;
import com.userfrontend.service.PatientService;



@Transactional
@Controller
@RequestMapping("/appointment")
public class AppointmentController {
	
	 @Autowired
	  private PatientHealthService patientHealthService;

    @Autowired
    private AppointmentService appointmentService;
    
    @Autowired
    private AppointmentDao appointmentDao;//del

    @Autowired
    private PatientService patientService;
    
    @Autowired
    private DatesDoctorAvailableService datesDoctorAvailableService;//del
    
    @Autowired
    TreatmentGuideDao treatmentGuideDao;
    
    
    @RequestMapping(value = "/show",method = RequestMethod.GET)
    public String getAppointment(Principal principal, Model model) {
    	model.addAttribute("MyName", principal.getName());
    	Appointment appointment = new Appointment();
    	Patient patient = patientService.findByUsername(principal.getName());
        model.addAttribute("datesav", datesDoctorAvailableService. FindAllNotWithDescriptionQuery(patient.getPatientID()));//works 

        return "myappointment";
    }
    
    @RequestMapping(value="/show/scheduled/{id}" ,method = RequestMethod.GET)///scheduled/${datess.dateID}
    public String getAppointmentDel(Model model, @PathVariable String id, Principal principal, @ModelAttribute("appointment") Appointment appointment, @ModelAttribute("datess.dateID") DatesDoctorAvailable datesDoctoravailable) {
       Patient patient = patientService.findByUsername(principal.getName());
       appointment.getId();

       Appointment appointment1 = appointmentService.findByDatesDoctorAvailable(datesDoctorAvailableService.findByDateID(Long.parseLong(id)));
       appointment  = appointmentService.findByDatesDoctorAvailable(datesDoctorAvailableService.findByDateID(Long.parseLong(id)));
       TreatmentGuide tGuide = new TreatmentGuide();
       tGuide = appointment1.getTreatmentGuide();
       treatmentGuideDao.deleteTguideById(tGuide.getId());
       appointmentService.deleteScheduleById(appointment1.getId()); 
   
        return "redirect:/appointment/show"; 
    }
    
    
    

    @RequestMapping(value = "/create",method = RequestMethod.GET)
    public String searchAppointment(Principal principal, Model model) {
    	model.addAttribute("MyName", principal.getName());
        Appointment appointment = new Appointment();
        DatesDoctorAvailable datesDoctorAvailable = new DatesDoctorAvailable();
        model.addAttribute("appointment", appointment);
        model.addAttribute("dateString", "");

        List<Object[]> uniqueMsgs = new ArrayList<Object[]>(datesDoctorAvailableService.FindAllWithDescriptionQuery());//DatesDoctorAvailable
       
        model.addAttribute("locations", uniqueMsgs);
        return "appointment";
    }
	 

	    @RequestMapping(value = "/create",method = RequestMethod.POST)
	    public String searchAppointmentPost(@ModelAttribute("appointment") Appointment appointment, @ModelAttribute("dateStringFrom") String dateFrom, @ModelAttribute("dateStringTo") String dateTo, @ModelAttribute("location") String location,@ModelAttribute("gender") String gen, Model model, Principal principal) throws ParseException {
	         SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	         Date d1 = format1.parse( dateFrom );
	         Date d2 = format1.parse( dateTo );
	         DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
	         String d11=outputFormatter.format(d1);
	         String d22=outputFormatter.format(d2);
	         LocalDate fd1 = LocalDate.parse(d11);
	         LocalDate fd2 = LocalDate.parse(d22);
	        model.addAttribute("datesav", datesDoctorAvailableService.FindAllWithDescriptionQuery(fd1, fd2, location));//works 

	        return "appointment"; 
	    }
	    
	    @RequestMapping(value="/create/scheduled/{id}" ,method = RequestMethod.GET)
	    public String doSchedule(Model model, @PathVariable String id, Principal principal, @ModelAttribute("appointment") Appointment appointment, @ModelAttribute("datess.dateID") DatesDoctorAvailable datesDoctoravailable) {
	       
	       Patient patient = patientService.findByUsername(principal.getName());
	       datesDoctorAvailableService.findAll();
	      
	       patient.getPatientID();
	       
	       appointment.setPatientAccount(patient);

	       Doctor doctor = new Doctor();
	       doctor = datesDoctorAvailableService.findByDateID(Long.parseLong(id)).getDoctorAccount();
	      
	       appointment.setDoctorAccount(doctor);
	       appointment.setDatesDoctorAvailable(datesDoctorAvailableService.findByDateID(Long.parseLong(id)));
	     
	       PatientHealth patientHealth = new PatientHealth();
	       patientHealth.setPatientAccount(patient);
	       patientHealth.setAppointment(appointment);
	       patientHealthService.createPatientHealth(patientHealth);
	       TreatmentGuide tGuide= new TreatmentGuide();
	       tGuide.setDoctorAccount(doctor);
	       tGuide.setMedicationAccount(patient.getMedicationAccount());
	       appointment.setTreatmentGuide(tGuide);
	       treatmentGuideDao.save(tGuide);
	       appointment.setPatienthealth(patientHealth);
	       appointmentService.createAppointment(appointment);
	       return "redirect:/appointment/create"; 
	    }


}
