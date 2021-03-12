package com.diagnosis_gateway.gateway.service;

import java.util.List;

import com.diagnosis_gateway.gateway.Repository.StrokeRepository;
import com.diagnosis_gateway.gateway.model.Diabetes;
import com.diagnosis_gateway.gateway.model.NotificationEmail;
import com.diagnosis_gateway.gateway.model.Stroke;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StrokesService {
    @Autowired
    private AuthService authService;

    @Autowired
    private StrokeRepository strokeRepo;

    private String tbs_outcome;
    private String tbs_proba;

    
    // private NotificationEmail email;

    public String save(Stroke stroke){

        String username = authService.getCurrentUser().getUsername();
        stroke.setUsername(username);

        strokeRepo.save(stroke);
        return "Success";
    }

    public String fetchAndSaveOutcomes(Integer id, Integer outcome, Double probability){
        Stroke stroke = strokeRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("No Record Found"));
        stroke.setStroke(outcome);
        stroke.setProbability(probability);
        strokeRepo.save(stroke);

        tbs_outcome = (outcome == 1) ? "Positive" : "Negative";
        tbs_proba = probability.toString().substring(0,6);

        return "Data Saved in db";
    }

    public List<Stroke> getPatientStrokeReport() {
        String username = authService.getCurrentUser().getUsername();
        List<Stroke> stroke = strokeRepo.findByUsername(username);
        return stroke;
    }

    public String saveByDoc(Stroke body) {
        strokeRepo.save(body);
        return "Success";
    }
    
    public NotificationEmail sendMailIfPatient(Stroke body, Integer outcome, Double probability) {
        String msg = "Hey "+authService.getCurrentUser().getUsername()+" your report came out to be "+tbs_outcome+" and chances of having it is "+tbs_proba;
        NotificationEmail email = new NotificationEmail();
        email.setRecipient(authService.getCurrentUser().getEmail());
        email.setSubject("Your Stroke Report");
        email.setBody(msg);
        return email;
    }

    
}
