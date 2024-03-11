package com.test.quind.bussines.Iservices;

import java.util.List;

import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.domain.commons.DTO.MainResponseDTO;
import com.test.quind.domain.commons.DTO.ProductDTO;
import com.test.quind.domain.commons.DTO.TransactionDTO;
import com.test.quind.persistent.entity.client.ClientEntity;
import com.test.quind.persistent.entity.products.ProductEntity;
import com.test.quind.persistent.entity.transsaction.TransactionEntity;

public interface ITransactionServices {

	TransactionDTO getTransactionById(Long id);
	
	List<TransactionDTO> getAllTransaction();
	
	List<TransactionDTO> getTransactionByIdProduct(Long id);
	
	MainResponseDTO createTransaction(TransactionDTO transactionDTO);
	
	MainResponseDTO deleteTransaction(Long id);

}
