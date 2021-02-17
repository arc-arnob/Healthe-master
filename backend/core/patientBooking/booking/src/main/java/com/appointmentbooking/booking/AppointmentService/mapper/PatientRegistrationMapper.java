package com.appointmentbooking.booking.AppointmentService.mapper;

import com.appointmentbooking.booking.AppointmentService.dto.PatientRegistrationDto;
import com.appointmentbooking.booking.AppointmentService.model.AppointmentType;
import com.appointmentbooking.booking.AppointmentService.model.Doctor;
import com.appointmentbooking.booking.AppointmentService.model.Patient;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientRegistrationMapper {
    
   
    PatientRegistrationDto mapPatientToDto(Patient patient);
    
    @Mapping(target = "user", source = "user")
    Patient dtoToPatient(PatientRegistrationDto patientDto, 
                            String user); 

}
