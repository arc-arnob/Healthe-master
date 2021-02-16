package com.appointmentbooking.booking.AppointmentService.dto;

import java.sql.Time;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusDto {
    
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss", timezone = "IST")
    private Time time;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;
    private Long docId;
    
}
