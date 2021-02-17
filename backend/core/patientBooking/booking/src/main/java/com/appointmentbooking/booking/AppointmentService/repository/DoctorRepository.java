package com.appointmentbooking.booking.AppointmentService.repository;

import java.util.Optional;


import com.appointmentbooking.booking.AppointmentService.model.Doctor;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByUserId(String user_check);
    
}
