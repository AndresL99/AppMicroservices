package com.alerner.app.item.domain;

import com.alerner.app.commons.domain.entity.Product;

public class Item 
{
	
	private Product product;
	private Integer size;

	
	public Item() {
		
	}
	
	public Item(Product product, Integer size) {
		this.product = product;
		this.size = size;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	
	public Double getTotal()
	{
		return product.getPrice() * size.doubleValue();
	}
	
}
