package com.test.quind.bussines.services.transaction.component;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.test.quind.bussines.Iservices.client.component.IConvertClientDTOEntity;
import com.test.quind.bussines.Iservices.products.component.IConvertProductDTOEntity;
import com.test.quind.bussines.Iservices.transaction.component.IConvertTransactionDTOEntity;
import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.domain.commons.DTO.ProductDTO;
import com.test.quind.domain.commons.DTO.TransactionDTO;
import com.test.quind.domain.commons.DTO.ValidateDTO;
import com.test.quind.domain.commons.Enums.EnumValidateTransactionDTO;
import com.test.quind.infrastructure.controller.rest.ClientRestController;
import com.test.quind.persistent.entity.client.ClientEntity;
import com.test.quind.persistent.entity.client.IdentificationTypeEntity;
import com.test.quind.persistent.entity.products.AccountTypeEntity;
import com.test.quind.persistent.entity.products.ProductEntity;
import com.test.quind.persistent.entity.products.StatusEntity;
import com.test.quind.persistent.entity.transsaction.TransactionEntity;
import com.test.quind.persistent.entity.transsaction.TransactionTypeEntity;

@Component
public class ConvertTransactionDTOEntity implements  IConvertTransactionDTOEntity{

	private Logger log = LoggerFactory.getLogger(ClientRestController.class);

	@Override
	public TransactionEntity convertToEntity(TransactionDTO transactionDTO) {
	    log.info("Convert DTO to entity :: {}", transactionDTO);
	    if (transactionDTO == null)
	        return null;

	    TransactionEntity transactionEntity = new TransactionEntity();
	    transactionEntity.setAmount(transactionDTO.getAmount());
	    transactionEntity.setDescription(transactionDTO.getDescription());
	    transactionEntity.setSuccessful(transactionDTO.getSuccessful());
	    
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = LocalDateTime.now().format(formatter);
        transactionEntity.setTransactionDate(formattedDate);
	    
        TransactionTypeEntity transactionTypeEntity = new TransactionTypeEntity();
        transactionTypeEntity.setIdtransaction(Long.valueOf(transactionDTO.getTransactionType()));
	    transactionEntity.setTransactionTypeEntity(transactionTypeEntity);
	    
	    // Valida si es consignacion o transferencia
	    if (transactionDTO.getTransactionType().equalsIgnoreCase("1")
				|| transactionDTO.getTransactionType().equalsIgnoreCase("3")) {
			
	    	ProductEntity sourceAccountEntity = new ProductEntity();
	        sourceAccountEntity.setIdProduct(transactionDTO.getSourceAccount().getIdProduct());	 
	        transactionEntity.setSourceAccount(sourceAccountEntity);
	    	
			if (transactionDTO.getTransactionType().equalsIgnoreCase("3")){
				ProductEntity destinationAccountEntity = new ProductEntity();
		        destinationAccountEntity.setIdProduct(transactionDTO.getDestinationAccount().getIdProduct());	
		        transactionEntity.setDestinationAccount(destinationAccountEntity);
				
			}

		}

		//Valida si es retiro 
		if (transactionDTO.getTransactionType().equalsIgnoreCase("2")) {
			
			ProductEntity sourceAccountEntity = new ProductEntity();
	        sourceAccountEntity.setIdProduct(transactionDTO.getSourceAccount().getIdProduct());	 
	        transactionEntity.setSourceAccount(sourceAccountEntity);
	        
		}		
	    

	    return transactionEntity;
	}



}
