package com.test.quind.bussines.Iservices.transaction;


import com.test.quind.domain.commons.DTO.TransactionDTO;
import com.test.quind.persistent.entity.transsaction.TransactionEntity;

@FunctionalInterface
public interface IConvertEntityTransactionDTO {

	TransactionDTO convertToDTO(TransactionEntity transactionEntity);

	
}
