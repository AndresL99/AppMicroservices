package com.alerner.app.products.domain.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
					product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
					//product.setPort(port);
					return product;
				}).collect(Collectors.toList());
	}
	
	
	@GetMapping("/detail/{id}")
	public Product detail(@PathVariable Long id) throws InterruptedException
	{
		if(id.equals(10L))
		{
			throw new IllegalStateException("Not found product.");
		}
		
		if(id.equals(7L))
		{
			TimeUnit.SECONDS.sleep(5L);
		}
		
		Product product = iProductService.findById(id);
		product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		//product.setPort(port);
		

		return product;
	}
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Product create(@RequestBody Product product)
	{
		return iProductService.save(product);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Product update(@RequestBody Product product, @PathVariable Long id)
	{
		Product productDb=iProductService.findById(id);
		
		productDb.setName(product.getName());
		productDb.setPrice(product.getPrice());
		
		return iProductService.save(productDb);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable Long id)
	{
		iProductService.deleteById(id);
	}
}
