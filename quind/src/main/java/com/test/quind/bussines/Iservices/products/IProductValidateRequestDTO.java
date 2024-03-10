package com.test.quind.bussines.Iservices.products;

import com.test.quind.domain.commons.DTO.ProductDTO;
import com.test.quind.domain.commons.DTO.ValidateDTO;

@FunctionalInterface
public interface IProductValidateRequestDTO {
	ValidateDTO validateDataProduct(ProductDTO productDTO); 

}
