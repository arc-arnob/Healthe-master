package com.appointmentbooking.booking.AppointmentService.mapper;

import com.appointmentbooking.booking.AppointmentService.dto.StatusDto;
import com.appointmentbooking.booking.AppointmentService.model.StatusCheck;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusMapper {
    
    StatusDto mapStatusToDto(StatusCheck status);

}
