package com.appointmentbooking.booking.AppointmentService.model;

import java.sql.Time;
import java.time.Instant;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StatusCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;
    
    @JsonFormat(pattern="yyyy-MM-dd")
    //@Temporal(TemporalType.DATE)
    private Date date;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss", timezone = "IST")
    @Temporal(TemporalType.TIME)
    private Date time;

    private String docId;
}
