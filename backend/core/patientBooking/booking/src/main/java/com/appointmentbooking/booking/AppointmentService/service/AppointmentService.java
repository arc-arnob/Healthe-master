package com.appointmentbooking.booking.AppointmentService.service;

import com.appointmentbooking.booking.AppointmentService.dto.AppointmentRegitrationDto;
import com.appointmentbooking.booking.AppointmentService.dto.StatusDto;
import com.appointmentbooking.booking.AppointmentService.mapper.AppointmentRegistrationMapper;
import com.appointmentbooking.booking.AppointmentService.model.Appointment;
import com.appointmentbooking.booking.AppointmentService.model.AppointmentType;
import com.appointmentbooking.booking.AppointmentService.model.Doctor;
import com.appointmentbooking.booking.AppointmentService.model.StatusCheck;
import com.appointmentbooking.booking.AppointmentService.repository.AppointmentRepository;
import com.appointmentbooking.booking.AppointmentService.repository.AppointmentTypeRepository;
import com.appointmentbooking.booking.AppointmentService.repository.DoctorRepository;
import com.appointmentbooking.booking.AppointmentService.repository.StatusCheckRepository;

import org.checkerframework.checker.units.qual.A;
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
    // private StatusMapper statusMapper;
    private AuthService authservice;
    // @Autowired
    // private StatusDto statusDto;

    public void saveAppointment(AppointmentRegitrationDto appointmentRegitrationDto){

        Doctor doctor = doctorRepository.findById(appointmentRegitrationDto.getDocId())
                        .orElseThrow(() -> new UsernameNotFoundException("Doctor Id do not exists"));
        AppointmentType appType = appTypeRepo.findById(appointmentRegitrationDto.getAppTypeId())
                                    .orElseThrow(()-> new UsernameNotFoundException("App Type do not exists"));

        
        Appointment appointment = appRegMapper.mapDtoToAppointment(appointmentRegitrationDto, 
                                        authservice.getCurrentUser().getUsername(),
                                        doctor,
                                        appType);

        StatusCheck statusCheck =  new StatusCheck();
        statusCheck.setDocId(appointmentRegitrationDto.getDocId());
        statusCheck.setDate(appointmentRegitrationDto.getStartDate());
        statusCheck.setTime(appointmentRegitrationDto.getStartTime());

        // StatusCheck statusCheck = statusMapper.mapDtoToStatusCheck(statusDto, doctor);

        appointmentRepository.save(appointment);
        statusRepo.save(statusCheck);


    }
    
}
