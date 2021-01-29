package com.appointmentbooking.booking.AppointmentService.repository;

import com.appointmentbooking.booking.AppointmentService.model.Patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRegistrationRepository extends JpaRepository<Patient, Long>{
    
}
