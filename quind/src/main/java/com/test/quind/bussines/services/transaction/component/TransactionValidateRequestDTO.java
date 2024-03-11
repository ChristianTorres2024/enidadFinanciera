package com.test.quind.bussines.services.transaction.component;

import org.springframework.stereotype.Component;

import com.test.quind.bussines.Iservices.transaction.component.ITransactionValidateRequestDTO;
import com.test.quind.domain.commons.DTO.TransactionDTO;
import com.test.quind.domain.commons.DTO.ValidateDTO;
import com.test.quind.domain.commons.Enums.EnumValidatePoductDTO;
import com.test.quind.domain.commons.Enums.EnumValidateTransactionDTO;

@Component
public class TransactionValidateRequestDTO implements ITransactionValidateRequestDTO {

	@Override
	public ValidateDTO validateDataTransaction(TransactionDTO transactionDTO) {

		if (transactionDTO.getTransactionType() == null)
			return new ValidateDTO(EnumValidateTransactionDTO.NULL_TRANSACTION_TYPE.getCode(),
					EnumValidateTransactionDTO.NULL_TRANSACTION_TYPE.getField(),
					EnumValidateTransactionDTO.NULL_TRANSACTION_TYPE.getDescription());

		if (transactionDTO.getAmount() == null)
			return new ValidateDTO(EnumValidateTransactionDTO.NULL_AMOUNT.getCode(),
					EnumValidateTransactionDTO.NULL_AMOUNT.getField(),
					EnumValidateTransactionDTO.NULL_AMOUNT.getDescription());

		if (transactionDTO.getSuccessful() == null)
			return new ValidateDTO(EnumValidateTransactionDTO.NULL_SUCCESSFUL.getCode(),
					EnumValidateTransactionDTO.NULL_SUCCESSFUL.getField(),
					EnumValidateTransactionDTO.NULL_SUCCESSFUL.getDescription());

		// Valida si es consignacion o transferencia
		if (transactionDTO.getTransactionType().equalsIgnoreCase("1")
				|| transactionDTO.getTransactionType().equalsIgnoreCase("3")) {
			
			if (transactionDTO.getSourceAccount() == null || transactionDTO.getSourceAccount().getIdProduct() == null)
				return new ValidateDTO(EnumValidateTransactionDTO.NULL_SOURCE_ACOUNT.getCode(),
						EnumValidateTransactionDTO.NULL_SOURCE_ACOUNT.getField(),
						EnumValidateTransactionDTO.NULL_SOURCE_ACOUNT.getDescription());

			if (transactionDTO.getTransactionType().equalsIgnoreCase("3")
					&& transactionDTO.getDestinationAccount() == null || transactionDTO.getDestinationAccount().getIdProduct() == null)
				return new ValidateDTO(EnumValidateTransactionDTO.NULL_DESTINATION_ACCOUNT.getCode(),
						EnumValidateTransactionDTO.NULL_DESTINATION_ACCOUNT.getField(),
						EnumValidateTransactionDTO.NULL_DESTINATION_ACCOUNT.getDescription());

		}

		//Valida si es retiro 
		if (transactionDTO.getTransactionType().equalsIgnoreCase("2")) {
			
			if (transactionDTO.getSourceAccount() == null || transactionDTO.getSourceAccount().getIdProduct() == null)
				return new ValidateDTO(EnumValidateTransactionDTO.NULL_SOURCE_ACOUNT.getCode(),
						EnumValidateTransactionDTO.NULL_SOURCE_ACOUNT.getField(),
						EnumValidateTransactionDTO.NULL_SOURCE_ACOUNT.getDescription());

		}		
		return null;
	}

}
