package com.caotrinh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.caotrinh.entities.Customer;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	@Query("SELECT c FROM Customer c where  c.username= :username")
	public Customer findByUserName(@Param("username") String username);
	
	@Query("SELECT c FROM Customer c where  c.email= :email")
	public Customer findByEmail(@Param("email") String email);
	
}
