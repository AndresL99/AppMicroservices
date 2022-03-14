package com.alerner.app.item.domain.service;

import java.util.List;

import com.alerner.app.item.domain.Item;

public interface ItemService 
{
	public List<Item>findAll();
	public Item findById(Long id, Integer size);
	
}
