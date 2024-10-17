# Spring Boot Student Management API

This is a simple Spring Boot application that provides a RESTful API for managing students. It allows you to perform CRUD operations on student records.

## Table of Contents

- [Getting Started](#getting-started)
- [API Routes](#api-routes)
  - [Get All Students](#get-all-students)
  - [Register a New Student](#register-a-new-student)
  - [Update Student Email](#update-student-email)
  - [Delete a Student](#delete-a-student)

## Getting Started

To get started with this project, follow these steps:

1. **Clone the repository:**

   ```bash
   git clone https://github.com/yourusername/student-management-api.git
   cd student-management-api
   ```

2. **Set up PostgreSQL:**
   Ensure you have a PostgreSQL database up and running. You can create a database named `student` and configure the connection in the `src/main/resources/application.properties` file as follows:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/student
   spring.datasource.username=postgres
   spring.datasource.password=123123
   ```

3. **Build the project:**
   Make sure you have Maven installed, then run:

   ```bash
   mvn clean install
   ```

4. **Run the application:**
   You can run the application using:

   ```bash
   mvn spring-boot:run
   ```

5. **Access the API:**
   The API will be available at `http://localhost:8080/api/v1/students`.

## API Routes

### Get All Students

- **Endpoint:** `GET /api/v1/students`
- **Description:** Retrieves a list of all students.
- **Response:**
  ```json
  [
    {
      "id": 1,
      "name": "John Doe",
      "email": "john.doe@example.com"
    },
    {
      "id": 2,
      "name": "Jane Smith",
      "email": "jane.smith@example.com"
    }
  ]
  ```

### Register a New Student

- **Endpoint:** `POST /api/v1/students`
- **Description:** Registers a new student.
- **Payload:**
  ```json
  {
    "name": "New Student",
    "email": "new.student@example.com"
  }
  ```

### Update Student Email

- **Endpoint:** `PUT /api/v1/students/{studentId}`
- **Description:** Updates the email of an existing student.
- **Query Parameters:**
  - `name` (optional): The new name of the student.
  - `email` (optional): The new email of the student.
- **Example Request:**
  ```http
  PUT /api/v1/students/1?name=Updated Name&email=updated.email@example.com
  ```

### Delete a Student

- **Endpoint:** `DELETE /api/v1/students/{studentId}`
- **Description:** Deletes a student by their ID.
- **Example Request:**
  ```http
  DELETE /api/v1/students/1
  ```

## Conclusion

This API provides a simple interface for managing student records. You can extend it further by adding more features as needed. For any questions or issues, feel free to reach out!
