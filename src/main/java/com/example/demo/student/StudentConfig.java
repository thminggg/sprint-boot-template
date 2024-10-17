package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student patrick = new Student(
                    "Patrick",
                    "patrick.hm.tse@gmail.com",
                    LocalDate.of(1995, Month.NOVEMBER, 4)
            );

            Student diulasing = new Student(
                    "Lasing",
                    "diulasing21@gmail.com",
                    LocalDate.of(1999, Month.SEPTEMBER, 9)
            );

            repository.saveAll(List.of(patrick, diulasing));
        };
    }
}
