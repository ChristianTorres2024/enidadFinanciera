package com.test.quind.bussines.Iservices.products;

import java.text.ParseException;

import com.test.quind.domain.commons.DTO.ProductDTO;
import com.test.quind.persistent.entity.products.ProductEntity;

@FunctionalInterface
public interface IConvertEntityProductDTO {

    ProductDTO convertToDTO(ProductEntity productEntity);

	
}
