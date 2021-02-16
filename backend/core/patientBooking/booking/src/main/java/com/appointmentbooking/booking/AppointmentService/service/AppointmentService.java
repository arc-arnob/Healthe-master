package com.appointmentbooking.booking.AppointmentService.service;

import java.sql.Time;
import java.util.Date;

import com.appointmentbooking.booking.AppointmentService.dto.AppointmentRegitrationDto;
import com.appointmentbooking.booking.AppointmentService.dto.StatusDto;
import com.appointmentbooking.booking.AppointmentService.mapper.AppointmentRegistrationMapper;
import com.appointmentbooking.booking.AppointmentService.mapper.StatusMapper;
import com.appointmentbooking.booking.AppointmentService.model.Appointment;
import com.appointmentbooking.booking.AppointmentService.model.AppointmentType;
import com.appointmentbooking.booking.AppointmentService.model.Doctor;
import com.appointmentbooking.booking.AppointmentService.model.StatusCheck;
import com.appointmentbooking.booking.AppointmentService.repository.AppointmentRepository;
import com.appointmentbooking.booking.AppointmentService.repository.AppointmentTypeRepository;
import com.appointmentbooking.booking.AppointmentService.repository.DoctorRepository;
import com.appointmentbooking.booking.AppointmentService.repository.StatusCheckRepository;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AppointmentTypeRepository appTypeRepo;
    @Autowired
    private StatusCheckRepository statusRepo;

    private AppointmentRegistrationMapper appRegMapper;
    private StatusMapper statusMapper;
    private AuthService authservice;
    // @Autowired
    // private StatusDto statusDto;

    public String saveAppointment(AppointmentRegitrationDto appointmentRegitrationDto){

        // Debug 
        System.out.println("Ok Now How come im in here? appService");

        Doctor doctor = doctorRepository.findById(appointmentRegitrationDto.getDocId())
                        .orElseThrow(() -> new UsernameNotFoundException("Doctor Id do not exists"));
        AppointmentType appType = appTypeRepo.findById(appointmentRegitrationDto.getAppTypeId())
                                    .orElseThrow(()-> new UsernameNotFoundException("App Type do not exists"));

        
        Appointment appointment = appRegMapper.mapDtoToAppointment(appointmentRegitrationDto, 
                                        authservice.getCurrentUser().getUsername(),
                                        doctor,
                                        appType);

        //jwt token do not hold roles;
        // check status first.

        Date date = appointmentRegitrationDto.getStartDate();
        Date time = appointmentRegitrationDto.getStartTime();

        Long docId = appointmentRegitrationDto.getDocId();
        Long statusId = statusRepo.getStatusChecked(time, docId, date);

        Date test = statusRepo.getDateForTesting();

        
        // System.out.println("&*()(*&^%$%^&*()*&^%$%^&*()(*&% date from Query"+ test); // Returning correct format

        // System.out.println("&*()(*&^%$%^&*()*&^%$%^&*()(*&%Stattus IDDDDDDDDDDDD"+statusId);

        // System.out.println("&*()(*&^%$%^&*()*&^%$%^&*()(*&%Stattus date "+date);
        // System.out.println("&*()(*&^%$%^&*()*&^%$%^&*()(*&%Stattus time "+time);
        // System.out.println("&*()(*&^%$%^&*()*&^%$%^&*()(*&%Stattus docd"+docId);
        
        if(statusId == null){

            StatusCheck statusCheck =  new StatusCheck();
            statusCheck.setDocId(appointmentRegitrationDto.getDocId());
            statusCheck.setDate(appointmentRegitrationDto.getStartDate());
            statusCheck.setTime(appointmentRegitrationDto.getStartTime());

            // StatusCheck statusCheck = statusMapper.mapDtoToStatusCheck(statusDto, doctor);

            appointmentRepository.save(appointment);
            statusRepo.save(statusCheck);

            System.out.println("Im even finished executing hahah.. appservice");
            return("Appointment Booked");
        }
        else{
            return("Not Available");
        }
    


    }
    
}
