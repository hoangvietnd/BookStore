package com.caotrinh.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caotrinh.entities.Book;
import com.caotrinh.entities.ContactEmail;
import com.caotrinh.entities.Customer;
import com.caotrinh.entities.OrderDetail;
import com.caotrinh.entities.ShoppingCart;
import com.caotrinh.entities.ShoppingCartItem;
import com.caotrinh.service.BookStoreService;
import com.caotrinh.service.CustomerService;
import com.caotrinh.service.OrderDetailService;

@Controller
public class MainController {

	@Autowired
	private CustomerValidation customerValidation;

	@Autowired
	private BookStoreService bookService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping(value = { "/", "/index" })
	public String homePage(Model model) {
		model.addAttribute("listBook", bookService.pagination(0));
		return "index";
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
	
	@GetMapping("/checkout")
	public String checkOut(HttpSession session, Model model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String userName = userDetails.getUsername();
		Customer customer = customerService.findCustomerByUserName(userName);
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		if (cart == null) {
			cart = new ShoppingCart();
			session.setAttribute("cart", cart);
		}
		model.addAttribute("customer", customer);
		model.addAttribute("numberOfItems", cart.getNumberOfItems());
		model.addAttribute("total", cart.getTotal());
		model.addAttribute("books", cart.getItems());
		return "checkout";
	}
	
	@GetMapping(value = "/saveOrder")
	public String saveOder(HttpSession session, OrderDetail orderDetail, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			String userName = userDetails.getUsername();
			ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
			List<ShoppingCartItem> items = cart.getItems();
			for (ShoppingCartItem shoppingCartItem : items) {
				orderDetail.setBook(shoppingCartItem.getItem());
				orderDetail.setCustomerUserName(userName);
				orderDetail.setQuantity(shoppingCartItem.getQuantity());
				orderDetailService.saveOrder(orderDetail);
			}
		}
		model.addAttribute("contact", new ContactEmail());
		return "contact";
	}

	@GetMapping("/addCustomerForm")
	public String addCustomerForm(Model model) {
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
			return "login";
		}
	}

	@GetMapping("/singleProduct/{id}")
	public String getSingleProduct(@PathVariable("id") int id, Model model) {
		model.addAttribute("book", bookService.findBookById(id));
		return "singleProduct";
	}

	/*
	 * Shopping cart
	 */
	@GetMapping("/addCart/{id}")
	public String addCart(HttpSession session, @PathVariable int id, Model model) {
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
		model.addAttribute("numberOfItems", cart.getNumberOfItems());
		model.addAttribute("total", cart.getTotal());
		model.addAttribute("books", cart.getItems());
		return "cart";
	}

	@GetMapping("/removeCart/{id}")
	public String removeCart(HttpSession session, @PathVariable("id") int id, Model model) {
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		if (cart == null) {
			cart = new ShoppingCart();
			session.setAttribute("cart", cart);
		}
		if (id != 0) {
			cart.remove(id);
		}
		model.addAttribute("numberOfItems", cart.getNumberOfItems());
		model.addAttribute("total", cart.getTotal());
		model.addAttribute("books", cart.getItems());
		return "cart";
	}

	@GetMapping("/getCart")
	public String getCart(HttpSession session, Model model) {
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		if (cart == null) {
			cart = new ShoppingCart();
			session.setAttribute("cart", cart);
		}
		model.addAttribute("numberOfItems", cart.getNumberOfItems());
		model.addAttribute("total", cart.getTotal());
		model.addAttribute("books", cart.getItems());
		return "cart";
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
