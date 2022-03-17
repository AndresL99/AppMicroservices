package com.alerner.app.item.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.alerner.app.item.domain.Item;
import com.alerner.app.item.domain.Product;
import com.alerner.app.item.domain.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class ItemController 
{

	@Autowired
	@Qualifier("serviceRestTemplate")
	private ItemService itemService;
	
	@GetMapping("/list")
	public List<Item>list()
	{
		return itemService.findAll();
	}
	
	
	@HystrixCommand(fallbackMethod = "alternativeMethod")
	@GetMapping("/detail/{id}/size/{size}")
	public Item detail(@PathVariable Long id, @PathVariable Integer size)
	{
		return itemService.findById(id, size);
	}
	
	public Item alternativeMethod(Long id, Integer size)
	{
		Item item = new Item();
		Product product = new Product();
		
		item.setSize(size);
		product.setId(id);
		product.setName("Sony");
		product.setPrice(300.00);
		item.setProduct(product);
		return item;
	}
}
