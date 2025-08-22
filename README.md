# Event Booking in Spring Boot, Postgresql, Docker, Resend

## Postman Integration tests collection 
[here]()

# Prerequisites
- Java JDK 24 installed
- Docker installed

# Installation 
1. Clone the repository
2. Get a Resend api key and configure it in `application.properties`
``` 
    # application.properties
    resend.api.key=YOUR_RESEND_API_KEY
```
# Usage
To run the application you can either run it using your favorite IDE like IntelliJ or use the terminal

```bash
    ./mvnw spring-boot:run 
    # Since the app has dependency <artifactId>spring-boot-docker-compose</artifactId>
    # , docker services (defined in compose.yml) are ran by springboot automatically.
```
# System Design

## System Description
The system is an event booking platform that enables users to register, authenticate, and manage their accounts. Users can create, view, update, and delete events, as well as manage their own bookings. A cart feature allows users to add or remove events before checkout. Upon successful checkout, the system automatically generates orders and bookings, and sends notifications within one minute to inform users about their booking status. Notifications are also sent whenever booking statuses change, ensuring users are kept informed in real-time.
## Functional Requirements

### User Management
1. A user shall be able to register for an account, then be authenticated
2. A user shall be able to delete their account
### Event & Booking Management 
1. A user shall be able to create, read, update and delete events
2. A user shall be able to read and update their bookings
### Cart Management
1. A user shall be able to add or remove events to their cart
2. The system shall generate an order, bookings and notify users about their bookings within 1 minute after a successful checkout
### Notification
1. A user shall receive a notification about the status of their bookings

## Tech Stack 
1. Spring Framework (SpringBoot, Spring Security)
2. Postgresql: database
3. Resend: for sending notification email
4. Docker: for development


