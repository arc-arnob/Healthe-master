package com.appointmentbooking.booking.AppointmentService.mapper;

import com.appointmentbooking.booking.AppointmentService.dto.DoctorRegistrationDto;
import com.appointmentbooking.booking.AppointmentService.model.Clinic;
import com.appointmentbooking.booking.AppointmentService.model.Doctor;
import com.appointmentbooking.booking.AppointmentService.model.DoctorSpeciality;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DoctorRegistrationMapper {

    DoctorRegistrationDto mapdoctorToDto(Doctor doctor);


    @Mapping(target = "userId", source = "user")
    @Mapping(target = "doctorSpeciality", source = "docSpec")
    @Mapping(target="clinic", source = "clinic")
    Doctor dtoToDoctor(DoctorRegistrationDto doctorRegistrationDto,
                        String user,
                        DoctorSpeciality docSpec,
                        Clinic clinic);




    
}
