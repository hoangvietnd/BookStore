package com.caotrinh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caotrinh.entities.ContactEmail;
import com.caotrinh.entities.Customer;
import com.caotrinh.service.BookStoreService;
import com.caotrinh.service.CustomerService;

@Controller
public class MainController {

	@Autowired
	private CustomerValidation customerValidation;
	
	@Autowired
	private BookStoreService bookService; 
	
	@Autowired
	private CustomerService customerService;
	
	
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(value = {"/","/index"})
	public String home(Model model) {
		model.addAttribute("listBook", bookService.pagination(0));
		return "index";
	}
	
	@GetMapping(value = "/single-product/{id}")
	public String singleProduct(@PathVariable int id, Model model) {
		model.addAttribute("book", bookService.getBookById(id).get());
		return "single-product";
	}
	
	@RequestMapping(value = "/shop-grid")
	public String shop(Model model) {
		model.addAttribute("listBook", bookService.pagination(0));
		return "shop-grid";
	}
	@GetMapping(value = "/about")
	public String about() {
		return "about";
	}

	@GetMapping(value = "/cart")
	public String cart() {
		return "cart";
	}

	@GetMapping(value = "/checkout")
	public String checkout() {
		return "checkout";
	}
	
	@GetMapping("/addCustomerForm")
	public String addCustomerForm(Model model){
		model.addAttribute("customer", new Customer());
		return "addCustomer";
	}
	
	@PostMapping("/addCustomer")
	public String addCustomer(@ModelAttribute("customer") Customer customer, Errors errors, Model model) {
		ValidationUtils.invokeValidator(customerValidation, customer, errors);
		if (errors.hasErrors()) {
            return "addCustomer";
        } else {
        	customerService.saveCustomer(customer);
        	model.addAttribute("listBook", bookService.pagination(0));
    		return "index";
        }
		
	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
	
	@GetMapping("/user")
	public String user() {
		return "user";
	}
	
	@GetMapping("/403")
	public String notfoundpage() {
		return "403";
	}
	
	@GetMapping("/contact")
	public String contact(Model model) {
		model.addAttribute("contact", new ContactEmail());
		return "contact";
	}
	
	@GetMapping("/myaccount")
	public String myaccount(Model model) {
		model.addAttribute("customer", new Customer());
		return "myaccount";
	}
	
}
