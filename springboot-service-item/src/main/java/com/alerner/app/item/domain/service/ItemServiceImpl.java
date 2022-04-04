package com.alerner.app.item.domain.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alerner.app.item.domain.Item;
import com.alerner.app.item.domain.Product;

import feign.Request.HttpMethod;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService 
{
	@Autowired
	private RestTemplate clientRest;

	@Override
	public List<Item> findAll() {
		List<Product> products = Arrays.asList(clientRest.getForObject("http://service-product/list", Product[].class));
		return products.stream().map(p -> new Item(p, 1))
				.collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer size) {
		Map<String, String>pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		Product product = clientRest.getForObject("http://service-product/list/{id}", Product.class, pathVariables);
		return new Item(product, size);
	}

	@Override
	public Product save(Product product) 
	{
		
		HttpEntity<Product> body = new HttpEntity<Product>(product);
		ResponseEntity<Product>response = clientRest.exchange("http://service-product/", HttpMethod.POST, body, Product.class);
		Product productResponse = response.getBody();
		
		return productResponse;
	}

	@Override
	public Product update(Product product, Long id) 
	{
		Map<String, String>pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		HttpEntity<Product>body = new HttpEntity<Product>(product);
		
		ResponseEntity<Product>response = clientRest.exchange("http://service-product/{id}", HttpMethod.PUT, body, Product.class,pathVariables);
		return response.getBody();
	}

	@Override
	public void delete(Long id) 
	{
		Map<String, String>pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		clientRest.delete("http://service-product/{id}",pathVariables);
	}

}
