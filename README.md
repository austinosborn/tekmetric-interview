# Online Auto Shop Spring Boot Application

## Overview

This project is a demo Spring Boot application designed to showcase backend development skills using modern best practices. 

The application simulates an online auto shop where shop owners can catalog digital products, and buyers can view and manage their carts. 

This project includes rudimentary user authentication, role-based access control, and an in-memory H2 database for demonstration purposes.

# Running the Application

This guide provides instructions on how to build and run the application using Maven.

## Prerequisites

Before you begin, ensure you have the following installed on your system:

- **Java Development Kit (JDK)**: Version 22 or higher. Ensure `JAVA_HOME` is set correctly.
- **Maven**: Any version with support for Java 22. Recommend version 3.9.8 or higher.

## Building the Application

To build the application, follow these steps:

1. **Clone the Repository**

   ```bash
   git clone git@github.com:austinosborn/tekmetric-interview.git

2. **Navigate to the Project Directory and run**:
   Open a terminal and navigate to the root directory of the project.

   ```bash
   cd /backend
   mvn clean install spring-boot:run

# Demo
   
## Postman Setup

   For ease of demo purposes, I have created a Postman collection to demonstrate the full functionality of the API, including logins to preconfigured shop owner
   and buyer accounts.

   - Download Postman (Desktop Agent, since this is necessary when executing on localhost): https://www.postman.com/downloads/
   - Open the public workspace: https://www.postman.com/austinosborn/workspace/tekmetric-project
   - In the environment drop-down (top-right), select the "Demonstration" environment. This prepopulates {{backendURL}} as `http://localhost:8080`, which is 
     the listening port for this application.

## Postman Usage

   The Postman workspace is divided into three main sections.
   1. Login
   2. Product
   3. Cart

   You can navigate between these and interact with the API as you wish. There are various examples spread throughout to demonstrate handling of sad path outcomes (non 200 responses).

### Login

   The `Login` collection has a singular request with 4 preconfigured examples. When a valid login is provided, it returns a JWT token for the user.

   1. **Owner Login** - Using this endpoint will log you in as a Shop Owner user. It automatically will set your `{{token}}` environment variable. This 
      will grant you access to use the `Product` endpoints.
   2. **Buyer Login** - Using this endpoint will log you in as a Buyer user. It automatically will set your `{{token}}` environment variable. This
       will grant you access to use the `Cart` endpoints.

   The other two are examples to demonstrate a bad request, or what happens when you provide incorrect login credentials.


### Product

The `Product` collection gives owner user access to the API to create, update, and remove products. It also gives all authenticated users access to browse the product 
catalog, for all available parts, or a specific part in particular.

Note: When you create a new part, this is loaded into the environment variable {{productId}}, where you can safely start to perform update and delete operations.



### Cart

The `Cart` collection gives buyer user access to the API to view, add/remove/update cart items, and checkout. 

#### Checkout
For demo purposes, checkout is a simplified operation that would represent the purchase of their cart of parts. It returns the same shape response as a standard
cart response, and deducts the purchased quantity from the respective product listings.

# Key Concepts
### 1. **Spring Boot Framework**

- **Spring Boot**: A framework for building stand-alone, production-grade Spring-based applications. It simplifies configuration and deployment through conventions and auto-configuration.
### 2. **User Authentication and Authorization**

- **Bearer Token Authentication**: Users authenticate with a bearer token that expires every 24 hours. This method is used to secure API endpoints.
- **Role-Based Access Control**: Users are assigned roles (`SHOP_OWNER` and `BUYER`).
### 3. **Database Integration**

- **H2 Database**: An in-memory database used for demo purposes. It allows rapid development and testing without the need for an external database server.
  - Data is initialized with H2 DDL (`schema.sql`) and DML (`data.sql`) scripts.

### 4. **Entity Models and Relationships**
- **User Model**: Represents application users with roles. The `email` is used as the unique identifier for authentication purposes.
- **Product Model**: Represents digital products with fields such as `name`, `description`, `price`, and `availableQuantity`. 
- **CartItem Model**: Represents a product in a buyer user's cart. Includes a `product_id` and `quantity`. `totalPrice` is persisted to demo usage of entity listeners

### 5. **Security Configuration**
- **Method Security**: `UserVerification` service provides a means to verify the user has the roles appropriate to perform the operation
- **JWT Token Management**: Generates and validates JWT tokens for securing RESTful API endpoints.

### 6. **RESTful API Design**
- **Endpoints**: API endpoints are provided for user auth, managing products (as a shop owner), and adding/purchasing products (as a buyer). 
- The API is designed to be intuitive and follows RESTful principles.

### 7. **Exception Handling**
- **Custom Exceptions**: Handles exceptions such as `NotFoundException` for better error management and user feedback. Any unexpected application error is 
 returned as a 500 with a unique error ID, and is logged with the corresponding error to make debugging easier.


