package com.appointmentbooking.booking.AppointmentService.repository;

import com.appointmentbooking.booking.AppointmentService.model.DoctorSpeciality;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorSpecialityRepository extends JpaRepository<DoctorSpeciality, Long>{
    
}
