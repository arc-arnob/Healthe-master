package com.appointmentbooking.booking.AppointmentService.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.appointmentbooking.booking.AppointmentService.dto.DoctorRegistrationDto;
import com.appointmentbooking.booking.AppointmentService.mapper.DoctorRegistrationMapper;
import com.appointmentbooking.booking.AppointmentService.model.Appointment;
import com.appointmentbooking.booking.AppointmentService.model.Clinic;
import com.appointmentbooking.booking.AppointmentService.model.Doctor;
import com.appointmentbooking.booking.AppointmentService.model.DoctorSpeciality;
import com.appointmentbooking.booking.AppointmentService.repository.AppointmentRepository;
import com.appointmentbooking.booking.AppointmentService.repository.ClinicRepository;
import com.appointmentbooking.booking.AppointmentService.repository.DoctorRepository;
import com.appointmentbooking.booking.AppointmentService.repository.DoctorSpecialityRepository;
import com.appointmentbooking.booking.AppointmentService.repository.StatusCheckRepository;

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
    private AppointmentRepository appointmentRepository;
    private StatusCheckRepository statusCheckRepository;


    public String save(DoctorRegistrationDto doctorDto){

        String user = authService.getCurrentUser().getUsername();

        Doctor user_check = doctorRepository.findByUserId(user).orElse(null);
        System.out.println(user_check);
        if(user_check  ==null){

            Clinic clinic = clinicRepository.findById(doctorDto.getClinicId())
                            .orElseThrow(() -> new UsernameNotFoundException("Clinic Id does not exists"));

            DoctorSpeciality docSpec = doctorSpecialityRepository.findById(doctorDto.getDocSpecId())
                                        .orElseThrow(() -> new UsernameNotFoundException("Doctor Speciality id does not exist"));

            Doctor doctor = doctorMapper.dtoToDoctor(doctorDto, authService.getCurrentUser().getUsername(), docSpec, clinic);
            String docId = UUID.randomUUID().toString();
            System.out.println("&*&*&*&*&*&*&*&*&*&*&*&*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println(docId);
            doctor.setDocId(docId);
            doctorRepository.save(doctor);
            return "You are Registered as a Doctor";
        }
        else{
            return "You are already Registered";
        }
    }

    // Edit doctor's profile
    
    public String updateDoctorProfile(DoctorRegistrationDto doctorDto){

        Doctor user = doctorRepository.findByUserId(authService.getCurrentUser().getUsername())
                                        .orElseThrow(()-> new UsernameNotFoundException("Does not Exist"));
        String id = user.getDocId();

        Clinic clinic = clinicRepository.findById(doctorDto.getClinicId())
                            .orElseThrow(() -> new UsernameNotFoundException("Clinic Id does not exists"));

        DoctorSpeciality docSpec = doctorSpecialityRepository.findById(doctorDto.getDocSpecId())
                                    .orElseThrow(() -> new UsernameNotFoundException("Doctor Speciality id does not exist"));

        Doctor doctor = doctorMapper.dtoToDoctor(doctorDto, authService.getCurrentUser().getUsername(), docSpec, clinic);
        doctor.setDocId(id);
        doctorRepository.save(doctor);
        return "You Updated Your Profile as a doctor";

    }

    public String deleteDoctorInfo(){
        Doctor user = doctorRepository.findByUserId(authService.getCurrentUser().getUsername())
                                        .orElseThrow(()->new UsernameNotFoundException("User name not found"));
        
        String docId = user.getDocId();

        List<Appointment> appointment = appointmentRepository.findBydocId(docId);

        System.out.println("Size of App list is *(*(*(*(*(*(*"+appointment.size());//working

        if(appointment.size() > 0){
            appointmentRepository.deleteBydocId(docId);
            statusCheckRepository.deleteBydocId(docId);
        }

        doctorRepository.delete(user);
        return "You Registration is Deleted";
    }
    



}
