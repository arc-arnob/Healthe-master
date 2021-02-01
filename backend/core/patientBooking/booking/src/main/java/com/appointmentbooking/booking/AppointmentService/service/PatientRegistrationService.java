package com.appointmentbooking.booking.AppointmentService.service;

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

    @Autowired
    private PatientRegistrationMapper patientMapper;
    private AuthService authService;
    private DoctorRepository doctorRepository;
    private AppointmentTypeRepository appointmentTypeRepository;

    public void save(PatientRegistrationDto patientDto){
        System.out.println("Here Inside Save of PR Service");

        Doctor doctor = doctorRepository.findById(patientDto.getDocId())
                        .orElseThrow(()-> new UsernameNotFoundException("Doctor Id Does not Exists"));
        
        AppointmentType appType = appointmentTypeRepository.findById(patientDto.getAppTypeId())
                                .orElseThrow(() -> new UsernameNotFoundException("Appointment Type id Does not exists"));


        Patient patient = patientMapper.dtoToPatient(patientDto, 
        authService.getCurrentUser().getUsername(), doctor, appType);

        patientRepository.save(patient);
    }
        

    public PatientRegistrationDto getPatientDetailsById(){

        String user = authService.getCurrentUser().getUsername();
        Patient patient = patientRepository.findByUser(user)
                        .orElseThrow(() -> new UsernameNotFoundException("Patient not registered"));
        Doctor doctor = doctorRepository.findById(patient.getDoctor().getDocId())
                                        .orElseThrow(()-> new UsernameNotFoundException("Doctor id do not exists"));
        
        AppointmentType appType = appointmentTypeRepository.findById(patient.getAppointmentType().getAppTypeId())
                                                            .orElseThrow(()-> new UsernameNotFoundException("Appointmen Type Id not found"));

        return patientMapper.mapPatientToDto(patient, doctor.getDocId(), appType.getAppTypeId());
    }
    

}
