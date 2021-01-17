package com.caotrinh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.caotrinh.entities.Book;
import com.caotrinh.repository.BookRepository;

@Service
public class ProductService {
	@Autowired
	private BookRepository bookRepository;

	public List<Book> findByPrice(double price1, double price2) {
		return bookRepository.findByPrice(price1, price2);
	}

	public Book save(Book entity) {
		return bookRepository.save(entity);
	}

	public <S extends Book> Optional<S> findOne(Example<S> example) {
		return bookRepository.findOne(example);
	}

	public Page<Book> listAll(int pageNumber, String keyword) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
		if (keyword != null) {
			return bookRepository.findAll(keyword, pageable);
		}
		return bookRepository.findAll(pageable);
	}

	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	public List<Book> findAll(Sort sort) {
		return bookRepository.findAll(sort);
	}

	public List<Book> findAllById(Iterable<Integer> ids) {
		return bookRepository.findAllById(ids);
	}

	public <S extends Book> List<S> saveAll(Iterable<S> entities) {
		return bookRepository.saveAll(entities);
	}

	public Optional<Book> findById(Integer id) {
		return bookRepository.findById(id);
	}

	public void flush() {
		bookRepository.flush();
	}

	public <S extends Book> S saveAndFlush(S entity) {
		return bookRepository.saveAndFlush(entity);
	}

	public boolean existsById(Integer id) {
		return bookRepository.existsById(id);
	}

	public void deleteInBatch(Iterable<Book> entities) {
		bookRepository.deleteInBatch(entities);
	}

	public <S extends Book> Page<S> findAll(Example<S> example, Pageable pageable) {
		return bookRepository.findAll(example, pageable);
	}

	public void deleteAllInBatch() {
		bookRepository.deleteAllInBatch();
	}

	public Book getOne(Integer id) {
		return bookRepository.getOne(id);
	}

	public <S extends Book> long count(Example<S> example) {
		return bookRepository.count(example);
	}

	public <S extends Book> boolean exists(Example<S> example) {
		return bookRepository.exists(example);
	}

	public <S extends Book> List<S> findAll(Example<S> example) {
		return bookRepository.findAll(example);
	}

	public long count() {
		return bookRepository.count();
	}

	public void deleteById(Integer id) {
		bookRepository.deleteById(id);
	}

	public <S extends Book> List<S> findAll(Example<S> example, Sort sort) {
		return bookRepository.findAll(example, sort);
	}

	public void delete(Book entity) {
		bookRepository.delete(entity);
	}

	public void deleteAll(Iterable<? extends Book> entities) {
		bookRepository.deleteAll(entities);
	}

	public void deleteAll() {
		bookRepository.deleteAll();
	}

}
