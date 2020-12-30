package com.caotrinh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caotrinh.entities.Category;
import com.caotrinh.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository repository;

	public List<Category> getAllCategory() {
		return repository.findAll();

	}

}
