package com.appointmentbooking.booking.AppointmentService.controller;

import com.appointmentbooking.booking.AppointmentService.dto.PatientRegistrationDto;
import com.appointmentbooking.booking.AppointmentService.service.PatientRegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/patient")
public class SecuredController {

    @Autowired
    private PatientRegistrationService patientService;


    @PostMapping(value="/register")
    public String savePatient(@RequestBody PatientRegistrationDto patientDto) {
        
        System.out.println("Here inside /patient/register");

        patientService.save(patientDto);
        return "Saved Man!";
    }
    
    

}
