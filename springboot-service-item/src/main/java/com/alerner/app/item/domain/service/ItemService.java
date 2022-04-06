package com.alerner.app.item.domain.service;

import java.util.List;

import com.alerner.app.item.domain.Item;
import com.alerner.app.commons.domain.entity.Product;

public interface ItemService 
{
	public List<Item>findAll();
	public Item findById(Long id, Integer size);
	
	
	public Product save(Product product);
	
	public Product update(Product product, Long id);
	
	public void delete(Long id);
	
}
