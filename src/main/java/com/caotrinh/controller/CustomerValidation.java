package com.caotrinh.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.caotrinh.entities.Customer;

@Service
public class CustomerValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Customer.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmpty(errors, "name", "notempty.customer.name");
		ValidationUtils.rejectIfEmpty(errors, "password", "notempty.customer.password");
		ValidationUtils.rejectIfEmpty(errors, "address", "notempty.customer.address");
		ValidationUtils.rejectIfEmpty(errors, "yearOfBirth", "notempty.customer.yearOfBirth");
		ValidationUtils.rejectIfEmpty(errors, "phone", "notempty.customer.phone");
		ValidationUtils.rejectIfEmpty(errors, "email", "notempty.customer.email");
		
		Customer customer = (Customer) target;
		if(customer.getPassword().length() < 6) {
			errors.rejectValue("password", "password.length.customer");
		} else if(customer.getYearOfBirth() > LocalDate.now().getYear()) {
			errors.rejectValue("yearOfBirth", "yearOfBirth.customer.notvalid.currentyear");
		} else if((LocalDate.now().getYear() - customer.getYearOfBirth()) < 18) {
			errors.rejectValue("yearOfBirth", "yearOfBirth.customer.notvalid.age");
		} else if(customer.getPhone().length() < 10) {
			errors.rejectValue("phone", "phone.customer.notvalid.length");
		}
	}

}
