package com.appointmentbooking.booking.AppointmentService.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy", timezone = "IST")
    @Temporal(TemporalType.DATE)
    private Date startDate; 

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss", timezone = "IST")
    @Temporal(TemporalType.TIME)
    private Date startTime;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss", timezone = "IST")
    @Temporal(TemporalType.TIME)
    private Date endTime; // +30min

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name="patId")
    // private Patient patient;
    private String pat_username; // this is from userepository

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="docId") // what if we give here userId ?
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="appTypeId")
    private AppointmentType appType;





    
}
