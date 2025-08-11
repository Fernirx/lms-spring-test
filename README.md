# LMS Spring

**LMS Spring** is a robust, modular RESTful API for university academic management, built with Spring Boot. It provides
a solid foundation for managing courses, students, grades, roles, and other operations, ready for integration with UIs
or other services.

---

## Table of Contents

* [Introduction](#introduction)
* [Key Features](#key-features)
* [Technologies](#technologies)
* [Prerequisites](#prerequisites)
* [Installation & Setup](#installation--setup)
* [Project Structure](#project-structure)
* [Configuration](#configuration)
* [API Documentation](#api-documentation)
* [Security & Health](#security--health)

---

## Introduction

**LMS Spring** is a scalable, multi-module REST API designed for comprehensive academic management at universities. It
emphasizes:

* **Modularity**: clear separation of concerns across common, core, auth, service, and API modules
* **Flexibility**: profile-based configuration for development, staging, and production
* **Security**: robust authentication and authorization via Spring Security and JWT
* **Documentation**: integrated OpenAPI/Swagger UI for easy endpoint exploration

Use this project as a foundation to rapidly build and customize academic management workflows.

---

## Key Features

* **Multi‑module architecture**: separate modules for common utilities, core configs, auth, user, academic, finance,
  reporting, gateway, admin, etc.
* **Profile‑based configuration**: support for `application.yaml` + `application-{profile}.yaml` patterns
* **Spring Security & JWT**: fine‑grained RBAC and token‑based authentication
* **Bean Validation**: ensure data integrity at the API layer
* **OpenAPI/Swagger**: auto‑generated API docs and interactive Swagger UI
* **Actuator & health checks**: built‑in monitoring and metrics endpoints
* **Lombok & MapStruct**: reduce boilerplate and simplify DTO mappings

---

## Technologies

* **Spring Boot** 3.5.4
* **Java** 21
* **Maven** 3.8+
* **MySQL** 8.x (via Spring Data JPA)
* **Spring Security** + **JWT**
* **SpringDoc OpenAPI / Swagger UI**
* **Lombok**, **MapStruct**
* **Spring Boot Actuator**

---

## Prerequisites

* **Java 21+**
* **Maven 3.8+**
* **Git**
* **MySQL 8+** (or any JDBC‑compatible database)
* **IDE**: IntelliJ IDEA, VS Code, or Eclipse (optional)
* **API Client**: Postman, Insomnia, or similar (optional)

---

## Installation & Setup

1. **Clone repository**

   ```bash
   git clone https://github.com/Fernirx/lms-spring.git
   cd lms-spring
   ```
2. **Create profile config**

   ```bash
   cd application/src/main/resources
   cp application-local.example.yaml application-local.yaml
   ```

    * Edit `application-local.yaml` for your local config.
3. **Activate profile**

    * **Option A**: add in `application.yaml`

      ```yaml
      spring:
        profiles:
          active: local
      ```
    * **Option B**: JVM argument

      ```bash
      ./mvnw spring-boot:run -pl application \
        -Dspring-boot.run.arguments="--spring.profiles.active=local"
      ```
4. **Build & run**

   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run -pl application
   ```

---

## Project Structure

```plaintext
lms-system
├── lms-common          # Shared utilities & DTOs
├── lms-core            # Configurations & infrastructure
├── lms-auth            # Authentication & authorization
├── lms-user            # User management
├── lms-academic        # Academic structure management
├── lms-personnel       # Personnel management
├── lms-course          # Course management
├── lms-scheduling      # Scheduling management
├── lms-registration    # Registration management
├── lms-finance         # Finance management
├── lms-communication   # Notice & messaging
├── lms-gradebook       # Gradebook & grades
├── lms-report          # Reporting & analytics
├── lms-gateway         # API gateway
└── lms-admin           # Admin & system management
```

---

## Configuration

* **application.yaml**: common defaults and profile selector
* **application-{profile}.yaml**: environment‑specific overrides
* **application.example.yaml**: safe templates for setup

> Use `${ENV_VAR:default}` placeholders for sensitive data and provide real values via environment variables or CI/CD.

---

## API Documentation

* Swagger UI: `http://localhost:{port}/api/your-project-name/swagger-docs`
* OpenAPI JSON: `http://localhost:{port}/api/your-project-name/v3/api-docs`

---

## Security & Health

* Actuator endpoints:

    * `/actuator/health`
    * `/actuator/metrics`
    * (customizable per profile)
* JWT and role configurations in your profile files or `security.yaml`

**Happy coding!**