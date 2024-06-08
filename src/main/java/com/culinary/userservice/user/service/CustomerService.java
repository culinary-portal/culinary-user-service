package com.culinary.userservice.user.service;

import com.culinary.userservice.user.exception.CustomerNotFoundException;
import com.culinary.userservice.user.model.Customer;
import com.culinary.userservice.user.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;


    public Customer getLoggedCustomer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found!");
        }
        return customer.get();

    }
}
