package com.appointmentbooking.booking.AppointmentService.model;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GeneratorType;

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
    private String name;
    private String address;
    private String phone;
    private Date dateOfStarting;
    private String settlePoint; //lon and lat for nearby mongodb
    private Date dob;
    private String gender;
    private String description;
    private String userId; // This has to be get from userRepository
    @ManyToOne
    @JoinColumn(name="docSpecId")
    private DoctorSpeciality doctorSpeciality;
    @ManyToOne
    @JoinColumn(name="clinicId")
    private Clinic clinic;

}
