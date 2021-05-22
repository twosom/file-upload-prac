package com.example.fileuploadprac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FileUploadPracApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileUploadPracApplication.class, args);
    }

}
