package com.appointmentbooking.booking.mapper;

import com.appointmentbooking.booking.dto.AppointmentRequest;
import com.appointmentbooking.booking.model.Appointment;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class AppointmentMapper {
    public abstract Appointment map(AppointmentRequest appointmentRequest);
}
