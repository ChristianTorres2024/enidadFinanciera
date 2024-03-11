package com.test.quind.bussines.services.transaction.component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.test.quind.bussines.Iservices.products.component.IConvertEntityProductDTO;
import com.test.quind.bussines.Iservices.transaction.component.IConvertEntityTransactionDTO;
import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.domain.commons.DTO.ProductDTO;
import com.test.quind.domain.commons.DTO.TransactionDTO;
import com.test.quind.infrastructure.controller.rest.ClientRestController;
import com.test.quind.persistent.entity.products.ProductEntity;
import com.test.quind.persistent.entity.transsaction.TransactionEntity;

@Component
public class ConvertEntityTransactionDTO implements IConvertEntityTransactionDTO {

	private Logger log = LoggerFactory.getLogger(ClientRestController.class);

	@Override
	public TransactionDTO convertToDTO(TransactionEntity transactionEntity) {
		log.info("Convert entity to object :: {}", transactionEntity);
		if (transactionEntity == null)
			return null;

		TransactionDTO transactionDTO = new TransactionDTO();
		transactionDTO.setIdTransaction(transactionEntity.getIdTransaction());
		transactionDTO.setTransactionType(transactionEntity.getTransactionTypeEntity().getTypeName());
		transactionDTO.setAmount(transactionEntity.getAmount());
		transactionDTO.setDescription(transactionEntity.getDescription());
		transactionDTO.setSuccessful(transactionEntity.getSuccessful());

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date TransactionDate = dateFormat.parse(transactionEntity.getTransactionDate());
			transactionDTO.setTransactionDate(dateFormat.format(TransactionDate));
		} catch (ParseException e) {
			log.error("Error Format TransactionDate {}", e.getCause(), e.getMessage());
		}

		if (transactionEntity.getSourceAccount() != null
				&& transactionEntity.getSourceAccount().getAccountNumber() != null) {
			
			ProductDTO SourceAccountproductDTO = new ProductDTO();
			
			SourceAccountproductDTO.setIdProduct(transactionEntity.getSourceAccount().getIdProduct());
			SourceAccountproductDTO.setAccountNummber(transactionEntity.getSourceAccount().getAccountNumber());
			SourceAccountproductDTO.setAccountType(transactionEntity.getSourceAccount().getAccountTypeEntity().getTypeName());
			
			transactionDTO.setSourceAccount(SourceAccountproductDTO);
					
		}
		if (transactionEntity.getDestinationAccount() != null
				&& transactionEntity.getDestinationAccount().getAccountNumber() != null) {
		
			ProductDTO DestinationAccountproductDTO = new ProductDTO();
			
			DestinationAccountproductDTO.setIdProduct(transactionEntity.getDestinationAccount().getIdProduct());
			DestinationAccountproductDTO.setAccountNummber(transactionEntity.getDestinationAccount().getAccountNumber());
			DestinationAccountproductDTO.setAccountType(transactionEntity.getDestinationAccount().getAccountTypeEntity().getTypeName());
		
			transactionDTO.setDestinationAccount(DestinationAccountproductDTO);
		
		}
		return transactionDTO;
	}

}
