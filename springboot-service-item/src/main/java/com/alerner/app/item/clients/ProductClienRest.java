package com.alerner.app.item.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.alerner.app.item.domain.Product;

@FeignClient(name = "service-product")
public interface ProductClienRest {

	@GetMapping("/list")
	public List<Product>list();
	
	@GetMapping("/detail/{id}")
	public Product detail(@PathVariable Long id);
}
