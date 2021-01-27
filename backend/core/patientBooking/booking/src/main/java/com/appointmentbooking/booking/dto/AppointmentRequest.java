package com.appointmentbooking.booking.dto;

import java.sql.Time;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest {

    private Long pat_id;
    private Long doc_id;
    private LocalDate appointmentDate;
    private Time appointStartTime;
    private Time appointEndTime;

    
}
