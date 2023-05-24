package com.spring.batch.config;

import com.spring.batch.entity.Customer;
import org.springframework.batch.item.ItemProcessor;

public class CustomerProcessor implements ItemProcessor<Customer,Customer> {

    @Override
    public Customer process(Customer customer) {
        //write your own custom process here
            return customer;
    }
}
