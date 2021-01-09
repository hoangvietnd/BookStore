package com.caotrinh.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
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
import com.caotrinh.service.ProductService;

@Controller
@RequestMapping("/admin")
public class ProductController {
	@Autowired
	ProductService productService;

	@RequestMapping("/list")
	public String showList(Model model) {
		return listByPage(model, 1);
	}

	@GetMapping("/list/page/{pageNum}")
	public String listByPage(Model model, @PathVariable("pageNum") int pageNum) {
		Page<Book> page = productService.listAll(pageNum);
		List<Book> listProduct = page.getContent();
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("PRODUCTS", listProduct);
		return "view-product";

	}

	@GetMapping("/add")
	public String showAddOrEditForm(ModelMap model) {
		ProductDTO dto = new ProductDTO();
		model.addAttribute("PRODUCTDTO", dto);
		model.addAttribute("ACTION", "/admin/saveOrUpdate");
		return "addProduct";

	}

	@PostMapping("/saveOrUpdate")
	public String save(ModelMap model, @ModelAttribute("PRODUCTDTO") ProductDTO dto) throws IOException {
		Optional<Book> optional = productService.findById(dto.getId());
		Book book = null;
		String image = "/images/books/leather-book-preview.png";
		File file = new ClassPathResource("static/images/books").getFile();
		String canonicalPath = file.getCanonicalPath();
		Path path = Paths.get(canonicalPath);
		if (optional.isPresent()) {
			// update
			if (dto.getImage().isEmpty()) {
				image = optional.get().getImage();
			} else {
				try {
					InputStream inputStream = dto.getImage().getInputStream();
					Files.copy(inputStream, path.resolve(dto.getImage().getOriginalFilename()),
							StandardCopyOption.REPLACE_EXISTING);
					image = "/images/books/" + dto.getImage().getOriginalFilename().toString();
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
					image = "/images/books/" + dto.getImage().getOriginalFilename().toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		book = new Book(dto.getCategory(), dto.getName(), dto.getPrice(), image);
		productService.save(book);
		showList((Model) model);
		return "redirect:/admin/list";

	}

	@RequestMapping("/edit/{id}")
	public String edit(ModelMap model, @PathVariable(name = "id") int id, HttpServletRequest request)
			throws IOException {
		Optional<Book> optional = productService.findById(id);
		ProductDTO productDTO = null;
		if (optional.isPresent()) {
			Book book = optional.get();
			File file = new ClassPathResource("static" + book.getImage()).getFile();
			try {
				FileInputStream input = new FileInputStream(file);
				MultipartFile multiImage = new MockMultipartFile("file", file.getName(), "text/plan",
						IOUtils.toByteArray(input));
				productDTO = new ProductDTO(book.getId(), book.getCategory(), book.getName(), book.getPrice(),
						multiImage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addAttribute("PRODUCTDTO", productDTO);
		} else {
			model.addAttribute("PRODUCTDTO", new ProductDTO());
		}
		model.addAttribute("ACTION", "/admin/saveOrUpdate");
		return "addProduct";

	}

	@RequestMapping("/delete/{id}")
	public String delete(ModelMap model, @PathVariable(name = "id") int id) {
		productService.deleteById(id);
		showList((Model) model);
		return "view-product";

	}

//	@ModelAttribute(name = "CATEGORY")
//	public List<Category> getCategory() {
//		return productService.findAllCategory();
//
//	}

}
