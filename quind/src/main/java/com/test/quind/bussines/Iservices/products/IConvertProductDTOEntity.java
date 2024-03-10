package com.test.quind.bussines.Iservices.products;


import com.test.quind.domain.commons.DTO.ProductDTO;
import com.test.quind.persistent.entity.products.ProductEntity;


@FunctionalInterface
public interface IConvertProductDTOEntity {
	ProductEntity convertToEntity(ProductDTO productDTO);
}
