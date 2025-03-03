package com.example.controller;

import com.example.request.CreateUserRequest;
import com.example.request.UpdateUserRequest;
import com.example.service.*;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DataController {

    @Autowired
    private CreateUserService createUserService;
    @Autowired
    private DeleteUserService deleteUserService;
    @Autowired
    private UpdateUserService updateUserService;
    @Autowired
    private BatchService batchService;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobRepository jobRepository;

    @PostMapping("/registration")
    public ResponseEntity<?> createUser(
            @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(createUserService.createUser(request));
    }

    @GetMapping("/delete")
    public ResponseEntity<?> deleteUser(
            @RequestParam String userID) {
        return deleteUserService.deleteUser(userID);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> updateUser (
            @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(updateUserService.updateUser(request));
    }

    @PostMapping("/start-batch")
    public ResponseEntity<String> startBatchJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(batchService.runJob(jobRepository), jobParameters);

            return ResponseEntity.ok("Job started with ID: " + jobExecution.getId()
                    + ", Status: " + jobExecution.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Job failed to start: " + e.getMessage());
        }
    }
//    @PostMapping("/notification")
//    public ResponseEntity<?> sendMessage(@RequestBody Notification notification) {
//        rabbitTemplate.convertAndSend("directExchange", "noti", notification);
//        return ResponseEntity.ok(notification);
//    }
}
