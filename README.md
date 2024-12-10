Ecommerce project 

This project involves backend ecommerce application with password encryption and authentication of users 

Features:

Secure User Login and Registration (JWT Authentication):
Implemented JWT (JSON Web Token) for secure, stateless user authentication. Upon successful login or registration, users receive a token, ensuring seamless and secure API access.

Password Encryption (BCrypt):
User passwords are securely hashed using BCrypt, ensuring sensitive data is protected. The application never stores plain-text passwords, enhancing security.

Email Verification:
Users must verify their email upon registration. A verification email with a unique token is sent, ensuring that only valid email addresses can be used for account activation. Tested using SMTP4dev for reliable email functionality during development.

Entity Validation:
Input data is validated at the entity level using Java's Bean Validation annotations to ensure accurate and secure data collection. This prevents invalid or incomplete data from being processed or stored.



Technologies Used
Backend:
Spring Boot: For building RESTful APIs and application management.
Spring Security: For securing the application, handling authentication, and managing roles.
JWT: For stateless authentication and securing APIs.
Spring Data JPA: For database interaction and ORM (Object Relational Mapping).

Database:

MySQL: For persistent storage of user data, orders, and product catalog.

Tools & Libraries:

Postman: For testing and documenting APIs.
Lombok: For reducing boilerplate code (e.g., getters/setters).
BCrypt: For password hashing and encryption.
