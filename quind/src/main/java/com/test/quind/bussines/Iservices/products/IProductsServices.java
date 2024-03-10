package com.test.quind.bussines.Iservices.products;

import java.util.List;

import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.domain.commons.DTO.MainResponseDTO;
import com.test.quind.domain.commons.DTO.ProductDTO;
import com.test.quind.persistent.entity.client.ClientEntity;
import com.test.quind.persistent.entity.products.ProductEntity;

public interface IProductsServices {

	ProductDTO getProductById(Long id);


	List<ProductDTO> getAllProduct();

	MainResponseDTO createProduct(ProductDTO productDTO);

	MainResponseDTO updateProduct(ProductDTO productDTO);
	
	ProductEntity updateDataExistingProduct(ProductEntity existingProduct, ProductEntity productEntity);

	
	MainResponseDTO deleteProduct(Long id);

}
