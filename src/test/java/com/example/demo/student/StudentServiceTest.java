package com.example.demo.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Create a common Student instance for use in tests
        student = new Student("Patrick", "patrick.hm.tse@gmail.com", LocalDate.of(1995, 11, 4));
    }

    @Test
    void canGetStudents() {
        // Given
        when(studentRepository.findAll()).thenReturn(List.of(student));

        // When
        List<Student> students = studentService.getStudents();

        // Then
        assertEquals(1, students.size());
        assertEquals("Patrick", students.getFirst().getName());
    }

    @Test
    void canAddNewStudent() {
        // Given
        when(studentRepository.findStudentByEmail(student.getEmail())).thenReturn(Optional.empty());

        // When
        studentService.addNewStudent(student);

        // Then
        verify(studentRepository).save(student);
    }

    @Test
    void cannotAddExistingStudent() {
        // Given
        when(studentRepository.findStudentByEmail(student.getEmail())).thenReturn(Optional.of(student));

        // When & Then
        Exception exception = assertThrows(IllegalStateException.class, () -> studentService.addNewStudent(student));
        assertEquals("email taken", exception.getMessage());
        verify(studentRepository, never()).save(any());
    }

    @Test
    void canDeleteStudent() {
        // Given
        Long studentId = 1L;
        when(studentRepository.existsById(studentId)).thenReturn(true);

        // When
        studentService.deleteStudent(studentId);

        // Then
        verify(studentRepository).deleteById(studentId);
    }

    @Test
    void cannotDeleteNonExistingStudent() {
        // Given
        Long studentId = 1L;
        when(studentRepository.existsById(studentId)).thenReturn(false);

        // When & Then
        Exception exception = assertThrows(IllegalStateException.class, () -> studentService.deleteStudent(studentId));
        assertEquals("student with id " + studentId + " does not exist", exception.getMessage());
        verify(studentRepository, never()).deleteById(any());
    }

    @Test
    void canUpdateStudent() {
        // Given
        Long studentId = 1L;
        Student existingStudent = new Student(studentId, "Patrick", "patrick.hm.tse@gmail.com",
                LocalDate.of(1995, 11, 4));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        String newName = "Patrick Updated";
        String newEmail = "patrick.updated@gmail.com";
        when(studentRepository.findStudentByEmail(newEmail)).thenReturn(Optional.empty());

        // When
        studentService.updateStudent(studentId, newName, newEmail);

        // Then
        assertEquals(newName, existingStudent.getName());
        assertEquals(newEmail, existingStudent.getEmail());
    }

    @Test
    void cannotUpdateNonExistingStudent() {
        // Given
        Long studentId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(IllegalStateException.class,
                () -> studentService.updateStudent(studentId, "New Name", "new.email@gmail.com"));
        assertEquals("student with id " + studentId + " does not exist", exception.getMessage());
    }
}
