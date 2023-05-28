package com.spring.batch.service;

import com.spring.batch.entity.Customer;
import com.spring.batch.repository.CustomerRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job singleThreadedJob;
    @Autowired
    private Job partitionerJob;

    public List<Customer> getCustomers(){
        return customerRepository.findAll().stream().sorted(Comparator.comparing(Customer::getId)).toList();
    }

    public ResponseEntity<String> singleThreadedBatchJob(){
        return runSpringBatch(singleThreadedJob);
    }
    public ResponseEntity<String> partitionerBatchJob(){
        return runSpringBatch(partitionerJob);
    }

    private ResponseEntity<String> runSpringBatch(Job job) {
        JobParameters jobParameters = buildJobParams();
        try {
            jobLauncher.run(job, jobParameters);
            DateFormat dateObj = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z");
            Date res = new Date(jobParameters.getLong("startAt"));
            return ResponseEntity.ok("Job started at "+dateObj.format(res));
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body("Error Scheduling the job");
    }

    private JobParameters buildJobParams(){
        return new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
    }

}
