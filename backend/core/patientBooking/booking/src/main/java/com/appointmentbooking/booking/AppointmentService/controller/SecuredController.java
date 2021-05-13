package com.appointmentbooking.booking.AppointmentService.controller;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import com.appointmentbooking.booking.AppointmentService.config.Config;
import com.appointmentbooking.booking.AppointmentService.dto.AppointmentRegitrationDto;
import com.appointmentbooking.booking.AppointmentService.dto.DoctorRegistrationDto;
import com.appointmentbooking.booking.AppointmentService.dto.PatientRegistrationDto;
import com.appointmentbooking.booking.AppointmentService.model.Appointment;
import com.appointmentbooking.booking.AppointmentService.model.NotificationEmail;
import com.appointmentbooking.booking.AppointmentService.model.StatusCheck;
import com.appointmentbooking.booking.AppointmentService.service.AppointmentService;
import com.appointmentbooking.booking.AppointmentService.service.DoctorRegistrationService;
import com.appointmentbooking.booking.AppointmentService.service.PatientRegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.amqp.rabbit.annotation.RabbitListener;


@RestController
// @RequestMapping("")
public class SecuredController {

    @Autowired
    private PatientRegistrationService patientService;
    @Autowired
    private DoctorRegistrationService doctorRegistration;
    @Autowired
    private AppointmentService appService;

    private CacheManager cacheManager;

    @Autowired
    LoadBalancerClient loadBalancer;
    @Autowired
    RestTemplate restTemplate;

    
    
    @RabbitListener(queues = Config.QUEUE)
    public void consumeMessageFromQueue(PatientRegistrationDto patientDto){
        PatientRegistrationDto patientrecord = patientDto;

        //System.out.println("Message recieved from queue : " + patientrecord);
         System.out.println("Ok Im passing it");
         System.out.println(patientDto);

        // String status = patientService.save(patientrecord);
        // System.out.println("Status: " + status);
    }
   

    @GetMapping("/")
    public String testing(){
        return "Yes Working";
    }

    // PATIENT CONTROLLER

    @PostMapping("/patient/register") //added in catalog
    public String savePatient(@RequestBody PatientRegistrationDto patientDto) {
        
        System.out.println("Here inside patient/register");
        String status = patientService.save(patientDto);
        return status;
    }

    @PutMapping("/patient/update/profile")
    public String updatePatientProfile(@RequestBody PatientRegistrationDto patientDto){
        System.out.println("Here inside patient/upate/profile");
        String status =patientService.updatePatient(patientDto);
        return status;

    }

    @GetMapping("/patient/profile") //added in catalog
    @Cacheable(value = "Patient", key = "")
    public PatientRegistrationDto getPatientById(){

        // return ResponseEntity
        //         .status(HttpStatus.OK)
        //         .body(patientService.getPatientDetailsById());
        return patientService.getPatientDetailsById();

    }

    

    @PostMapping("/patient/appointmentbooking") //added in catalog
    public String appointmentBooking(@RequestBody AppointmentRegitrationDto appointmentRegitrationDto){
        
        String res = appService.saveAppointment(appointmentRegitrationDto);

        // ************MAILING SERVICE CODE*****************

        if(!res.equals("Not Available")){
            NotificationEmail email = appService.sendMailIfPatient(appointmentRegitrationDto);
            System.out.println("GOOOD to GO!");
            String mail_uri = loadBalancer.choose("mailing-service").getServiceId();
            String mail_url = "http://"+mail_uri.toString() + "/sendmail";
            System.out.println(mail_url);
            ResponseEntity<String> mailResponseEntity = restTemplate.postForEntity(mail_url,email,String.class);
        }


        return res;
    }

    // All appointments made by patient
    @GetMapping("/patient/getappointments")
    public List<Appointment> getAppointments(){
        return appService.getAppointmentsOfaPatient();
    }


    // DOCTOR CONTROLLER


    @PostMapping("/doctor/register")
    public String registerDoc(@RequestBody DoctorRegistrationDto doctorDto){

        String status = doctorRegistration.save(doctorDto);
        return status;

    }

    @PutMapping("/doctor/update/profile")
    public String updateDocProfile(@RequestBody DoctorRegistrationDto doctorDto){
        String status = doctorRegistration.updateDoctorProfile(doctorDto);
        return status;
    }

    @DeleteMapping("/doctor/delete/profile")
    public String deleteDocProfile(){
        String status = doctorRegistration.deleteDoctorInfo();
        return status;
    }

    @GetMapping("/doctor/getschedule")
    public List<StatusCheck> getDocSchedule(){
        List<StatusCheck> result = appService.getDoctorBookings();
        return result;
    }
    

    // Doctor booking status check
    // Status check while booking appointment //keep db using except
    // All appointments made by patient
    // Enable doc id only if registered as doctor

    
    
    

}
