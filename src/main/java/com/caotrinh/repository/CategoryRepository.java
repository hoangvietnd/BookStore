package com.caotrinh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.caotrinh.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
	@Query("SELECT c FROM Category c")
	Category findCategoryById(String id);

}
