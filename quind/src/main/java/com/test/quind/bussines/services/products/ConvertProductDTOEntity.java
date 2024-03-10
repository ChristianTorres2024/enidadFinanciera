package com.test.quind.bussines.services.products;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.test.quind.bussines.Iservices.client.IConvertClientDTOEntity;
import com.test.quind.bussines.Iservices.products.IConvertProductDTOEntity;
import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.domain.commons.DTO.ProductDTO;
import com.test.quind.infrastructure.controller.rest.ClientRestController;
import com.test.quind.persistent.entity.client.ClientEntity;
import com.test.quind.persistent.entity.client.IdentificationTypeEntity;
import com.test.quind.persistent.entity.products.AccountTypeEntity;
import com.test.quind.persistent.entity.products.ProductEntity;
import com.test.quind.persistent.entity.products.StatusEntity;

@Component
public class ConvertProductDTOEntity implements  IConvertProductDTOEntity{

	private Logger log = LoggerFactory.getLogger(ClientRestController.class);


	@Override
	public ProductEntity convertToEntity(ProductDTO productDTO) {
	    log.info("Convert DTO to entity :: {}", productDTO);
	    if (productDTO == null)
	        return null;

	    ProductEntity productEntity = new ProductEntity();
	    	    
	    AccountTypeEntity accountTypeEntity = new AccountTypeEntity();
	    accountTypeEntity.setIdType(Long.valueOf(productDTO.getAccountType()));
	    productEntity.setAccountTypeEntity(accountTypeEntity);
 	    	    
	    StatusEntity statusEntity = new StatusEntity();
	    statusEntity.setIdstatus(Long.valueOf(productDTO.getStatus()));
	    productEntity.setStatusEntity(statusEntity);

	    productEntity.setBalance(productDTO.getBalance()); 
	    productEntity.setGMFExempt(productDTO.getGmfExempt());

	    ClientEntity clientEntity = new ClientEntity();
	    clientEntity.setIdClient(productDTO.getClientDTO().getIdClient());
	    productEntity.setClientEntity(clientEntity);
	    
	    
	    return productEntity;
	}


}
