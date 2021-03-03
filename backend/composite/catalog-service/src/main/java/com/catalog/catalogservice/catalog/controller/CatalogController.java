package com.catalog.catalogservice.catalog.controller;

import java.net.URI;
import java.util.List;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.catalog.catalogservice.model.*;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    RestTemplate restTemplate;

    // Perform Load Balancing here.
    @Autowired
    LoadBalancerClient loadBalancer;

    private CacheManager cacheManager;



    // Patient Controllers

    @GetMapping("/patient-profile") //<-- product-composite
    @HystrixCommand(fallbackMethod = "showPPFallback")
    @Cacheable(value="Patient")
    public Object showPatientProfile(@RequestHeader(value = "Authorization") String token){

        System.out.println("Here in catalog service 1");
        System.out.println("autho header: " + token);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entityReq = new HttpEntity<String>("request", headers);

        String uri = loadBalancer.choose("app-service").getServiceId();

        System.out.println(uri);
        //http://localhost:8084/patient/profile
        String url = "http://"+uri.toString() + "/patient/profile";
        System.out.println(url);

         //need to forward auth header--fixed
         ResponseEntity<Object> responseEntity = restTemplate.exchange(url,HttpMethod.GET,entityReq, Object.class);
         Object patient = responseEntity.getBody();
         return patient;
     
    }

    @PostMapping("/patient-register")
    @HystrixCommand(fallbackMethod = "registerPatientFB")
    public String registerPatient(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(dto, headers);

        String uri = loadBalancer.choose("app-service").getServiceId();
        String url = "http://"+uri.toString() + "/patient/register";

        ResponseEntity<String> result = restTemplate
                .postForEntity(url, entity, String.class);

        return result.getBody();
    }

    // Update Patient Profile
    @PutMapping("/patient-update")
    @HystrixCommand(fallbackMethod = "patientProfileUpdateFB")
    @CacheEvict(value = "Patient",allEntries = true) //Not working
    public Object patientProfileUpdate(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(dto, headers);

        String uri = loadBalancer.choose("app-service").getServiceId();
        String url = "http://"+uri.toString() + "/patient/update/profile";

        ResponseEntity<String> result = restTemplate
        .exchange(url, HttpMethod.PUT, entity, String.class);

        Object patient = result.getBody();
        return patient; // Do not forget to update the cache! @cachevict


    }

    
    @PostMapping("/patient-app-booking")
    @HystrixCommand(fallbackMethod = "patientAppointmentBookingFB")
    public String patientAppointmentBooking(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(dto, headers);

        String uri = loadBalancer.choose("app-service").getServiceId();
        String url = "http://"+uri.toString() + "/patient/appointmentbooking";

        ResponseEntity<String> result = restTemplate
                .postForEntity(url, entity, String.class);

        return result.getBody();
    }

    @GetMapping("/patient-get-bookings")
    @HystrixCommand(fallbackMethod = "getPatientAppointmentsFB")
    @Cacheable(value="PatientBookings")
    public Object getPatientAppointments(@RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>("request",headers);

        String uri = loadBalancer.choose("app-service").getServiceId();
        String url = "http://"+uri.toString() + "/patient/getappointments";

        ResponseEntity<Object> result = restTemplate.exchange(url,HttpMethod.GET,entity, Object.class);
        return result.getBody();
    }



    // DOCTOR CONTROLLERSS


    @PostMapping("/doctor-register")
    @HystrixCommand(fallbackMethod = "registerDoctorFB")
    public String registerDoctor(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(dto, headers);

        String uri = loadBalancer.choose("app-service").getServiceId();
        String url = "http://"+uri.toString() + "/doctor/register";

        ResponseEntity<String> result = restTemplate
                .postForEntity(url, entity, String.class);

        return result.getBody();
    }

    @PutMapping("/doctor-update")
    @HystrixCommand(fallbackMethod = "updateDoctorFB")
    @CacheEvict(value="DoctorProfile", allEntries = true)
    public String updateDoctor(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(dto, headers);

        String uri = loadBalancer.choose("app-service").getServiceId();
        String url = "http://"+uri.toString() + "/doctor/update/profile";

        restTemplate
                .put(url, entity, String.class);

        return "Updated";
    }

    @DeleteMapping("/doctor-delete")
    @HystrixCommand(fallbackMethod = "deleteDoctorFB")
    @CacheEvict(value="DoctorProfile", allEntries = true)
    public String deleteDoctor(@RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        String uri = loadBalancer.choose("app-service").getServiceId();
        String url = "http://"+uri.toString() + "/doctor/delete/profile";

        ResponseEntity<String> status = restTemplate.exchange(url,HttpMethod.DELETE, entity, String.class);

        return status.getBody();
    }

    // Doctor Schedule
    @GetMapping("/doctor-schedule")
    @HystrixCommand(fallbackMethod = "doctorScheduleFB")
    @Cacheable(value="docschedule")
    public Object doctorSchedule(@RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        String uri = loadBalancer.choose("app-service").getServiceId();
        String url = "http://"+uri.toString() + "/doctor/getschedule";

        ResponseEntity<Object> status = restTemplate.exchange(url,HttpMethod.GET, entity, Object.class);

        return status.getBody();
    }




    // todo
    // update patient profile --D
    // get doctor profile --D
    // Appointment Controllers
        // get available date and time for appointment
        // Look at all the appoiments made by a patient-details --D
        // Doctor can look at his schedule  -- D

    // Forum Controllers

    @PostMapping("/subreddit-create") // C
    @HystrixCommand(fallbackMethod = "createNewThreadFB")
    public Object createNewThread(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(dto, headers);
        String uri = loadBalancer.choose("forum-service").getServiceId();
        String url = "http://"+uri.toString() + "/forum/subreddit/create";

        ResponseEntity<String> result = restTemplate
                .postForEntity(url, entity, String.class);
        return result.getBody();

    }

    @PostMapping("/post-create") // C
    @HystrixCommand(fallbackMethod = "createNewPostUnderThreadFB")
    public Object createNewPostUnderThread(@RequestBody String dto, @RequestHeader(value="Authorization") String token){
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(dto, headers);
        String uri = loadBalancer.choose("forum-service").getServiceId();
        String url = "http://"+uri.toString() + "/forum/posts/create";

        ResponseEntity<String> result = restTemplate
        .postForEntity(url, entity, String.class);
        return result.getBody();



    }

    @GetMapping("/thread-list") // C
    @HystrixCommand(fallbackMethod = "getAllThreadFB")
    @Cacheable(value="threads")
    public Object getAllThread(@RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String uri = loadBalancer.choose("forum-service").getServiceId();
        String url = "http://"+uri.toString() + "/forum/subreddit/getAll";
        
        ResponseEntity<Object> status = restTemplate.exchange(url,HttpMethod.GET, entity, Object.class);
        return status.getBody();
    }
    @GetMapping("/thread/{id}") // C
    @HystrixCommand(fallbackMethod = "getOneThreadFB")
    @Cacheable(value="oneThread")
    public Object getOneThread(@PathVariable Long id, @RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String uri = loadBalancer.choose("forum-service").getServiceId();
        String url = "http://"+uri.toString() + "/forum/subreddit/getOne/"+id;
        
        ResponseEntity<Object> status = restTemplate.exchange(url,HttpMethod.GET, entity, Object.class);
        return status.getBody();
    }

    @GetMapping("/userposts") //C
    @HystrixCommand(fallbackMethod = "getAllUserPostsFB")
    @Cacheable(value="userposts")
    public Object getAllUserPosts(@RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String uri = loadBalancer.choose("forum-service").getServiceId();
        String url = "http://"+uri.toString()+"/forum/posts/getallByUsername";
        
        ResponseEntity<Object> status = restTemplate.exchange(url,HttpMethod.GET, entity, Object.class);
        return status.getBody();
    }

    @GetMapping("/posts-thread/{subredditId}") // C
    @HystrixCommand(fallbackMethod = "getAllPostsUnderThreadFB")
    @Cacheable(value="postUnderThread")
    public Object getAllPostsUnderThread(@PathVariable Long subredditId, @RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String uri = loadBalancer.choose("forum-service").getServiceId();
        String url = "http://"+uri.toString()+"/forum/posts/getallBySId/" + subredditId;
        
        ResponseEntity<Object> status = restTemplate.exchange(url,HttpMethod.GET, entity, Object.class);
        return status.getBody();
    }

    @GetMapping("/posts-comments") //-- C
    @HystrixCommand(fallbackMethod = "getAllCommentsByUserFB")
    @Cacheable(value="commnetsByUser")
    public Object getAllCommentsByUser(@RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String uri = loadBalancer.choose("forum-service").getServiceId();
        String url = "http://"+uri.toString()+"/forum/comment/by-username";
        
        ResponseEntity<Object> status = restTemplate.exchange(url,HttpMethod.GET, entity, Object.class);
        return status.getBody();
    }

    @GetMapping("/post-comment/{postId}") // --C
    @HystrixCommand(fallbackMethod = "getCommentByPostIdFB")
    @Cacheable(value="commentById")
    public Object getCommentByPostId(@PathVariable Long postId, @RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String uri = loadBalancer.choose("forum-service").getServiceId();
        String url = "http://"+uri.toString() + "/forum/comment/by-post/"+postId;
        
        ResponseEntity<Object> status = restTemplate.exchange(url,HttpMethod.GET, entity, Object.class);
        return status.getBody();
    }

    @PostMapping("/vote") // -- C
    @HystrixCommand(fallbackMethod = "votePostFB")
    public Object votePost(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(dto, headers);
        String uri = loadBalancer.choose("forum-service").getServiceId();
        String url = "http://"+uri.toString() + "/forum/vote";
        ResponseEntity<String> result = restTemplate
        .postForEntity(url, entity, String.class);        
        return result.getBody();
    }
    @PostMapping("/create-comment") // C
    @HystrixCommand(fallbackMethod = "createCommentFB")
    public String createComment(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(dto, headers);

        String uri = loadBalancer.choose("forum-service").getServiceId();
        String url = "http://"+uri.toString() + "/forum/comment/create";

        ResponseEntity<String> result = restTemplate
                .postForEntity(url, entity, String.class);

        return result.getBody();
    }

    @DeleteMapping("/comment-delete/{id}")
    @HystrixCommand(fallbackMethod = "deleteCommentFB")
    @Caching(evict = {
        @CacheEvict("commentByUser"),
        @CacheEvict(value = "CommentById")
    })
    public String deleteComment(@PathVariable Long id, @RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        String uri = loadBalancer.choose("forum-service").getServiceId();
        String url = "http://"+uri.toString() + "/forum/comment/delete/"+ id;

        ResponseEntity<String> status = restTemplate.exchange(url,HttpMethod.DELETE, entity, String.class);

        return status.getBody();
    }

    // delete subreddit // chaining 1. vote 2. comment 3. post 4. thread
    // delete posts // 1. vote 2. comment 3. post
    // delete votes 

    // Medication Controllers
    // create-medication -post
    @PostMapping("/create-medication")
    public String createMedic(@RequestBody AssignMedication dto, @RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<AssignMedication> entity = new HttpEntity<AssignMedication>(dto, headers);
        String uri = loadBalancer.choose("medication-service").getServiceId();
        String url = "http://"+uri.toString() + "/medication/create";

        ResponseEntity<String> result = restTemplate
                .postForEntity(url, entity, String.class);
        return result.getBody();
    }
    // get medication - get by patient id .
    @GetMapping("/get-medication")
    public AssignMedication[] getMedic(@RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String uri = loadBalancer.choose("medication-service").getServiceId();
        String url = "http://"+uri.toString() + "/medication/get";

        ResponseEntity<AssignMedication[]> responseEntity = restTemplate.exchange(url,HttpMethod.GET,entity, AssignMedication[].class);
        AssignMedication[] res  = responseEntity.getBody();
        return res;
    }
    // get-nearby stores -get
    @GetMapping("/get-stores")
    public Object getNearBy(@RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String uri = loadBalancer.choose("medication-service").getServiceId();
        String url = "http://"+uri.toString() + "/medication/nearByStore";

        ResponseEntity<Object> responseEntity = restTemplate.exchange(url,HttpMethod.GET,entity, Object.class);
        Object res  = responseEntity.getBody();
        return res;
    }

    @DeleteMapping("/handover-medicine/{patId}")
    public Object handOverMedicine(@PathVariable String patId, @RequestHeader(value = "Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String uri = loadBalancer.choose("medication-service").getServiceId();
        String url = "http://"+uri.toString() + "/medication/handover-medicine/"+patId;

        ResponseEntity<String> responseEntity = restTemplate.exchange(url,HttpMethod.DELETE,entity, String.class);
        Object res  = responseEntity.getBody();
        return res;
    }

   

    





    // Fallback commands

    public Object showPPFallback(@RequestHeader(value = "Authorization") String token){
        
        return "Sorry Something is wrong, cant get your info!";
    }

    public String registerPatientFB(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){
        return "Cannot Register you right now! Try again some time";
    }

    public String patientAppointmentBookingFB(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){
        return "Oops Cannot book appointment right now Please try again";
    }
    public String registerDoctorFB(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){
        return "Cannot Register you , try again later";
    }

    public String updateDoctorFB(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){
        return "oops cannot update you yet, try again later";
    }
    public String deleteDoctorFB(@RequestHeader(value = "Authorization") String token){
        return "Cannot complete operation, try again!";
    }
    public Object patientProfileUpdateFB(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){
        return "Something Horribly went wrong";
    }
    public Object getPatientAppointmentsFB(@RequestHeader(value = "Authorization") String token){
        return "Oops Something went Terribly wrong";
    }
    public Object doctorScheduleFB(@RequestHeader(value = "Authorization") String token){
        return "Oops Something went wrong";
    }
    public Object createNewThreadFB(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){
        return "Oops Something went wrong";
    }
    // one Redis connetion here for each service application
    public Object createNewPostUnderThreadFB(@RequestBody String dto, @RequestHeader(value="Authorization") String token){
        return "Oops Something went wrong";
    }

    public Object getAllThreadFB(@RequestHeader(value = "Authorization") String token){
        return "Oops Something went wrong";
    }

    public Object getOneThreadFB(@PathVariable Long id, @RequestHeader(value = "Authorization") String token){
        return "Oops Something went wrong";

    }

    public Object getAllUserPostsFB(@RequestHeader(value = "Authorization") String token){
        return "oops Something went wrong";
    }
    public Object getAllPostsUnderThreadFB(@PathVariable Long subredditId, @RequestHeader(value = "Authorization") String token){
        return "oops Something went wrong";

    }
    public Object getAllCommentsByUserFB(@RequestHeader(value = "Authorization") String token){
        return "oops Something went wrong";

    }
    public Object getCommentByPostIdFB(@PathVariable Long postId, @RequestHeader(value = "Authorization") String token){
        return "oops Something went wrong";

    }
    public Object votePostFB(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){
        return "oops Something went wrong";

    }

    public String createCommentFB(@RequestBody String dto, @RequestHeader(value = "Authorization") String token){
        return "oops Something went wrong";
    }
    public String deleteCommentFB(@PathVariable Long id, @RequestHeader(value = "Authorization") String token){
        return "oops Something went wrong";

    }

    
}
