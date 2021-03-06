 package com.alerner.app.item.controller;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alerner.app.item.domain.Item;
import com.alerner.app.commons.domain.entity.Product;
import com.alerner.app.item.domain.service.ItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.vavr.collection.Map;

@RefreshScope
@RestController
public class ItemController 
{
	private final Logger logger = LoggerFactory.getLogger(ItemController.class);

	@Autowired
	private Environment env;
	
	@Autowired
	private CircuitBreakerFactory cbFactory;
	
	@Autowired
	@Qualifier("serviceFeign")
	private ItemService itemService;
	
	
	@Value("${config.text}")
	private String text;
	
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
	
	@GetMapping("/giveConfig")
	public ResponseEntity<?>giveConfiguration(@Value("${server.port}") String port)
	{
		Map<String, String>json = (Map<String, String>) new HashMap();
		json.put("text",text);
		json.put("port",port);
		
		if(env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev"))
		{
			json.put("author.name", env.getProperty("config.author.name"));
			json.put("author.email", env.getProperty("config.author.email"));
		}
		
		return new ResponseEntity<Map<String, String>>(json,HttpStatus.OK);
	}
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Product create(@RequestBody Product product)
	{
		return itemService.save(product);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Product update(@RequestBody Product product, @PathVariable Long id)
	{
		return itemService.update(product, id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable Long id)
	{
		itemService.delete(id);
	}
	
}
