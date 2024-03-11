package com.test.quind.bussines.Iservices.transaction.component;

import com.test.quind.domain.commons.DTO.TransactionDTO;
import com.test.quind.domain.commons.DTO.ValidateDTO;

@FunctionalInterface
public interface ITransactionValidateRequestDTO {
	ValidateDTO validateDataTransaction(TransactionDTO TransactionDTO); 
}
