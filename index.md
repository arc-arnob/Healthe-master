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
