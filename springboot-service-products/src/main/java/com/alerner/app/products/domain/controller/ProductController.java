package com.alerner.app.products.domain.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.alerner.app.products.domain.entity.Product;
import com.alerner.app.products.domain.service.IProductService;

@RestController

public class ProductController 
{
	
	@Autowired
	private Environment env;
	
	@Value("${server.port}")
	private Integer port;
	
	@Autowired
	private IProductService iProductService;

	@GetMapping("/list")
	public List<Product> list()
	{
		return iProductService.findAll().stream()
				.map(product -> {
					//product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
					product.setPort(port);
					return product;
				}).collect(Collectors.toList());
	}
	
	
	@GetMapping("/detail/{id}")
	public Product detail(@PathVariable Long id)
	{
		Product product = iProductService.findById(id);
		//product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		product.setPort(port);
		return product;
	}
}
