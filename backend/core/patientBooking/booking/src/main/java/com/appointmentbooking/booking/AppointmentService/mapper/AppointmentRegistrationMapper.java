package com.appointmentbooking.booking.AppointmentService.mapper;

import com.appointmentbooking.booking.AppointmentService.dto.AppointmentRegitrationDto;
import com.appointmentbooking.booking.AppointmentService.model.Appointment;
import com.appointmentbooking.booking.AppointmentService.model.AppointmentType;
import com.appointmentbooking.booking.AppointmentService.model.Doctor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentRegistrationMapper {
    
    AppointmentRegitrationDto mapAppointmentToDto(Appointment appointment);


    @Mapping(target = "doctor", source = "doctor")
    @Mapping(target = "pat_username", source = "user")
    @Mapping(target = "appType", source = "appType")
    Appointment mapDtoToAppointment(AppointmentRegitrationDto appointmentRegitrationDto,
                                        String user,
                                        Doctor doctor,
                                        AppointmentType appType);

}
