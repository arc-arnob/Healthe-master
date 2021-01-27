package com.appointmentbooking.booking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clinicId;

    private String name;
    private String SettlePoint;
    private String address;
    private String phone;
    private String url;
    private String description;

    @ManyToOne
    @JoinColumn(name="docId")
    private Doctor doctor;
}
