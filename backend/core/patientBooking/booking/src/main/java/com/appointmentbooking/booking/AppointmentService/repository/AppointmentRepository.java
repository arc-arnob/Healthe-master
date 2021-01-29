package com.appointmentbooking.booking.AppointmentService.repository;

import com.appointmentbooking.booking.AppointmentService.model.Appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long>{

    
}
