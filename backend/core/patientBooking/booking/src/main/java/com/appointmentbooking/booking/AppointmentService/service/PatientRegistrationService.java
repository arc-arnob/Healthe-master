package com.appointmentbooking.booking.AppointmentService.service;

import java.util.Optional;

import com.appointmentbooking.booking.AppointmentService.dto.PatientRegistrationDto;
import com.appointmentbooking.booking.AppointmentService.mapper.PatientRegistrationMapper;
import com.appointmentbooking.booking.AppointmentService.model.AppointmentType;
import com.appointmentbooking.booking.AppointmentService.model.Doctor;
import com.appointmentbooking.booking.AppointmentService.model.Patient;
import com.appointmentbooking.booking.AppointmentService.repository.AppointmentRepository;
import com.appointmentbooking.booking.AppointmentService.repository.AppointmentTypeRepository;
import com.appointmentbooking.booking.AppointmentService.repository.DoctorRepository;
import com.appointmentbooking.booking.AppointmentService.repository.PatientRegistrationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatientRegistrationService {
    
    @Autowired
    private final PatientRegistrationRepository patientRepository;

    
    private PatientRegistrationMapper patientMapper;
    private AuthService authService;
    private DoctorRepository doctorRepository;
    private AppointmentTypeRepository appointmentTypeRepository;

    public String save(PatientRegistrationDto patientDto){
        System.out.println("Here Inside Save of PR Service");

        String user = authService.getCurrentUser().getUsername();
        Optional<Patient> user_check = patientRepository.findByUser(user);

        if(user_check == null){


            Patient patient = patientMapper.dtoToPatient(patientDto, 
            authService.getCurrentUser().getUsername());

            patientRepository.save(patient);
            return "You are registered";
        }
        else{
            return "You are already Registered";
        }
        
    }
        

    public PatientRegistrationDto getPatientDetailsById(){

        String user = authService.getCurrentUser().getUsername();
        Patient patient = patientRepository.findByUser(user)
                        .orElseThrow(() -> new UsernameNotFoundException("Patient not registered"));
        // Doctor doctor = doctorRepository.findById(patient.getDoctor().getDocId())
        //                                 .orElseThrow(()-> new UsernameNotFoundException("Doctor id do not exists"));
        
        // AppointmentType appType = appointmentTypeRepository.findById(patient.getAppointmentType().getAppTypeId())
        //                                                     .orElseThrow(()-> new UsernameNotFoundException("Appointmen Type Id not found"));

        return patientMapper.mapPatientToDto(patient);
    }
    

}
