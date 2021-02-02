package com.appointmentbooking.booking.AppointmentService.controller;

import com.appointmentbooking.booking.AppointmentService.dto.DoctorRegistrationDto;
import com.appointmentbooking.booking.AppointmentService.service.DoctorRegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/doctor")
public class docSecController {

    @Autowired
    private DoctorRegistrationService doctorRegistration;

    @PostMapping(value = "/register")
    public String registerDoc(@RequestBody DoctorRegistrationDto doctorDto){

        doctorRegistration.save(doctorDto);
        return "SAVED SUCCESSFULLY";

    }
    
}
