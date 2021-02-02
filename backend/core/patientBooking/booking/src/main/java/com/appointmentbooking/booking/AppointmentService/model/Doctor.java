package com.appointmentbooking.booking.AppointmentService.model;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long docId;
    private String doc_name;
    private String doc_address;
    private String doc_phone;
    private Date dateOfStarting;
    private String settlePoint; //lon and lat for nearby mongodb
    private Date doc_dob; // FIX REQUIRED
    private String doc_gender;
    private String doc_description;
    private String userId; // This has to be get from userRepository
    @ManyToOne
    @JoinColumn(name="docSpecId")
    private DoctorSpeciality doctorSpeciality;
    @ManyToOne
    @JoinColumn(name="clinicId")
    private Clinic clinic;

}
