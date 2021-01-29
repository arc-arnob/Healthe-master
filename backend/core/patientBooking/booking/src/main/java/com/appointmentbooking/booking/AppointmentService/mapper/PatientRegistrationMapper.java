package com.appointmentbooking.booking.AppointmentService.mapper;

import com.appointmentbooking.booking.AppointmentService.dto.PatientRegistrationDto;
import com.appointmentbooking.booking.AppointmentService.model.AppointmentType;
import com.appointmentbooking.booking.AppointmentService.model.Doctor;
import com.appointmentbooking.booking.AppointmentService.model.Patient;
import com.appointmentbooking.booking.UserService.model.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientRegistrationMapper {
    
    PatientRegistrationDto mapPatientToDto(Patient patient);
    
    @Mapping(target = "user", source = "user")
    @Mapping(target = "appointmentType", source = "appointmentType")
    @Mapping(target = "doctor", source = "doctor")
    Patient dtoToPatient(PatientRegistrationDto patientDto, 
                            String user, // user id has to sent from authservice.getcurrentUser
                            Doctor doctor, // this can be joined
                            AppointmentType appointmentType); // this can be joined

}
