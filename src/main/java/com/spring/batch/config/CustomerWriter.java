package com.spring.batch.config;

import com.spring.batch.entity.Customer;
import com.spring.batch.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerWriter implements ItemWriter<Customer> {

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public void write(Chunk<? extends Customer> chunk) throws Exception {
        log.info("Thread Name: {}",Thread.currentThread().getName());
//        customerRepository.saveAll(chunk);
        RepositoryItemWriter<Customer> writer = new RepositoryItemWriter<>();
        writer.setRepository(customerRepository);
        writer.setMethodName("saveAll");
    }
}
