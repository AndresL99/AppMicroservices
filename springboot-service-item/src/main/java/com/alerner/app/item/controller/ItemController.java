package com.alerner.app.item.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.alerner.app.item.domain.Item;
import com.alerner.app.item.domain.service.ItemService;

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
	
	@GetMapping("/detail/{id}/size/{size}")
	public Item detail(@PathVariable Long id, @PathVariable Integer size)
	{
		return itemService.findById(id, size);
	}
}
