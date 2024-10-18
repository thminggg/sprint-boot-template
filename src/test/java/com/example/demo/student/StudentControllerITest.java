package com.example.demo.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll(); // Clear the database before each test
    }

    @Test
    void canGetStudents() throws Exception {
        // Given
        Student student = new Student("Patrick", "patrick.hm.tse@gmail.com", LocalDate.of(1995, 11, 4));
        studentRepository.save(student); // Save a student to the database

        // When & Then
        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Patrick"))
                .andExpect(jsonPath("$[0].email").value("patrick.hm.tse@gmail.com"));
    }

    @Test
    void canRegisterNewStudent() throws Exception {
        // Given
        String studentJson = "{\"name\":\"Patrick\",\"email\":\"patrick.hm.tse@gmail.com\",\"dob\":\"1995-11-04\"}";

        // When & Then
        mockMvc.perform(post("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(studentJson))
                .andExpect(status().isOk());

        // Verify that the student was saved
        Student savedStudent = studentRepository.findStudentByEmail("patrick.hm.tse@gmail.com").orElse(null);
        assertNotNull(savedStudent);
        assertEquals("Patrick", savedStudent.getName());
    }

    @Test
    void canUpdateStudentEmail() throws Exception {
        // Given
        Student student = new Student("Patrick", "patrick.hm.tse@gmail.com", LocalDate.of(1995, 11, 4));
        studentRepository.save(student); // Save a student to the database
        Long studentId = student.getId();


        // When & Then
        mockMvc.perform(put("/api/v1/students/{studentId}", studentId)
                        .param("email", "patrick.updated@gmail.com")
                        .param("name", "Patrick Updated"))
                .andExpect(status().isOk());

        // Verify that the student's email was updated
        Student updatedStudent = studentRepository.findById(studentId).orElse(null);
        assertNotNull(updatedStudent);
        assertEquals("patrick.updated@gmail.com", updatedStudent.getEmail());
        assertEquals("Patrick Updated", updatedStudent.getName());
    }

    @Test
    void canDeleteStudent() throws Exception {
        // Given
        Student student = new Student("Patrick", "patrick.hm.tse@gmail.com", LocalDate.of(1995, 11, 4));
        studentRepository.save(student); // Save a student to the database
        Long studentId = student.getId();

        // When & Then
        mockMvc.perform(delete("/api/v1/students/{studentId}", studentId))
                .andExpect(status().isOk());

        // Verify that the student was deleted
        assertFalse(studentRepository.existsById(studentId));
    }
}
