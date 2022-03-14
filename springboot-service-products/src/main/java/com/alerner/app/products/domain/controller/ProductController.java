package com.alerner.app.products.domain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.alerner.app.products.domain.entity.Product;
import com.alerner.app.products.domain.service.IProductService;

@RestController

public class ProductController 
{
	@Autowired
	private IProductService iProductService;

	@GetMapping("/list")
	public List<Product> list()
	{
		return iProductService.findAll();
	}
	
	
	@GetMapping("/list/{id}")
	public Product detail(@PathVariable Long id)
	{
		return iProductService.findById(id);
	}
}
