package com.alerner.app.products.domain.service;
import java.util.List;

import com.alerner.app.products.domain.entity.Product;

public interface IProductService 
{
	public List<Product> findAll();
	public Product findById(Long id);
	
	public Product save(Product product);
	public void deleteById(Long id);
}
