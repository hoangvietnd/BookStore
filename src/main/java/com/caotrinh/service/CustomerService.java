package com.caotrinh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.caotrinh.entities.Customer;
import com.caotrinh.repository.CustomerRepository;

@Service
public class CustomerService implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public void saveCustomer(Customer customer) {
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		customer.setRole("user");
		customerRepository.save(customer);
	}
	
	public Customer findCustomerByUserName(String userName) {
		return customerRepository.findByUserName(userName);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer user = customerRepository.findByUserName(username);
        if (user == null) throw new UsernameNotFoundException(username);
        return new CustomerDetail(user);
	}
	
}
