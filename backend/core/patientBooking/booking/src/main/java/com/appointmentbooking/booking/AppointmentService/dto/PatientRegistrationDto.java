package com.appointmentbooking.booking.AppointmentService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientRegistrationDto {
    
    private String name;
    private String pat_phone;
    private String pat_dob;
    private String enrollDate;
    private String pat_gender;
    private String insured;

    private Long appTypeId;
    private Long docId;

}
