//package com.something.demo.service;
//
//import com.something.demo.entity.User;
//import com.something.demo.repository.UserRepository;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.LineMapper;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Service
//public class BatchService {
//
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    PasswordEncoder passwordEncoder;
//    @Autowired
//    PlatformTransactionManager transactionManager;
//
//    public Job runJob(JobRepository jobRepository) {
//        return new JobBuilder("MSTabcNEUser", jobRepository)
//                .start(step(jobRepository))
//                .build();
//    }
//
//    public Step step(JobRepository jobRepository) {
//        return new StepBuilder("csv-step", jobRepository)
//                .<User, User>chunk(10, transactionManager)
//                .reader(itemReader())
//                .processor(itemProcessor())
//                .writer(itemWriter())
//                .build();
//    }
//
//    public LineMapper<User> lineMapper() {
//
//        DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<>();
//        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
//
//        lineTokenizer.setDelimiter(",");
//        lineTokenizer.setStrict(false);
//        lineTokenizer.setNames("name", "dateOfBirth", "age", "username", "password", "role");
//
//        BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
//        fieldSetMapper.setTargetType(User.class);
//
//        defaultLineMapper.setLineTokenizer(lineTokenizer);
//        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
//
//        return defaultLineMapper;
//    }
//
//    public FlatFileItemReader<User> itemReader() {
//        FlatFileItemReader<User> flatFileItemReader = new FlatFileItemReader<>();
//
//        // Cấu hình nguồn dữ liệu (file CSV)
//        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/users.csv"));
//        flatFileItemReader.setName("CSV-Reader");
//        flatFileItemReader.setLinesToSkip(1);
//        flatFileItemReader.setLineMapper(lineMapper());
//
//        return flatFileItemReader;
//    }
//
//    public ItemWriter<User> itemWriter() {
//        // Ghi dữ liệu vào MongoDB
//        return items -> userRepository.saveAll(items);
//    }
//
//    public ItemProcessor<User, User> itemProcessor() {
//        return user -> {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//            return user;
//        };
//    }
//}


package com.example.service;

import com.example.entity.Product;
import com.example.repository.ProductRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

@Service
public class BatchService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    PlatformTransactionManager transactionManager;

    public Job runJob(JobRepository jobRepository) {
        return new JobBuilder("MSTabcNEUser", jobRepository)
                .start(step(jobRepository))
                .build();
    }

    public Step step(JobRepository jobRepository) {
        return new StepBuilder("csv-step", jobRepository)
                .<Product, Product>chunk(10, transactionManager)
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }

    public LineMapper<Product> lineMapper() {

        DefaultLineMapper<Product> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("productID","productName", "price");
        BeanWrapperFieldSetMapper<Product> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Product.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }

    public FlatFileItemReader<Product> itemReader() {
        FlatFileItemReader<Product> flatFileItemReader = new FlatFileItemReader<>();

        // Cấu hình nguồn dữ liệu (file CSV)
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/products.csv"));
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());

        return flatFileItemReader;
    }

    public ItemWriter<Product> itemWriter() {
        // Ghi dữ liệu vào MongoDB
        return items -> productRepository.saveAll(items);
    }

}
