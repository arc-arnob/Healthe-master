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
    private String userId; // This has to be get from userRepository

    private String docId; // generate unique id
    private String doc_name;
    private String doc_address;
    private String doc_phone;
    private Date dateOfStarting;
    private String settlePoint; //lon and lat for nearby mongodb
    private Date doc_dob; 
    private String doc_gender;
    private String doc_description;
    
    @ManyToOne
    @JoinColumn(name="docSpecId")
    private DoctorSpeciality doctorSpeciality;
    @ManyToOne
    @JoinColumn(name="clinicId")
    private Clinic clinic;

}
