package com.appointmentbooking.booking.AppointmentService.dto;

import java.sql.Time;
//import java.util.Date;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentRegitrationDto {
    

    private Long docId; // This will be used to find doctor user name from doctorRepository
    private Long appTypeId;
    // @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate; // May give parsing error // last fix = util.time -> sql.time
    private Time startTime; // May give parsing error
    private Time endTime; // May give parsing error


}
