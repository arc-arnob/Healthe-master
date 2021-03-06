package com.appointmentbooking.booking.AppointmentService.repository;

import java.util.Optional;


import com.appointmentbooking.booking.AppointmentService.model.Doctor;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {

    Optional<Doctor> findByUserId(String user_check);

    
    
}
