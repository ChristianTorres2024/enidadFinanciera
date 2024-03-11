package com.test.quind.bussines.services.products.component;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.test.quind.bussines.Iservices.products.component.IConvertEntityProductDTO;
import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.domain.commons.DTO.ProductDTO;
import com.test.quind.infrastructure.controller.rest.ClientRestController;
import com.test.quind.persistent.entity.products.ProductEntity;

@Component
public class ConvertEntityProductDTO implements IConvertEntityProductDTO{

	private Logger log = LoggerFactory.getLogger(ClientRestController.class);

	@Override
	public ProductDTO convertToDTO(ProductEntity productEntity) {
		log.info("Convert entity to object :: {}", productEntity);
		if (productEntity == null)
			return null;

		ProductDTO productDTO = new ProductDTO();
		productDTO.setIdProduct(productEntity.getIdProduct());
	    productDTO.setAccountType(productEntity.getAccountTypeEntity().getTypeName());
	    productDTO.setAccountNummber(productEntity.getAccountNumber());
	    productDTO.setStatus(productEntity.getStatusEntity().getStatusName());
	    productDTO.setBalance(productEntity.getBalance());
	    productDTO.setGmfExempt(productEntity.getGMFExempt());
	    
	    ClientDTO clientDTO = new ClientDTO();
	    clientDTO.setIdClient(productEntity.getClientEntity().getIdClient());
	    clientDTO.setFirstName(productEntity.getClientEntity().getFirstName());
	    clientDTO.setLastName(productEntity.getClientEntity().getLastName());	    
	    productDTO.setClientDTO(clientDTO); 
		
		return productDTO;
	}

}
