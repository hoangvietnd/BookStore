package com.caotrinh.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.caotrinh.dto.ProductDTO;
import com.caotrinh.entities.Book;
import com.caotrinh.entities.Category;
import com.caotrinh.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	ProductService productService;

	@RequestMapping("/list")
	public String showList(Model model) {
		model.addAttribute("PRODUCTS", productService.findAll());
		return "view-product";
	}

	@GetMapping("/")
	public String addOrEdit(ModelMap model) {
		ProductDTO dto = new ProductDTO();
		model.addAttribute("PRODUCTDTO", dto);
		model.addAttribute("ACTION", "/product/saveOrUpdate");
		return "add-product";

	}

	@PostMapping("/saveOrUpdate")
	public String save(ModelMap model, @ModelAttribute("PRODUCTDTO") ProductDTO dto) {
		Optional<Book> optional = productService.findById(dto.getId());
		Book book = null;
		String image = "book-preview.png";
		Path path = Paths.get("uploads/");
		if (optional.isPresent()) {
			// update
			if (dto.getImage().isEmpty()) {
				image = optional.get().getImage();
			} else {
				try {
					InputStream inputStream = dto.getImage().getInputStream();
					Files.copy(inputStream, path.resolve(dto.getImage().getOriginalFilename()),
							StandardCopyOption.REPLACE_EXISTING);
					image = dto.getImage().getOriginalFilename().toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			// add new
			if (!dto.getImage().isEmpty()) {
				try {
					InputStream inputStream = dto.getImage().getInputStream();
					Files.copy(inputStream, path.resolve(dto.getImage().getOriginalFilename()),
							StandardCopyOption.REPLACE_EXISTING);
					image = dto.getImage().getOriginalFilename().toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		book = new Book(dto.getId(), new Category(dto.getCategory(), ""), dto.getName(), dto.getPrice(), image);
		productService.save(book);
		return "add-product";

	}

	@RequestMapping("/edit/{id}")
	public String edit(ModelMap model, @PathVariable(name = "id") int id) {
		Optional<Book> optional = productService.findById(id);
		ProductDTO dto = null;
		if (optional.isPresent()) {
			Book book = optional.get();
			File file = new File("uploads/", book.getImage());
			try {
				FileInputStream input = new FileInputStream(file);
				MultipartFile multiImage = new MockMultipartFile("file", file.getName(), "text/plan",
						IOUtils.toByteArray(input));
				dto = new ProductDTO(book.getId(), book.getCategory().getId(), book.getName(), book.getPrice(),
						multiImage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addAttribute("PRODUCTDTO", dto);
		} else {
			model.addAttribute("PRODUCTDTO", new ProductDTO());
		}
		model.addAttribute("ACTION", "/product/saveOrUpdate");
		return "add-product";

	}
	
	@RequestMapping("/delete/{id}")
	public String delete(ModelMap model, @PathVariable(name = "id") int id) {
		productService.deleteById(id);
		model.addAttribute("PRODUCTS", productService.findAll());
		return "view-product";

	}

	@ModelAttribute(name = "CATEGORY")
	public List<Category> getCategory() {
		return productService.findAllCategory();

	}

}
