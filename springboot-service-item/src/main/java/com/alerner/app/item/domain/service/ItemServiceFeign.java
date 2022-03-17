package com.alerner.app.item.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.alerner.app.item.clients.ProductClienRest;
import com.alerner.app.item.domain.Item;

@Service("serviceFeign")
@Primary
public class ItemServiceFeign implements ItemService {

	@Autowired
	private ProductClienRest productClienRest;
	
	@Override
	public List<Item> findAll() {
		
		return productClienRest.list()
				.stream()
				.map(p -> new Item(p,1))
				.collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer size) {
	
		return new Item(productClienRest.detail(id),size);
	}

}