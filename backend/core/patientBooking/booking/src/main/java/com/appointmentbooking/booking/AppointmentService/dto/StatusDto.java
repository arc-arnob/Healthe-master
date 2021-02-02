package com.appointmentbooking.booking.AppointmentService.dto;

import java.sql.Time;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusDto {

    private Time time;
    private Date date;
    private Long docId;
    
}
