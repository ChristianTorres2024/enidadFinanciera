package com.test.quind.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.quind.persistent.entity.products.ProductEntity;

@Repository
public interface ProductsRepository extends JpaRepository<ProductEntity, Long> {
	
}
