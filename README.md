Spring Boot API Program
=======================

This project implements a simple API using Spring Boot. It provides endpoints for managing customers and their addresses.

Endpoints
---------

### Add Customer

*   **Method**: POST
    
*   **URL**: **/add_customer**
    
*   { "name": "string", "lastName": "string", "age": "integer", "email": "string", "address": "string"}
    
*   **Description**: Adds a new customer with the provided details. Also adds the customer's address.
    

### Add Address to Customer

*   **Method**: POST
    
*   **URL**: **/add_address_to_customer**
    
*   **Request Parameters**:
    
    *   **id**: Customer ID (Long)
        
    *   **address**: Address (String)
        
*   **Description**: Adds an address to an existing customer identified by their ID.
    

### Get Customer by ID

*   **Method**: GET
    
*   **URL**: **/get_customer_by_id**
    
*   **Request Parameters**:
    
    *   **id**: Customer ID (Long)
        
*   **Description**: Retrieves a customer's details by their ID.
    

### Get All Customers

*   **Method**: GET
    
*   **URL**: **/get_all_customers**
    
*   **Description**: Retrieves details of all customers.
    

### Search Customer

*   **Method**: GET
    
*   **URL**: **/search**
    
*   **Request Parameters**:
    
    *   **name**: Customer's first name (String, optional)
        
    *   **lastName**: Customer's last name (String, optional)
        
*   **Description**: Searches for customers based on provided first name or last name.
    

### Update Customer Email

*   **Method**: GET
    
*   **URL**: **/update_email**
    
*   **Request Parameters**:
    
    *   **id**: Customer ID (Long)
        
    *   **email**: New email (String)
        
*   **Description**: Updates a customer's email address.
    

### Update Customer Address

*   **Method**: GET
    
*   **URL**: **/update_address**
    
*   **Request Parameters**:
    
    *   **id**: Address ID (Long)
        
    *   **address**: New address (String)
        
*   **Description**: Updates a customer's address.
    

Usage
-----

1.  Ensure you have Java and Maven installed.
    
2.  Clone the repository to your local machine.
    
3.  Build the project using Maven: **mvn clean install**.
    
4.  Run the application.
    
5.  Access the API endpoints using a tool like Postman or by making HTTP requests programmatically.
