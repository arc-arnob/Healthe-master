# DESIGNING A ROBUST AND SCALABLE HEALTH ANALYSIS/MANAGEMENT API WITH MICRO-SERVICE ARCHITECTURE
Being a web application, it is highly dynamic and it needs to be scalable with minimum efforts put to integrate it in the existing environment. Hence in this scenario building a large scale web based distributed system, micro-services architecture emerged in recent years. Through this project I wish share my early experience with micro-service architecture to design a scalable Health Analysis/Management platform with recent industry recently developed and most used services for load balancing, monitoring, API proxy,security, etc. and compare the benefits and challenges of distributed systems with traditional monolith architectures.

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

## Layered Architecture
<div>
    <img src="https://raw.githubusercontent.com/arc-arnob/Healthe-master/main/images/architecturediav3.png" class="img-responsive" alt="" height = 600px width = 650px> 
</div>

## Infrasturture
<div>
    <img src="https://raw.githubusercontent.com/arc-arnob/Healthe-master/main/images/tools.png" class="img-responsive" alt="" height = 600px width = 650px> 
</div>

