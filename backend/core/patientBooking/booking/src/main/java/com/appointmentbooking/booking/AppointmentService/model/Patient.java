package com.appointmentbooking.booking.AppointmentService.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.appointmentbooking.booking.UserService.model.User;

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

    // @ManyToOne
    // @JoinColumn(name="userId")
    private String user; // This has to be fetched from the auth-server // unknown entity???
                        //... Cannot join tables from 2 diff datasource.

    private String name;
    private String pat_phone;
    private String pat_dob;
    private String enrollDate;
    private String pat_gender;
    private String insured;
    
    @ManyToOne //This has to be passed after selecting from drop down menu.
    @JoinColumn(name="docId")
    private Doctor doctor;
    
    // @ManyToOne
    // @JoinColumn(name="docSpecId")
    // private DoctorSpeciality doctorSpeciality;

    @ManyToOne
    @JoinColumn(name="appTypeId")
    private AppointmentType appointmentType;

    
}   
