package com.appointmentbooking.booking.AppointmentService.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Time;
import java.time.Instant;
import java.util.Date;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long appId;

    private String description;
    private Date startDate; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="patId")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="docId")
    private Doctor doctor;





    
}
