package com.caotrinh.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProductDTO {
	private int id;
	private String category;
	private String name;
	private double price;
	private MultipartFile image;

	public ProductDTO() {
		super();
	}

	public ProductDTO(int id, String category, String name, double price, MultipartFile image) {
		super();
		this.id = id;
		this.category = category;
		this.name = name;
		this.price = price;
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

}
