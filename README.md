# EasyTrip-APP

EasyTrip is a secure, role-based RESTful backend application built using Spring Boot for a cab booking system.
It provides APIs to manage authentication, customers, drivers, cabs, and bookings with support for secure login, ownership-based access, and admin controls.

## 🔍 About Project

EasyTrip is a secure and scalable backend RESTful web application developed using Spring Boot for managing a complete cab booking system. The application is designed around dedicated dashboards for Customer, Driver, and Admin, with strict authentication and role-based authorization applied across all APIs. Customers can manage their profiles and book cabs, drivers can handle trip execution and booking status updates, and administrators can monitor and control customers, drivers, cabs, and bookings efficiently. All user operations are protected through ownership-based access control, ensuring that each user can access only their own data and related bookings.

The project follows a clean layered architecture using Controller, Service, Repository, DTO, and Transformer layers to ensure maintainability, scalability, and readability. Centralized exception handling provides consistent and structured error responses. Swagger UI is integrated for interactive API documentation and testing. The system also implements essential security features such as password encryption (BCrypt), login/logout mechanisms, change password and forgot password flows, along with status-based entity management (ACTIVE, INACTIVE, CONFIRMATION, COMPLETED, CANCELLED, etc.), reflecting real-world cab booking workflows.

**This project is suitable for demonstrating real-world backend development concepts such as:**

### ✔ ⚙️ Technical Features

- RESTful API design
- DTO based architecture
- Entity to DTO transformation
- Spring Security integration
- Role based authorization (Admin, Driver, Customer)  
- Ownership security (user can access only his own data)
- Validation annotations
- Service layer separation
- Interface-based services  
- Global exception handling
- Status-based filtering using Enums
- Clean layered architecture (Controller, Service, Repository, Model)
- Swagger UI for API documentation

### 📧 Email Notification System

The application sends email notifications to customers for:

- Booking Confirmation

- Booking Completion

- Booking Cancellation

Email templates are generated dynamically based on booking status.   


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

## 📂 Project Structure

```
easetrip
│
├── .idea
├── .mvn
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
│   │   │
│   │   └── resources
│   │       ├── static
│   │       ├── templates
│   │       └── application.properties
│   │
│   └── test
│
├── target
│
├── .gitattributes
├── .gitignore
├── HELP.md
├── mvnw
├── mvnw.cmd
└── pom.xml
```````

## 🗄 Database Configuration

Configure database in  ```application.properties```:
```
spring.datasource.url=jdbc:mysql://localhost:3306/easytrip_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

```

## ⚙ How to Run the Project

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

**✔ JWT Authentication**

**✔ Swagger Documentation**  

**✔ Ride Tracking**

**✔ Rating System**  

**✔ Payment Gateway Integration**

**✔ Frontend (React)**

## 👨‍💻 Developer

**Er. Mohd Sajid**

**Java Backend Developer**

## 📄 License

This project is developed for learning and practice purposes.


