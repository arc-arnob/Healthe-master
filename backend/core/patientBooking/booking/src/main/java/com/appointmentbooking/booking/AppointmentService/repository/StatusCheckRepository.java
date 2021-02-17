package com.appointmentbooking.booking.AppointmentService.repository;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import com.appointmentbooking.booking.AppointmentService.model.StatusCheck;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusCheckRepository extends JpaRepository<StatusCheck, Long> {
    
    @Query(value = "SELECT statusId FROM statuscheck WHERE docId=?2 AND date=?3 AND time=?1",nativeQuery = true)
    Long getStatusChecked(Date time, Long docId, Date date);

    @Query(value = "SELECT date FROM statuscheck WHERE statusId=30", nativeQuery = true)
    Date getDateForTesting();

    @Query(value = "DELETE FROM statuscheck WHERE docId=?1",nativeQuery = true)
    @Modifying
    @Transactional
    void deleteBydocId(Long docId);

}
