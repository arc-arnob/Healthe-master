package com.appointmentbooking.booking.AppointmentService.controller;

import com.appointmentbooking.booking.AppointmentService.dto.AppointmentRegitrationDto;
import com.appointmentbooking.booking.AppointmentService.dto.DoctorRegistrationDto;
import com.appointmentbooking.booking.AppointmentService.dto.PatientRegistrationDto;
import com.appointmentbooking.booking.AppointmentService.service.AppointmentService;
import com.appointmentbooking.booking.AppointmentService.service.DoctorRegistrationService;
import com.appointmentbooking.booking.AppointmentService.service.PatientRegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/secured")
public class SecuredController {

    @Autowired
    private PatientRegistrationService patientService;
    @Autowired
    private DoctorRegistrationService doctorRegistration;
    @Autowired
    private AppointmentService appService;


    @PostMapping(value="/patient/register")
    public String savePatient(@RequestBody PatientRegistrationDto patientDto) {
        
        System.out.println("Here inside /patient/register");

        patientService.save(patientDto);
        return "Saved Man!";
    }

    @GetMapping(value = "/patient/profile")
    public ResponseEntity<PatientRegistrationDto> getPatientById(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientService.getPatientDetailsById());

    }

    @PostMapping(value = "/doctor/register")
    public String registerDoc(@RequestBody DoctorRegistrationDto doctorDto){

        doctorRegistration.save(doctorDto);
        return "SAVED SUCCESSFULLY";

    }

    @PostMapping(value = "/patient/appointmentbooking")
    public String appointmentBooking(@RequestBody AppointmentRegitrationDto appointmentRegitrationDto){
        appService.saveAppointment(appointmentRegitrationDto);
        return "Appointment Book";
    }

    
    
    

}
