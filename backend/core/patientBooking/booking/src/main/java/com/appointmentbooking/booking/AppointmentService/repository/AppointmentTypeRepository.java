package com.appointmentbooking.booking.AppointmentService.repository;

import com.appointmentbooking.booking.AppointmentService.model.AppointmentType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentTypeRepository extends JpaRepository<AppointmentType, Long> {
    
}
