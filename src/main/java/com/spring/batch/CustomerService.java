package com.spring.batch;

import com.spring.batch.entity.Customer;
import com.spring.batch.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getCustomers(){
        return customerRepository.findAll().stream().sorted(Comparator.comparing(Customer::getId)).toList();
    }
}
