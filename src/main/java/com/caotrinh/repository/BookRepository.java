package com.caotrinh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.caotrinh.entities.Book;

@Repository 
public interface BookRepository extends JpaRepository<Book, Integer>{

	@Query("SELECT b FROM Book b WHERE b.price BETWEEN  ?1 AND ?2")
	List<Book> findByPrice(double price1, double price2);
	
	@Query("SELECT b FROM Book b WHERE b.name like %?1%")
	List<Book> autoComplete(String name);
	
	@Query("SELECT b FROM Book b WHERE b.id = ?1")
	Book findBookById(int id);
}
