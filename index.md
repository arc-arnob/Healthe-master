# DESIGNING A ROBUST AND SCALABLE HEALTH ANALYSIS/MANAGEMENT API WITH MICRO-SERVICE ARCHITECTURE
Being a web application, it is highly dynamic and it needs to be scalable with minimum efforts put to integrate it in the existing environment. Hence in this scenario building a large scale web based distributed system, micro-services architecture emerged in recent years. Through this project I wish share my early experience with micro-service architecture to design a scalable Health Analysis/Management platform with recent industry recently developed and most used services for load balancing, monitoring, API proxy,security, etc. and compare the benefits and challenges of distributed systems with traditional monolith architectures.

## Layered Architecture
<div>
    <img src="https://raw.githubusercontent.com/arc-arnob/Healthe-master/main/images/architecturediav3.png" class="img-responsive" alt="" height = 600px width = 700px> 
</div>

1. As depicted in above figure the architecture is divided into 3 sections each having its own set of responsibilities :

    - `API Service layer` : responsible for performing authorization and load balancing for next level of processes.
    - `Composite Layer` : responsible for performing abstraction and acts as a single point of contact for the external requests. It also manages circuit breaking process to     increase fault tolerance
    - `Core Layer`: consists of core services supported by the system which includes managing cache, database and business logic.

## Infrasturture
<div>
    <img src="https://raw.githubusercontent.com/arc-arnob/Healthe-master/main/images/tools.png" class="img-responsive" alt="" height = 600px width = 700px> 
</div>

## Database Configuration
The Schema configuration is simulated as per the following diagram below :

<div>
    <img href="https://raw.githubusercontent.com/arc-arnob/Healthe-master/main/images/ER%20Diagram%20(1).png" src="https://raw.githubusercontent.com/arc-arnob/Healthe-master/main/images/ER%20Diagram%20(1).png" class="img-responsive" alt="" > 
</div>
`Click here to enlarge :` [ER Diagram](https://raw.githubusercontent.com/arc-arnob/Healthe-master/main/images/ER%20Diagram%20(1).png)

Note : Word `Simulated` is used since basic SQL constraints are not possible on database per service architecture.

## Context Diagram 

Below is Context Diagram for better understanding of the overall working of the system,

<div>
    <img src="https://raw.githubusercontent.com/arc-arnob/Healthe-master/main/images/dfd_2.png" class="img-responsive" alt=""> 
</div>

## Brief about actual database configuration

 * Reasons why traditional database cannot be used in Microservice architecture:

    - Services must be loosely coupled so that they can be developed, deployed and scaled independently
    - Some business transactions spans multiple services. For example, every service uses User Service for security purpose
    - Some business transactions need to query data that is owned by multiple services.
    - Databases must sometimes be replicated in order to scale.
    - Different services have different data storage requirements. For some services, a relational database is the best choice and for some No Sql.

* Solution to above problems

    - Keep each microservice’s persistent data private to that service and accessible only via its API.
    - The service’s database is effectively part of the implementation of that service. It cannot be accessed directly by other services.
    - Database-server-per-service – each service has it’s own database server.
    - Helps ensure that the services are loosely coupled. Changes to one service’s database does not impact any other services.

## Deliverables
Below listed are all the deliverabeles of the project:

1. **Core Services**
    - **Forum API** : This service is reponsible for managing community discussion forum
    - **Appointment Booking API** : This service is reponsible for managing appointment management with patient and doctor as end users.
    - **Store Locator API** : This service is reponsible locating nearby pharma stores.
    - **Diagnosis API** : This service makes call to django servers which consists of below services:
        1. Heart Attack Predictor API : This service is reponsible for taking in readings as input and returning diagnosis with probability.
        2. Stroke Risk Analysos API : This service is reponsible for taking in readings as input and returning diagnosis with probability.
        3. Diabetes Pridictor API : This service is reponsible for taking in readings as input and returning diagnosis with probability.
    - **Medication Management API** : This service is responsible for managing prescription of patient.  

2. **Support Services**
    - **Authorization and Authentication API** : This API is responsible for performing Token based SSO authentication and authorization.
    - **Mailing API** : This API is responsible for dispastching mails to user whenever reuqired.
    - **Proxy Server** : This server is reponsible for proxying requests to application.
    - **Service Discovery Server** : This servers acts as Service Registry and helps in the process of service discovery.

3. **Gateway Services**
    - **Catalog Service** : This API acts as a gateway to the entire system and only authenticated requests can pass through this gateway.

# Forum API 

## Purpose
* This API will enable user to implement forum service in any project. It is stand-alone service with independent endpoints.
* In order to make it work you need implement `Authorization and Authentication API`.
## Endpoints

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/subreddit-create |	POST	| Creates a new thread

<br>

**Json Format Example**
```javascript
{
    "name":"Thread Name",
    "description":"This is First Thread",
    "numberOfPosts":0
}
```
<br>

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/post-create |	POST	| Creates a post under a thread

<br>

**Json Format Example**
```javascript
{
    "subredditName":"Thread Name",
    "postName":"This is under First Subreddit",
    "url":"www.firstSubreddit.com",
    "description":"This is description post first seventh Subreddit"
}
```
<br>

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/create-comment |	POST	| Creates a comment under a post

<br>

**Json Format Example**
```javascript
{
    "postId":1,
    "text":"This is a third comment for postId 1 under 1 subreddit"
}
```
<br>

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/userposts | GET    | Returns all the posts created by a user
/post-thread/{thread-id} |	GET	| Gets all posts under the thread with given id 
/thread-list |	GET	| Gets all threads 
/thread/{thread-id} |	GET	| Gets threads information with given id 

<br>

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/vote |	POST	| Creates a UPVOTE or DOWNVOTE for a post

<br>

**Json Format Example**
```javascript
{
    "voteType":"UPVOTE",
    "postId":"1"
}
```
<br>

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/post-comment/{post-id} |	GET	| Gets all comments for a post with a given id
/post-comments | GET	| Gets all comments for a post
/comment-delete/{commnet-id} | DELETE	| Deletes comment with given id
/post-delete/{commnet-id} | DELETE	| Deletes post with given id

<br>

# Appointment Management API

## Purpose
* This API will enable user to implement appointment booking, schdule checks and management. It is stand-alone service with independent endpoints.
* In order to make it work you need implement `Authorization and Authentication API`.

## Endpoints

<br>

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/patient-register |	POST	| Creates new patient Profile

<br>

**Json Format Example**
```javascript
{
    "pat_name":"Arnob_123",
    "pat_phone":"87171691191",
    "pat_dob":"03-09-1999",
    "pat_gender":"M",
    "enrollDate":"09-09-2020",
    "insured":"Y"
}
```
<br>

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/patient-update |	PUT	| Updates patient Profile

<br>

**Json Format Example**
```javascript
{
    "pat_name":"Arnob_123",
    "pat_phone":"87171691191",
    "pat_dob":"03-09-1999",
    "pat_gender":"M",
    "enrollDate":"09-09-2020",
    "insured":"Y"
}
```
<br>

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/patient-app-booking |	POST	| Updates patient Profile

<br>

**Json Format Example**
```javascript
{
    "docId":"testuser5",
    "appTypeId":1,
    "startDate":"2021-03-13",
    "startTime":"6:30:59",
    "endTime": "7:00:00"
}
```
<br>

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/doctor-register |	POST	| Creates a new Doctor Profile

<br>

**Json Format Example**
```javascript
{
    "doc_name":"Arnob chowdhury",
    "doc_phone":"87171691191",
    "doc_address":"Valentus, Gaur City mall CR",
    "dateOfStarting":"1978-09-09",
    "doc_gender":"M",
    "doc_dob":"1978-09-09",
    "doc_settlePoint":"22.2323,45.4433",
    "doc_description": "Did MBBS and MD from Boston, Heart specialist",
    "docSpecId": 1,
    "clinicId": 1
}
```
<br>

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/doctor-update |	POST	| Updates Doctor Profile

<br>

**Json Format Example**
```javascript
{
    "doc_name":"Ikala Chuhioq",
    "doc_phone":"8717123455",
    "doc_address":"Valentus, Gaur City mall CR",
    "dateOfStarting":"1978-09-09",
    "doc_gender":"M",
    "doc_dob":"1978-09-09",
    "doc_settlePoint":"22.2323,45.4433",
    "doc_description": "Did MBBS and MD from Boston, Heart specialist",
    "docSpecId": 1,
    "clinicId": 1
}
```
<br>

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/doctor-update |	POST	| Updates Doctor Profile

<br>

**Json Format Example**
```javascript
{
    "doc_name":"Ikala Chuhioq",
    "doc_phone":"12345667890",
    "doc_address":"Valentus, Gaur City mall CR",
    "dateOfStarting":"1978-09-09",
    "doc_gender":"M",
    "doc_dob":"1978-09-09",
    "doc_settlePoint":"22.2323,45.4433",
    "doc_description": "Did MBBS and MD from Boston, Heart specialist",
    "docSpecId": 1,
    "clinicId": 1
}
```
<br>

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/patient-profile |	GET	| Returns Patient Profile
/patient-get-booking |	GET	| Returns Patient Booking information
/doctor-delete |	DELETE	| Deletes doctor's profile
/doctor-schedule |	GET	| Returns Doctor schedule to avoid overlapping 

<br>

# Store Locator API

## Purpose
* This API will enable user to implement appointment near geo search. It is stand-alone service with independent endpoints.
* It does not require `Authorization and Authentication API` and is accesscible to anyone.

## Endpoints

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/booking/allmarkers |	GET	| Returns coordinates of all the stores.
/near/{latitude:.+},{longitude:.+} |	GET	| Returns coordinates of all the stores near given latitude and longitude

<br>

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/create | POST	| Registers a pharamacy stores with given coordinates coordinates.

<br>

**Json Format Example**
```javascript
{
    "storeName":"books testing",
    "streetName":"NEw Street 1 ...",
    "location": {
      "type": "Point",
      "coordinates": [12.1212, -45.2331]
    }
}



```

<br>
# Diagnosis API

## Purpose
* This API will enable user to call Django Server running Machine Learning Models to performs diagnosis on the input reading. It is stand-alone service with independent endpoints.
* It requires `Authorization and Authentication API` to be implemented prior to use.

## Endpoints

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/diabetes-diagnosis | POST	| Sends reading for ML model to process and returns the outcome

<br>

**Json Format Example**
```javascript
{
    "glucose":140.0,
    "insulin":0.0,
    "bmi":23.3,
    "age":50
}
```

<br>

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/stroke-diagnosis | POST	| Sends reading for ML model to process and returns the outcome

<br>

**Json Format Example**
```javascript
{
    "gender":1,
    "ever_married":1,
    "work_type":2,
    "residence_type":1,
    "smoking_status":1,
    "age":67, 
    "hypertension":0,
    "heart_disease":1, 
    "avg_glucose_level":229, 
    "bmi":37
}
```

<br>

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/heart-diagnosis | POST	| Sends reading for ML model to process and returns the outcome

<br>

**Json Format Example**
```javascript
{
    "age":63,
    "sex":1,
    "cp":3,  
    "trestbps":145,  
    "chol":233, 
    "fbs":1,  
    "restecg":0,  
    "thalach":150,  
    "exang": 0, 
    "oldpeak":2, 
    "slope": 0,
    "ca":0,
    "thal":1
}
```

<br>

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/get-heart-record | GET  | Returns stored Diagonis Report
/get-diabetes-record | GET  | Returns stored Diagonis Report
/get-stroke-record | GET  | Returns stored Diagonis Report

<br>

# Medication API

## Purpose
* This API will enable user to implement medication management for better pateint to store experience.

* It requires `Authorization and Authentication API` to be implemented prior to use.

## Endpoints

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/create-medication | POST  | Creates Precription and stores with User Location

<br>

**Json Format Example**
```javascript
{
    "patId":"testuser4",
    "coordinates":[12.1212,-45.2321],
    "medication":[{
            "medName":"paracitalom",
            "time":"morning"
        },
        {
            "medName":"cyrus",
            "time":"night" 
        },
        {
            "medName":"cyrus_19191",
            "time":"night" 
        }
    ]
}
```
<br>

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/get-medication | GET  | Returns Medication record of patient
/get-stores| GET  | Returns nearby stores to user location
/handover-medicines/{pat-id} | DELETE  | Deletes patient record from to be picked up medicines.

<br>

# Support APIs
 
# Authentication and Authorization API

## Purpose
* Multiple services put together forms an application each of which are independent of others. Hence securing the system as a whole is not enough and poses a risk of whole system compromise if even just one service is compromised. Hence to address this issue, I implemented JWT based authentication and authorization for each μS that will allow requests after authenticating it from centrally runningauthentication and authorization server

<div>
    <img src="https://raw.githubusercontent.com/arc-arnob/Healthe-master/main/images/security_accesstoken.png" class="img-responsive" alt=""> 
</div>

## Explanation
* When user logs in using credentials, it is encrypted and is always attached with the payload while requests are made. It the responsibility of each service to decrypt the credentials and verify it against the centrally running authorization andauthentication server and allow only valid calls.

## Endpoints

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/api/auth/signup | POST  | Registers a new User and sends email for verification purpose

<br>

**Json Format Example**
```javascript
{
    "username":"testuserd7",
    "email":"testuser7@gmail.com",
    "password":"test789",
    "role":"DOCTOR"
}
```
<br>

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/api/auth/accVerfication/{token} | GET | Activates User account by comparing one time token sent on mail

<br>


*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/api/auth/login | POST  | Returns Encryted Access Token and Expiration.

<br>

**Json Format Example**
```javascript
{
    "username":"testuser4",
    "password":"test456"
}
```
<br>

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/api/auth/logout | POST  | Deletes users security context.

<br>

**Json Format Example**
```javascript
{
    "username":"testuser4",
    "password":"test456"
}
```
<br>

# Mailing API

## Purpose

* This API is responsible for dispastching mails to user in case of account activation, diagnosis outcome.
* It requires `Authorization and Authentication API` to be implemented prior to use.

## Endpoints

*Endpoint* |	*Method* |	*Description*
-------  | ------- | -----------
/sendmail | POST  | Sends mail with required body


# GATEWAYS

*Service* |	*EndPoint*
------- | ------- 
auth-service    | /auth/**
forum-service | /forum/**
app-service | /appointment/**
catalog-service | /catalog/**
medication-service | /medication/**
diagnosis-service | /diagnosis/**

* URI for gateway : http://localhost:8085
* URI for Authorization API : http://localhost:8082
* URI for Mailing API : http://localhost:8089
* URI for Forum-API : http://localhost:8081
* URI for Appointement Management API : http://localhost:8084
* URI for Store Locator API : http://localhost:8086
* URI for Medication API : http://localhost:8083
* URI for Diagnosis API : http://localhost:8088
* URI for TOM TOM (Third party) API : http://localhost:8087

# Microservice Tenents

## Circuit Breaker (Fault Tolerance)

### Purpose
* Services sometimes collaborate when handling requests. When one service synchronously invokes another there is always the possibility that the other service is unavailable or is exhibiting such high latency it is essentially unusable. Precious resources such as threads might be consumed in the caller while waiting for the other service to respond. This might lead to resource exhaustion, which would make the calling service unable to handle other requests. The failure of one service can potentially cascade to other services throughout the application.
* How to prevent a network or service failure from cascading to other services?
<div>
    <img src="https://raw.githubusercontent.com/arc-arnob/Healthe-master/main/images/fault_tol_1.png" class="img-responsive" alt=""> 
</div>
<div>
    <img src="https://raw.githubusercontent.com/arc-arnob/Healthe-master/main/images/fault_tol_2.png" class="img-responsive" alt=""> 
</div>
<div>
    <img src="https://raw.githubusercontent.com/arc-arnob/Healthe-master/main/images/fault_tol_3.png" class="img-responsive" alt=""> 
</div>
<div>
    <img src="https://raw.githubusercontent.com/arc-arnob/Healthe-master/main/images/fault_tol_4.png" class="img-responsive" alt=""> 
</div>

# Explanation
* A service client should invoke a remote service via a proxy that functions in a similar fashion to an electrical circuit breaker. When the number of consecutive failures crosses a threshold, the circuit breaker trips, and for the duration of a timeout period all attempts to invoke the remote service will fail immediately. After the timeout expires the circuit breaker allows a limited number of test requests to pass through. If those requests succeed the circuit breaker resumes normal operation. Otherwise, if there is a failure the timeout period begins again.

# Implementation

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;


@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
public class CatalogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogServiceApplication.class, args);
	}

}

```
<br>

application.porperties Configuration :

```
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=2000
management.endpoints.web.exposure.include=hystrix.stream
hystrix.dashboard.proxyStreamAllowList=*
```

## Service Registry and Discovery

### Purpose

* Services typically need to call one another. In a monolithic application, services invoke one another through language-level method or procedure calls. In a traditional distributed system deployment, services run at fixed, well known locations (hosts and ports) and so can easily call one another using HTTP/REST or some RPC mechanism. However, a modern microservice-based application typically runs in a virtualized or containerized environments where the number of instances of a service and their locations changes dynamically.
* How does the client of a service - the API gateway or another service - discover the location of a service instance?

### Problems
* Each instance of a service exposes a remote API such as HTTP/REST, etc. at a particular location (host and port)
* The number of services instances and their locations changes dynamically.
* Virtual machines and containers are usually assigned dynamic IP addresses.
* The number of services instances might vary dynamically.

<div>
    <img src="https://raw.githubusercontent.com/arc-arnob/Healthe-master/main/images/sd3.png" class="img-responsive" alt=""> 
</div>

### Implementation Example
* **Service Discovery Registry**

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServicedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicedisApplication.class, args);
	}

}

```
* application.properties Configuration
```
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
eureka.client.service-url.default-zone=http://localhost:8761/eureka/
eureka.instance.hostname=localhost
spring.cloud.loadbalancer.ribbon.enabled = false
```


* **Service Discovery Client**

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
@EnableEurekaClient
public class RedditApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditApplication.class, args);
	}

}

```

* application.properties Configuration
```
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
eureka.client.service-url.default-zone=http://localhost:8761/eureka/
eureka.instance.hostname=localhost
```

## Load Balancing
* In simple terms Load Balancer distributes the incoming traffic to multiple instancesof the service and works in conjunction to scaling process.
* Algorithm for load balancing: Round Robin
* Type of Load balancing: Hybrid Load Balancing
* In hybrid load balancing technique used in this project all the consumers and prosumers (one that produces result as well as requires input ) are implemented with theirown load balancer 


<div>
    <img src="https://raw.githubusercontent.com/arc-arnob/Healthe-master/main/images/hybrid_lb_1.png" class="img-responsive" alt=""> 
</div>

### Implementation Example

```java

@GetMapping("/get-stores") //hystrix
@HystrixCommand(fallbackMethod = "getNearByFB")
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

```

# Run on your PC without Docker

## What Will You Build
You will build a web application that is Oauth2 enabled.

## What you will need

* A favorite text editor or IDE
* JDK 1.8 or later
* Gradle 4+ or Maven 3.2+

## To clone and the project do the following
* Download and unzip the source repository for this guide, or clone it using Git: `git clone https://github.com/arc-arnob/Healthe-master.git`
* cd to /target folders of all the spring boot applications and run: `mvnw spring-boot:run`


