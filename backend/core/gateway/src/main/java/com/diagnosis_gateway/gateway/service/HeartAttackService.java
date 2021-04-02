package com.diagnosis_gateway.gateway.service;

import java.util.List;

import com.diagnosis_gateway.gateway.Repository.HeartAttackRepository;
import com.diagnosis_gateway.gateway.model.HeartAttack;
import com.diagnosis_gateway.gateway.model.NotificationEmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class HeartAttackService {
    
    @Autowired
    private AuthService authService;

    @Autowired
    private HeartAttackRepository heartRepo;
    private String tbs_outcome;
    private String tbs_proba;

    public String save(HeartAttack body){

        String username = authService.getCurrentUser().getUsername();
        body.setUsername(username);

        heartRepo.save(body);
        return "Success";
    }

    public String fetchAndSaveOutcomes(Integer id, Integer outcome, Double probability){
        HeartAttack heart = heartRepo.findById(id).orElseThrow(()->new UsernameNotFoundException("No record"));
        heart.setOutcome(outcome);
        heart.setProbability(probability);
        heartRepo.save(heart);

        tbs_outcome = (outcome == 1) ? "Positive" : "Negative";
        tbs_proba = probability.toString().substring(0,6);

        return "Data Saved in db";
    }

    public NotificationEmail sendMailIfPatient(HeartAttack body, Integer outcome, Double probability) {
        String msg = "Hey "+authService.getCurrentUser().getUsername()+" your report came out to be "+tbs_outcome+" and chances of having it is "+tbs_proba;
        NotificationEmail email = new NotificationEmail();
        email.setRecipient(authService.getCurrentUser().getEmail());
        email.setSubject("Your Heart Report");
        email.setBody(msg);
        return email;
    }

    public List<HeartAttack> getPatientHeartReport() {
        String username = authService.getCurrentUser().getUsername();
        List<HeartAttack> body = heartRepo.findByUsername(username);
        return body;
    }

    public String saveByDoc(HeartAttack body) {
        heartRepo.save(body);
        return "Success";
    }

}
