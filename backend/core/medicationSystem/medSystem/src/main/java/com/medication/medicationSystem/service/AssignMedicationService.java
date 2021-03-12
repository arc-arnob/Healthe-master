package com.medication.medicationSystem.service;

import java.util.List;

import com.medication.medicationSystem.model.AssignMedication;
import com.medication.medicationSystem.model.Medication;
import com.medication.medicationSystem.model.NotificationEmail;
import com.medication.medicationSystem.repository.MedicationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class AssignMedicationService {
    @Autowired 
    MedicationRepository medicationRepo;

    @Autowired
    private AuthService authService;

    @Autowired
    MongoTemplate mongoTemplate;

    public List<AssignMedication> getAll() {
        
        return medicationRepo.findAll();
    }

    // this is for find nearby store
    public List<Double> getLocation(String patId){
        List<Double> coordinates = medicationRepo.findLocationByPatId(patId);
        return coordinates;
    }

    // only be done by doctor
    public AssignMedication createDoc(AssignMedication document) {
        
        String doc_username = authService.getCurrentUser().getUsername();
        document.setDocId(doc_username);
        return medicationRepo.save(document);
    }
    // only by patient
    public List<Double> getMedicationStore(){
        String role = authService.getCurrentUser().getRoles();
        System.out.println(role+"IN GETMENDOD");
        if(role.equals("PATIENT")){
            String pat_username = authService.getCurrentUser().getUsername();
            System.out.println("Here in getMedicatiorStore1");
            List<AssignMedication> document = medicationRepo.findByPatId(pat_username); //error
            List<Double> pat_location = document.get(0).getCoordinates();

            System.out.println("Here in getMedicatiorStore");

            return pat_location;
        }
        else{
            return null;
        }   
    }

    // by pharmacist
    public String deleteMedicationRecordByPatId(String patId){
        medicationRepo.deleteRecord(patId);
        return "Medicines handovered";
    }

    public NotificationEmail sendMailIfPatient(AssignMedication document) {

        String msg = "Hey "+document.getPatId()+" you have been assigned medicines by "+document.getDocId()+"They are: "+document.getMedication().toString();
        NotificationEmail email = new NotificationEmail();
        email.setRecipient(authService.getCurrentUser().getEmail());
        email.setSubject("Your Medication List");
        email.setBody(msg);
        return email;
    }



}
