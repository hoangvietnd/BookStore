package com.caotrinh.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.caotrinh.entities.*;
import com.caotrinh.service.BookStoreService;
import com.caotrinh.service.CustomerService;
import com.caotrinh.service.MailService;

@CrossOrigin("*")
@org.springframework.web.bind.annotation.RestController
public class RestController {

	@Autowired
	private BookStoreService bookService; 
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private MailService mailService;
	
	@GetMapping("/listBook")
	public List<Book> homeService(){
		return bookService.listBook();
	}
	
	@GetMapping("/getBook/{id}")
	public Optional<Book> getById(@PathVariable("id") int id){
		return bookService.getBookById(id);
	}
	
	@GetMapping("/pagination")
	public Page<Book> pagination(int pageNumber){
		return bookService.pagination(pageNumber);
	}
	
	@GetMapping("/filterPrice")
	public List<Book> filterPrice(double price1, double price2){
		return bookService.filterPrice(price1, price2);
	}
	
	@GetMapping("/autoComplete")
	public List<Book> autoComplete( String name){
		System.out.println("Name : "+name);
		return bookService.autoComplete(name);
	}
	/*
	 * Shopping cart
	 */
	@GetMapping("/addCart")
	public ShoppingCart addCart(HttpSession session, int id) {
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		if (cart == null) {
			cart = new ShoppingCart();
			session.setAttribute("cart", cart);
		}
		if (id != 0) {
			Book p = bookService.findBookById(id);
			cart.add(id, p);
			System.out.println("them san pham vao gio hang thanh cong");
		}
		return cart;
	}
	
	@GetMapping("/removeCart")
	public ShoppingCart removeCart(HttpSession session, int id) {
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		if (cart == null) {
			cart = new ShoppingCart();
			session.setAttribute("cart", cart);
		}
		if (id != 0) {
			cart.remove(id);
		}
		return cart;
	}
	
	@GetMapping("/getCart")
	public List getCart(HttpSession session) {
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		if (cart == null) {
			cart = new ShoppingCart();
			session.setAttribute("cart", cart);
		}
		return cart.getItems();
	}
	
	@GetMapping("/checkUserName")
	public boolean checkUserName(String userName) {	
		Customer customer = customerService.findCustomerByUserName(userName);
		if(customer != null) {
			return true;
		}else {
			return false;
		}
	}
	
	@PostMapping("/sendMail")
	public boolean sendMail(@ModelAttribute("contact") ContactEmail contactEmail) {
		System.out.println(contactEmail.toString());
		mailService.sendMail(contactEmail);
		return true;
	}
}
