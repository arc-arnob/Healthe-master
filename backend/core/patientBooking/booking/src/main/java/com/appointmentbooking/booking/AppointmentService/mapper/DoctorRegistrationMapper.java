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
    // @Mapping(target="doc_dob", source="doc_dob")
    // @Mapping(target="doc_description", source = "doc_description")
    // @Mapping(target = "settlePoint", source="doc_settlePoint")
    Doctor dtoToDoctor(DoctorRegistrationDto doctorRegistrationDto,
                        String user,
                        DoctorSpeciality docSpec,
                        Clinic clinic);




    
}
