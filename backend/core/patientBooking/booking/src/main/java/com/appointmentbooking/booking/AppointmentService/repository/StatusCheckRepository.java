package com.appointmentbooking.booking.AppointmentService.repository;

import com.appointmentbooking.booking.AppointmentService.model.StatusCheck;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusCheckRepository extends JpaRepository<StatusCheck, Long> {
    
}
