package com.alerner.app.products.domain.repository;

import org.springframework.data.repository.CrudRepository;

import com.alerner.app.products.domain.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

}
