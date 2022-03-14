package com.alerner.app.products.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alerner.app.products.domain.entity.Product;
import com.alerner.app.products.domain.repository.ProductRepository;

@Service
public class ProductServiceImpl implements IProductService{

	@Autowired
	private ProductRepository productRepository;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Product> findAll() {
		
		return (List<Product>)productRepository.findAll();
	}

	@Override
	public Product findById(Long id) {
	
		return productRepository.findById(id).orElse(null);
	}

}
