package com.batch;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Log4j2
public class SpringBatchApplication implements CommandLineRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        String fileName = "persons.zip";
        try {

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("nombre", fileName)
                    .addDate("fecha", new Date())
                    .toJobParameters();

            jobLauncher.run(job, jobParameters);

            Map<String, String> response = new HashMap<>();
            response.put("archivo", fileName);

            log.info(response.toString());

        } catch (Exception exception) {
            log.error("Error al iniciar el proceso batch. Error {}", exception.getMessage());
            throw new RuntimeException();
        }
    }
}
