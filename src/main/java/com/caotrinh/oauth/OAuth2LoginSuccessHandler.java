package com.caotrinh.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.caotrinh.entities.AuthenticationProvider;
import com.caotrinh.entities.Customer;
import com.caotrinh.service.CustomerService;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	@Autowired
	private CustomerService customerService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
		String email = oAuth2User.getEmail();
		Customer customer = customerService.getCustomerByEmail(email);
		String name = oAuth2User.getName();
		if (customer == null) {
			// register as a new customer
			customerService.createNewCustomerAfterOAuthLoginSuccess(email, name, AuthenticationProvider.GOOGLE);
		} else {
			// update existing customer
			customerService.updateCustomerAfterOAuthLoginSuccess(customer, name, AuthenticationProvider.GOOGLE);
		}
		System.out.println("Email: " + email);
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
