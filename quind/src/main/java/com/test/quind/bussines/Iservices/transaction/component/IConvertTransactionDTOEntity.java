package com.test.quind.bussines.Iservices.transaction.component;

import com.test.quind.domain.commons.DTO.TransactionDTO;
import com.test.quind.persistent.entity.transsaction.TransactionEntity;


@FunctionalInterface
public interface IConvertTransactionDTOEntity {
	TransactionEntity convertToEntity(TransactionDTO transactionDTO);
	
}
