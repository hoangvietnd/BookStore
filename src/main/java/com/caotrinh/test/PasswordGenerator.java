package com.caotrinh.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
	
	public static void main(String[] args) {
		BCryptPasswordEncoder encorder = new BCryptPasswordEncoder();
		String pass = "12";
		String enpass = encorder.encode(pass);
		System.out.println(enpass);
		
	}
}
