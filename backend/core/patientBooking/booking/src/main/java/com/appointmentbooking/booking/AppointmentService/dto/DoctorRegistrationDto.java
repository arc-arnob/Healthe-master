package com.appointmentbooking.booking.AppointmentService.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorRegistrationDto {
   
    private String doc_name; //
    private String doc_address;//
    private String doc_phone;//
    private Date dateOfStarting;//
    private String doc_settlePoint;//
    private String doc_gender;//
    private String doc_description;//
    private Date doc_dob;

    private Long docSpecId;//
    private Long clinicId;//
    


}
