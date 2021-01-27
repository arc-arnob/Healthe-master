package com.appointmentbooking.booking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Patient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patId;

    private Long userId; // This has to be fetched from the auth-server

    private String name;
    private String phone;
    private String dob;
    private String enrollDate;
    private String gender;
    private String insured;
    
    @ManyToOne
    @JoinColumn(name="docId")
    private Doctor doctor;
    
    @ManyToOne
    @JoinColumn(name="docSpecId")
    private DoctorSpeciality doctorSpeciality;

    @ManyToOne
    @JoinColumn(name="appTypeId")
    private AppointmentType appointmentType;

    
}   
