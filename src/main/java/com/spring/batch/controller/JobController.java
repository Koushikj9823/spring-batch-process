package com.spring.batch.controller;

import com.spring.batch.entity.Customer;
import com.spring.batch.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/customers/import/singleThread")
    public ResponseEntity<String> importCsvToDBJob() {
        return customerService.singleThreadedBatchJob();
    }

    @PostMapping("/customers/import/partitioner")
    public ResponseEntity<String> importCsvToDBJobByPartitioner() {
        return customerService.partitionerBatchJob();
    }

    @RequestMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());
    }
}
