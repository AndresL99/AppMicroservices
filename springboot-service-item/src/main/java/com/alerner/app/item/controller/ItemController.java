package com.alerner.app.item.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alerner.app.item.domain.Item;
import com.alerner.app.item.domain.Product;
import com.alerner.app.item.domain.service.ItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RestController
public class ItemController 
{
	private final Logger logger = LoggerFactory.getLogger(ItemController.class);

	@Autowired
	private CircuitBreakerFactory cbFactory;
	
	@Autowired
	@Qualifier("serviceRestTemplate")
	private ItemService itemService;
	
	@GetMapping("/list")
	public List<Item>list(@RequestParam(name="name", required = false)String name,@RequestHeader(name="token", required = false)String token)
	{
		
		return itemService.findAll();
	}
	
	
	@GetMapping("/detail/{id}/size/{size}")
	public Item detail(@PathVariable Long id, @PathVariable Integer size)
	{
		return cbFactory.create("items")
				.run(() -> itemService.findById(id, size), e -> alternativeMethod(id, size,e));
	}
	
	@CircuitBreaker(name="items", fallbackMethod = "alternativeMethod")
	@GetMapping("/detail2/{id}/size/{size}")
	public Item detail2(@PathVariable Long id, @PathVariable Integer size)
	{
		return itemService.findById(id, size);
	}
	
	
	@CircuitBreaker(name="items", fallbackMethod = "alternativeMethod2")
	@TimeLimiter(name="items")
	@GetMapping("/detail3/{id}/size/{size}")
	public CompletableFuture<Item> detail3(@PathVariable Long id, @PathVariable Integer size)
	{
		return CompletableFuture.supplyAsync(() -> itemService.findById(id, size));
	}
	
	public Item alternativeMethod(Long id, Integer size, Throwable e)
	{
		logger.info(e.getMessage());
		Item item = new Item();
		Product product = new Product();
		
		item.setSize(size);
		product.setId(id);
		product.setName("Sony");
		product.setPrice(300.00);
		item.setProduct(product);
		return item;
	}
	
	public CompletableFuture<Item> alternativeMethod2(Long id, Integer size, Throwable e)
	{
		logger.info(e.getMessage());
		Item item = new Item();
		Product product = new Product();
		
		item.setSize(size);
		product.setId(id);
		product.setName("Sony");
		product.setPrice(300.00);
		item.setProduct(product);
		return CompletableFuture.supplyAsync(() -> item);
	}
}
