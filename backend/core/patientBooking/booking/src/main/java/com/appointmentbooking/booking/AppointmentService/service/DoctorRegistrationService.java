package com.appointmentbooking.booking.AppointmentService.service;

import java.util.Optional;

import com.appointmentbooking.booking.AppointmentService.dto.DoctorRegistrationDto;
import com.appointmentbooking.booking.AppointmentService.mapper.DoctorRegistrationMapper;
import com.appointmentbooking.booking.AppointmentService.model.Clinic;
import com.appointmentbooking.booking.AppointmentService.model.Doctor;
import com.appointmentbooking.booking.AppointmentService.model.DoctorSpeciality;
import com.appointmentbooking.booking.AppointmentService.repository.ClinicRepository;
import com.appointmentbooking.booking.AppointmentService.repository.DoctorRepository;
import com.appointmentbooking.booking.AppointmentService.repository.DoctorSpecialityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DoctorRegistrationService {
    
    @Autowired
    private final DoctorRepository doctorRepository;
    
    @Autowired
    private DoctorRegistrationMapper doctorMapper;

    private AuthService authService;
    private DoctorSpecialityRepository doctorSpecialityRepository;
    private ClinicRepository clinicRepository;


    public String save(DoctorRegistrationDto doctorDto){

        String user = authService.getCurrentUser().getUsername();

        Optional<Doctor> user_check = doctorRepository.findByUserId(user);

        if(user_check == null){

            Clinic clinic = clinicRepository.findById(doctorDto.getClinicId())
                            .orElseThrow(() -> new UsernameNotFoundException("Clinic Id does not exists"));

            DoctorSpeciality docSpec = doctorSpecialityRepository.findById(doctorDto.getDocSpecId())
                                        .orElseThrow(() -> new UsernameNotFoundException("Doctor Speciality id does not exist"));

            Doctor doctor = doctorMapper.dtoToDoctor(doctorDto, authService.getCurrentUser().getUsername(), docSpec, clinic);
            
            doctorRepository.save(doctor);
            return "You are Registered as a Doctor";
        }
        else{
            return "You are already Registered";
        }
    }



}
