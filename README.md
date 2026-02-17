# EasyTrip-APP

EasyTrip is a secure, role-based RESTful backend application built using Spring Boot for a cab booking system.
It provides APIs to manage authentication, customers, drivers, cabs, and bookings with support for secure login, ownership-based access, and admin controls.

## 🔍 About Project

EasyTrip is a secure, **role-based cab booking backend application** developed using **Spring Boot** to manage customers, drivers, cabs, and bookings through RESTful APIs. The system supports the **complete booking lifecycle**, including cab booking, cancellation, trip completion, and automated **email notifications** based on booking status.

The application implements **authentication and role-based authorization (ADMIN, DRIVER, CUSTOMER)** using **Spring Security,** along with **ownership-based access control** to ensure users can access only their own profiles and bookings through secure /me APIs. An **Admin module** is designed to manage customers, drivers, cabs, and bookings with **status-based filtering and search functionality.**

The project follows a **clean layered architecture (Controller–Service–Repository–DTO)** with **validation, global exception handling, pagination, and filtering APIs** for scalable backend design. APIs are documented and tested using **Swagger UI**.

The backend is deployed on **Render** and integrated with a frontend application generated using **Antigravity AI**, which is deployed on **Vercel**.

**Backend Live URL:**
https://easytrip-api-kaeq.onrender.com

**Frontend Live URL:**
https://easytrip-app.vercel.app

**This project is suitable for demonstrating real-world backend development concepts such as:**

### ✔ Production-Level Enhancements

The backend has been enhanced with several industry-level practices:

- Deployment on Render cloud platform  
- Integration with a live frontend (Vercel)  
- CORS configuration for cross-origin frontend communication  
- PostgreSQL production database support  
- Docker support for deployment  
- Asynchronous email sending  
- Environment-based configuration  
- Clean service abstraction  
- Structured API responses  
- Enum-based status management  
- Principal-based ownership security  
- Centralized validation and exception handling  

### ✔ Backend Architecture

The project follows a clean layered architecture:

Controller → Service → Repository → Database

**Additional layers:**

- DTO layer  
- Transformer layer  
- Security layer  
- Email module  
- Exception module  
- Configuration module
  
This ensures maintainability, scalability, and separation of concerns.

### ✔ Security Model

The application uses Spring Security with session-based authentication.

**Security features include:**

- Role-based authorization (ADMIN, DRIVER, CUSTOMER)  
- Ownership-based access control  
- BCrypt password encryption  
- Login / Logout  
- Change password flow  
- Profile status validation  
- Principal-based data access  
- Protected REST endpoints  

Ownership security ensures users can access only their own data using /me APIs.

### ✔ 📧 Email Notification System

The system sends automated emails to customers for:

- Booking Confirmation  
- Booking Completion  
- Booking Cancellation  

Emails are generated dynamically using booking data.

Email sending is implemented using JavaMailSender and asynchronous execution.

### ✔ ⚙️ Technical Features

- RESTful API design
- DTO based architecture
- Entity to DTO transformation
- Spring Security integration
- Role based authorization (Admin, Driver, Customer)  
- Ownership security (user can access only his own data)
- Validation annotations
- Pagination & Sorting
- Service layer separation
- Interface-based services  
- Global exception handling
- Status-based filtering using Enums
- Clean layered architecture (Controller, Service, Repository, Model)
- Swagger UI for API documentation  


## 🛡️ Security Model (Role Security Rules)

| Role     | Access                                |
| -------- | ------------------------------------- |
| CUSTOMER | Own profile, own bookings, cab search |
| DRIVER   | Own profile, assigned bookings, cab qureries    |
| ADMIN    | Full system access                    |

✔ Ownership security is applied using logged-in user identity  
✔ No user can access another user's data  
✔ /me APIs are used instead of {id}  

## 🔒 Ownership Security

Every customer or driver API uses the logged-in user’s Principal email to fetch only that user’s own data.  

This ensures:  
✔ Prevents ID tampering  
✔ Only owners see their own bookings  
✔ Admin has privileged access  

- All protected APIs require session authentication.  

## 🚀 Features of EasyTrip-Backend Application

### 🔐 App Users (Authentication & Security)

✔ Signup with Role (Customer / Driver only)   
✔ Login User  
✔ Logout User  
✔ Change Password  
✔ BCrypt password encryption  
✔ Role based access (ADMIN / DRIVER / CUSTOMER)  
✔ Profile status check (ACTIVE / INACTIVE)  
✔ Ownership based security (user can access only own data)  

### 👤 Customer Dashboard

**🧾 Customer Profile**

✔ Create customer profile  
✔ View own profile  
✔ Update profile  
✔ Deactivate (inactive) profile  

**📖 Customer Booking Window**

✔ View all bookings  
✔ View active booking  
✔ View completed bookings  
✔ View cancelled bookings  
✔ Book cab  
✔ Update booking  
✔ Cancel booking  

**🚕 Cab Availability**

✔ Check available cabs  

### 🚗 Driver Dashboard

**👤 Driver Profile**

✔ Create driver profile  
✔ View own profile  
✔ Update profile  
✔ Deactivate (inactive) profile  

**📖 Driver Booking Window**

✔ View all assigned bookings  
✔ View active booking  
✔ View completed bookings  
✔ View cancelled bookings  
✔ Complete booking (trip end)  

**🚕 Driver Cab Queries**

✔ Register cab  
✔ Update cab details  
✔ Get own cab details  

### 🛡️ Admin Dashboard

**👥 Customer Management**

✔ View all customers  
✔ View active customers  
✔ View inactive customers  
✔ Find customer by ID  
✔ Search customers by gender & age  
✔ Search customers by age greater than  
✔ Activate customer profile  
✔ Inactivate customer profile  

**🚗 Driver Management**

✔ View all drivers  
✔ View active drivers  
✔ View inactive drivers  
✔ Find driver by ID  
✔ Activate driver profile  
✔ Inactivate driver profile  

**🚕 Cab Management**

✔ View all listed cabs  
✔ View active cabs  
✔ View inactive cabs  
✔ View available cabs  
✔ View unavailable cabs  
✔ Find cab by ID  

**📖 Booking Management**

✔ View all bookings  
✔ Find booking by ID  
✔ Get bookings by customer  
✔ Get bookings by driver  
✔ View active bookings  
✔ View completed bookings  
✔ View cancelled bookings  

## 🛠 Tech Stack

- **Java**  
- **Spring Boot**
- **Spring Security**  
- **Spring MVC**
- **Spring Data JPA**  
- **Hibernate ORM**  
- **MySQL Database / H2 (optional)**  
- **JavaMailSender (email)**
- **RESTful APIs**
- **Maven**
- **Lombok**
- **Postman / Swagger (for testing)**

## Deployment:

- Render (Backend hosting)  
- Docker  

## Frontend Integration:

- Antigravity AI generated frontend  
- Vercel deployment  
- CORS enabled backend APIs  

## Testing & Documentation:

- Postman  
- Swagger UI  

## 📂 Project Structure

```
easetrip
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.sajidtech.easytrip
│   │   │       ├── config
│   │   │       ├── controller
│   │   │       ├── dto
│   │   │       ├── emails
│   │   │       ├── enums
│   │   │       ├── exception
│   │   │       ├── model
│   │   │       ├── repository
│   │   │       ├── security
│   │   │       ├── service
│   │   │       ├── transformer
│   │   │       └── EasytripApplication.java
│   │   └── resources
│   │       ├── static
│   │       ├── templates
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       └── application-prod.properties
│   └── test
├── target
├── .gitattributes
├── .gitignore
├── Dockerfile
├── HELP.md
├── mvnw
├── mvnw.cmd
└── pom.xml
```````
# Application Configuration (application.properties)

The project supports multiple environment configurations for local development and production deployment.

You can configure them inside `application.properties` or using environment variables.

---

## 1. Local Development Configuration (MySQL)

```
spring.datasource.url=jdbc:mysql://localhost:3306/easytrip_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

Steps to run locally:

1. Install MySQL
2. Create database:

```
create database easytrip_db;
```

3. Update username and password
4. Run Spring Boot application

---

## 2. Production Database Configuration (PostgreSQL — Render)

Render provides PostgreSQL database credentials via environment variables.

Example configuration:

```
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

Render Environment Variables:

```
DB_URL=jdbc:postgresql://host:5432/db_name
DB_USERNAME=your_username
DB_PASSWORD=your_password
```

Steps on Render:

1. Create PostgreSQL database
2. Copy database credentials
3. Add Environment Variables in Render service
4. Redeploy backend service

---

## 3. Email Configuration (SMTP)

Email notifications are sent using Gmail SMTP.

```
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

Steps:

1. Enable 2-Step Verification in Gmail
2. Generate App Password
3. Use App Password in configuration
4. Restart application

---

## Notes

For production deployment, sensitive credentials should always be stored in environment variables instead of hardcoding them in `application.properties`.

This project is already configured to support environment-based configuration when deployed on Render.

## ⚙ How to Run the Project locally

 **1️. Clone repository**
```  git clone https://github.com/mohdsajid9600/easetrip-app.git  ```

**2️. Open project in IntelliJ / Eclipse**

**3. Configure database in application.properties**

**4. Run the application**

**5. Go to project directory**
```  cd easetrip-app  ```

**6. Build project**
```  mvn clean install  ```

**7. Run application**
```  mvn spring-boot:run  ```

## Application will run on:

```  http://localhost:8080  ```

## 🧪 API Testing

Use Postman or Swagger UI to test APIs.

## 🔐 App Users (Auth APIs)

| Method | Endpoint                | Description                                    |
| ------ | ----------------------- | ---------------------------------------------- |
| POST   | `/auth/signup`          | Signup user with role (CUSTOMER / DRIVER only) |
| POST   | `/auth/login`           | Login user                                     |
| POST   | `/auth/logout`          | Logout current user                            |
| PUT   | `/auth/change-password` | Change logged-in user password                 |


## 👤 Customer Dashboard

**🧾 Customer Profile**

| Method | Endpoint                   | Description                    |
| ------ | -------------------------- | ------------------------------ |
| POST   | `/customer/create-profile` | Create customer profile        |
| GET    | `/customer/me`             | Get logged-in customer profile |
| PUT    | `/customer/me/update`      | Update customer profile        |
| DELETE | `/customer/me`             | Deactivate customer profile    |


**📖 Customer Booking Windows**

| Method | Endpoint                      | Description                            |
| ------ | ----------------------------- | -------------------------------------- |
| GET    | `/booking/customer`           | Get all bookings of logged-in customer |
| GET    | `/booking/customer/active`    | Get active booking                     |
| GET    | `/booking/customer/completed` | Get completed bookings                 |
| GET    | `/booking/customer/cancelled` | Get cancelled bookings                 |
| POST   | `/booking/customer/booked`    | Book a cab                             |
| PUT    | `/booking/customer/update`    | Update booking                         |
| PUT    | `/booking/customer/cancel`    | Cancel booking                         |


**🚕 Cabs Availability**

| Method | Endpoint         | Description            |
| ------ | ---------------- | ---------------------- |
| GET    | `/cab/available` | Get all available cabs |


## 🚗 Driver Dashboard

**👤 Driver Profile**

| Method | Endpoint            | Description                  |
| ------ | ------------------- | ---------------------------- |
| POST   | `/driver/register`  | Create driver profile        |
| GET    | `/driver/me`        | Get logged-in driver profile |
| PUT    | `/driver/me/update` | Update driver profile        |
| DELETE | `/driver/me`        | Deactivate driver profile    |


**📖 Driver Booking Windows**

| Method | Endpoint                    | Description                         |
| ------ | --------------------------- | ----------------------------------- |
| GET    | `/booking/driver`           | Get all bookings assigned to driver |
| GET    | `/booking/driver/active`    | Get active booking                  |
| GET    | `/booking/driver/completed` | Get completed bookings              |
| GET    | `/booking/driver/cancelled` | Get cancelled bookings              |
| PUT    | `/booking/driver/complete`  | Complete booking (trip finished)    |


**🚕 Driver Cab Queries**

| Method | Endpoint               | Description         |
| ------ | ---------------------- | ------------------- |
| POST   | `/cab/driver/register` | Register cab        |
| PUT    | `/cab/driver/update`   | Update cab details  |
| GET    | `/cab/driver`       | Get own cab details |


## 🛡️ Admin Dashboard APIs

**🛡️ Admin – Customer Fetch APIs**

| Method | Endpoint                          | Description                          |
| ------ | --------------------------------- | ------------------------------------ |
| GET    | `/admin/customers`                | Get all customers                    |
| GET    | `/admin/customers/active`         | Get active customers                 |
| GET    | `/admin/customers/inactive`       | Get inactive customers               |
| GET    | `/admin/customer/search`          | Search customer by id                |
| GET    | `/admin/customers/search`         | Search customers by gender & age     |
| GET    | `/admin/customers/search/greater` | Search customers by age greater than |
| PUT    | `/admin/customer/{id}/active`     | Activate customer                    |
| PUT    | `/admin/customer/{id}/inactive`   | Inactivate customer                  |


**🛡️ Admin – Driver Fetch APIs**

| Method | Endpoint                      | Description          |
| ------ | ----------------------------- | -------------------- |
| GET    | `/admin/drivers`              | Get all drivers      |
| GET    | `/admin/drivers/active`       | Get active drivers   |
| GET    | `/admin/drivers/inactive`     | Get inactive drivers |
| GET    | `/admin/driver/search`        | Search driver by id  |
| PUT    | `/admin/driver/{id}/active`   | Activate driver      |
| PUT    | `/admin/driver/{id}/inactive` | Inactivate driver    |


**🛡️ Admin – Cab Fetch APIs**

| Method | Endpoint                  | Description          |
| ------ | ------------------------- | -------------------- |
| GET    | `/admin/cabs`             | Get all cabs         |
| GET    | `/admin/cabs/active`      | Get active cabs      |
| GET    | `/admin/cabs/inactive`    | Get inactive cabs    |
| GET    | `/admin/cabs/available`   | Get available cabs   |
| GET    | `/admin/cabs/unavailable` | Get unavailable cabs |
| GET    | `/admin/cab/search`       | Search cab by id     |


**🛡️ Admin – Booking Fetch APIs**

| Method | Endpoint                   | Description              |
| ------ | -------------------------- | ------------------------ |
| GET    | `/admin/bookings`          | Get all bookings         |
| GET    | `/admin/bookings/active`   | Get active bookings      |
| GET    | `/admin/bookings/complete` | Get completed bookings   |
| GET    | `/admin/bookings/cancel`   | Get cancelled bookings   |
| GET    | `/admin/bookings/driver`   | Get bookings by driver   |
| GET    | `/admin/bookings/customer` | Get bookings by customer |
| GET    | `/admin/booking/search`    | Search booking by id     |


## 📈 Future Enhancements

✔ JWT authentication  
✔ Payment gateway integration  
✔ Ride tracking  
✔ Rating system  
✔ API rate limiting  
✔ Logging & monitoring  
✔ CI/CD pipeline  


## 👨‍💻 Developer

**Er. Mohd Sajid**

**Java Backend Developer**

## 📄 License

This project is developed for learning and practice purposes.


