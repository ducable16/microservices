package com.example.config;

import com.example.entity.Product;
import com.example.service.BatchService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfig {

    @Autowired
    BatchService batchService;
    @Autowired
    PlatformTransactionManager transactionManager;
    @Lazy
    public Job runJob(JobRepository jobRepository) {
        return new JobBuilder("MSTabcNEUser", jobRepository)
                .start(step(jobRepository))
                .build();
    }

    @Bean
    @Lazy
    public Step step(JobRepository jobRepository) {
        return new StepBuilder("csv-step", jobRepository)
                .<Product, Product>chunk(10, transactionManager)
                .reader(batchService.itemReader())
                .writer(batchService.itemWriter())
                .build();
    }
}
