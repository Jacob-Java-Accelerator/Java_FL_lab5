# Java_FL_lab5

## Overview

This project is a Spring Boot application designed for Java Foundation Lab 5. It demonstrates core backend concepts including:

- User registration and authentication (JWT-based)
- Role-based access control (RBAC) with permissions
- Product CRUD operations
- PostgreSQL integration
- Exception handling and validation

## Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Setup Instructions](#setup-instructions)
- [API Endpoints](#api-endpoints)
- [Roles & Permissions](#roles--permissions)
- [Data Initialization](#data-initialization)
- [Error Handling](#error-handling)
- [Dependencies](#dependencies)
- [Environment Variables](#environment-variables)

---

## Features

- **User Registration & Login**: Secure registration and login with JWT token issuance.
- **Role Management**: Users and admins with different permissions.
- **Product Management**: CRUD operations for products, with delete restricted to users with specific permissions.
- **Exception Handling**: Global exception handler for meaningful error responses.
- **Environment-based Configuration**: Uses different properties for development.

---

## Architecture

- **Controllers**: Handle HTTP requests (`AuthenticationController`, `ProductController`)
- **Services**: Business logic for authentication, products, users, roles, and JWT.
- **Repositories**: Data access layer for users, roles, products, and permissions.
- **Models/DTOs**: Entity and data transfer objects for users, roles, products, and authentication.
- **Config**: Security and bean configuration.
- **Exception**: Custom exceptions and global error handling.

---

## Setup Instructions

### Prerequisites

- Java 17+
- Maven
- PostgreSQL

### Clone and Configure

1. Clone the repository.
2. Set up a PostgreSQL database and user.
3. Create a `.env` file or set environment variables for database and email credentials (see below).

### Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

The application will start on port `5000` by default.

---

## API Endpoints

### Authentication

- `POST /api/v1/auth/register`  
  Register a new user.  
  **Body:**

  ```json
  {
    "username": "string",
    "email": "string",
    "password": "string"
  }
  ```

- `POST /api/v1/auth/login`  
  Login and receive a JWT token.  
  **Body:**
  ```json
  {
    "username": "string",
    "password": "string"
  }
  ```

### Product Management

- `POST /api/v1/product`  
  Create a new product.  
  **Body:**

  ```json
  {
    "name": "string",
    "description": "string",
    "price": number
  }
  ```

- `GET /api/v1/product/{productId}`  
  Get a product by ID.

- `GET /api/v1/product`  
  List all products.

- `PUT /api/v1/product/{productId}`  
  Update a product.  
  **Body:** Same as create.

- `DELETE /api/v1/product/{productId}`  
  Delete a product (requires `USER` role and `DELETE` authority).

---

## Roles & Permissions

- **Roles**: `USER`, `ADMIN`
- **Permissions**: Defined in `AppPermissions.java` and assigned to roles.
- **Access Control**:
  - Only users with `USER` role and `DELETE` authority can delete products.

---

## Data Initialization

On startup, the application creates default users and roles if they do not exist:

- Default user:
  - Username: from `${spring.application.username}` (default: `user`)
  - Email: from `${spring.application.user_email}` (default: `user@gmail.com`)
  - Password: from `${spring.application.user_password}` (default: `123456`)
- Default admin:
  - Username: from `${spring.application.admin_username}` (default: `admin`)
  - Email: from `${spring.application.admin_email}` (default: `admin@gmail.com`)
  - Password: from `${spring.application.admin_password}` (default: `123456`)

---

## Error Handling

- Custom exceptions for user and global errors.
- Standardized error responses via `GlobalException.java`.

---

## Dependencies

- Spring Boot (Web, Security, Data JPA, Validation)
- PostgreSQL
- Lombok
- JWT (io.jsonwebtoken)
- Spring Boot DevTools (optional, for development)

---

## Environment Variables

Set these in your environment or `.env` file:

- `POSTGRESQL_HOST`
- `POSTGRESQL_PORT`
- `POSTGRESQL_DATABASE`
- `POSTGRESQL_USERNAME`
- `POSTGRESQL_PASSWORD`
- `EMAIL_HOST`
- `EMAIL_PORT`
- `EMAIL_USERNAME`
- `EMAIL_PASSWORD`
- `SECRET_KEY`
- `REGULAR_USERNAME`
- `REGULAR_USER_EMAIL`
- `REGULAR_USER_PASSWORD`
- `ADMIN_USERNAME`
- `ADMIN_PASSWORD`
- `ADMIN_EMAIL`

---

## Running Tests

```bash
mvn test
```
