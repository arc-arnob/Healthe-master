package com.diagnosis_gateway.gateway.controller;

import java.util.List;

import com.diagnosis_gateway.gateway.dto.DiabetesPYDto;
import com.diagnosis_gateway.gateway.dto.StrokeDto;
import com.diagnosis_gateway.gateway.model.Diabetes;
import com.diagnosis_gateway.gateway.model.NotificationEmail;
import com.diagnosis_gateway.gateway.model.Stroke;
import com.diagnosis_gateway.gateway.service.AuthService;
import com.diagnosis_gateway.gateway.service.DiabetesService;
import com.diagnosis_gateway.gateway.service.StrokesService;
import com.netflix.ribbon.proxy.annotation.Http.HttpMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/diagnosis")
public class DiagnosisController {
    @Autowired
    private DiabetesService diabetesService;

    @Autowired
    private StrokesService strokeService;

    @Autowired
    LoadBalancerClient loadBalancer;
    @Autowired
    RestTemplate restTemplate;
    



    @PostMapping("/patient/getDiabetesdiagnosis")
    public Double[] saveDiagnosis(@RequestBody Diabetes diabetes){

        String status = diabetesService.save(diabetes);
        Integer id = diabetes.getId();
        System.out.println("id for diabetes isssss "+id);
        // maptoDto.
        DiabetesPYDto dto = new DiabetesPYDto();
        dto.setGlucose(diabetes.getGlucose());
        dto.setAge(diabetes.getAge());
        dto.setBmi(diabetes.getBMI());
        dto.setInsulin(diabetes.getInsulin());

        String uri = loadBalancer.choose("diabetes-app").getServiceId();
        System.out.println(loadBalancer.choose("diabetes-app").getHost());
        System.out.println(loadBalancer.choose("diabetes-app").getPort());
        String url = "http://"+uri.toString() + "/diabetesapi/status/";
        System.out.println(url);
        ResponseEntity<Double[]> responseEntity = restTemplate.postForEntity(url,dto,Double[].class);
        Double[] patient = responseEntity.getBody();

        Integer outcome = patient[0].intValue();

        diabetesService.fetchAndSaveOutcomes(id, outcome, patient[1]);

        NotificationEmail email = diabetesService.sendMailIfPatient(diabetes, outcome, patient[1]);
        System.out.println("GOOOD to GO!");
        String mail_uri = loadBalancer.choose("mailing-service").getServiceId();
        String mail_url = "http://"+mail_uri.toString() + "/sendmail";
        System.out.println(mail_url);
        ResponseEntity<String> mailResponseEntity = restTemplate.postForEntity(mail_url,email,String.class);        
        
        return patient;

    }

    @GetMapping("/patient/getDiabetesReport")
    public List<Diabetes> getPatientDiabetesReport(){
        
        return diabetesService.getPatientDiabetesReport();
    }

    @PostMapping("patient/getStrokeDiagnosis")
    public Double[] saveAndGetDiagnosis(@RequestBody Stroke body){

        String status = strokeService.save(body);
        Integer id = body.getId();
        
        // Can use mapstruct
        StrokeDto dto = new StrokeDto();
        dto.setGender(body.getGender());
        dto.setEver_married(body.getEver_married());
        dto.setWork_type(body.getWork_type()); 
        dto.setResidence_type(body.getResidence_type());
        dto.setSmoking_status(body.getSmoking_status());
        dto.setAge(body.getAge());
        dto.setHypertension(body.getHypertension());
        dto.setHeart_disease(body.getHeart_disease());
        dto.setAvg_glucose_level(body.getAvg_glucose_level());
        dto.setBmi(body.getBmi());

        String uri = loadBalancer.choose("stroke-app").getServiceId();
        String url = "http://"+uri.toString() + "/strokeapi/status/";
        System.out.println(url);
        ResponseEntity<Double[]> responseEntity = restTemplate.postForEntity(url,dto,Double[].class);
        Double[] patient = responseEntity.getBody();
        Integer outcome = patient[0].intValue();
        Double probability = patient[1];
        strokeService.fetchAndSaveOutcomes(id, outcome, probability);

        NotificationEmail email = strokeService.sendMailIfPatient(body, outcome, probability);
        System.out.println("GOOOD to GO!");
        String mail_uri = loadBalancer.choose("mailing-service").getServiceId();
        String mail_url = "http://"+mail_uri.toString() + "/sendmail";
        System.out.println(mail_url);
        ResponseEntity<String> mailResponseEntity = restTemplate.postForEntity(mail_url,email,String.class);
        return patient;
    }

    @GetMapping("/patient/getStrokeReport")
    public List<Stroke> getPatientStrokeReport(){
        
        return strokeService.getPatientStrokeReport();
    }

    // save results in db, outcome and probability -- Done
    // need to make a doctor diagnosis facility too. Here patient username has to be passed...
    // ... in json too

    @PostMapping("/doctor/getDiabetesdiagnosis")
    public Double[] saveDiagnosisByDoctor(@RequestBody Diabetes diabetes){

        String status = diabetesService.saveByDoc(diabetes);
        Integer id = diabetes.getId();
        System.out.println("id for diabetes isssss "+id);
        // maptoDto.
        DiabetesPYDto dto = new DiabetesPYDto();
        dto.setGlucose(diabetes.getGlucose());
        dto.setAge(diabetes.getAge());
        dto.setBmi(diabetes.getBMI());
        dto.setInsulin(diabetes.getInsulin());

        String uri = loadBalancer.choose("diabetes-app").getServiceId();
        System.out.println(loadBalancer.choose("diabetes-app").getHost());
        System.out.println(loadBalancer.choose("diabetes-app").getPort());
        String url = "http://"+uri.toString() + "/diabetesapi/status/";
        System.out.println(url);
        ResponseEntity<Double[]> responseEntity = restTemplate.postForEntity(url,dto,Double[].class);
        Double[] patient = responseEntity.getBody();

        Integer outcome = patient[0].intValue();

        diabetesService.fetchAndSaveOutcomes(id, outcome, patient[1]);
        
        return patient;

    }

    @PostMapping("doctor/getStrokeDiagnosis/")
    public Double[] saveAndGetDiagnosisByDoctor(@RequestBody Stroke body){

        String status = strokeService.saveByDoc(body);
        Integer id = body.getId();
        
        // Can use mapstruct
        StrokeDto dto = new StrokeDto();
        dto.setGender(body.getGender());
        dto.setEver_married(body.getEver_married());
        dto.setWork_type(body.getWork_type()); 
        dto.setResidence_type(body.getResidence_type());
        dto.setSmoking_status(body.getSmoking_status());
        dto.setAge(body.getAge());
        dto.setHypertension(body.getHypertension());
        dto.setHeart_disease(body.getHeart_disease());
        dto.setAvg_glucose_level(body.getAvg_glucose_level());
        dto.setBmi(body.getBmi());

        String uri = loadBalancer.choose("stroke-app").getServiceId();
        String url = "http://"+uri.toString() + "/strokeapi/status/";
        System.out.println(url);
        ResponseEntity<Double[]> responseEntity = restTemplate.postForEntity(url,dto,Double[].class);
        Double[] patient = responseEntity.getBody();
        Integer outcome = patient[0].intValue();
        Double probability = patient[1];
        strokeService.fetchAndSaveOutcomes(id, outcome, probability);
        return patient;
    }

}
