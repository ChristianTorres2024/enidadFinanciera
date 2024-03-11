package com.test.quind.bussines.services.transaction;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.quind.bussines.Iservices.transaction.ITransactionServices;
import com.test.quind.domain.commons.DTO.MainResponseDTO;
import com.test.quind.domain.commons.DTO.ProductDTO;
import com.test.quind.domain.commons.DTO.TransactionDTO;
import com.test.quind.domain.commons.DTO.ValidateDTO;
import com.test.quind.domain.commons.Enums.EnumMainResponse;
import com.test.quind.infrastructure.controller.rest.ClientRestController;
import com.test.quind.persistent.entity.transsaction.TransactionEntity;
import com.test.quind.persistent.repository.ProductsRepository;
import com.test.quind.persistent.repository.TransactionRepository;

@Service
public class TransactionService implements ITransactionServices {

	private Logger log = LoggerFactory.getLogger(ClientRestController.class);

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private ConvertTransactionDTOEntity convertTransactionDTOEntity;

	@Autowired
	private ConvertEntityTransactionDTO convertEntityTransactionDTO;

	@Autowired
	private TransactionValidateRequestDTO transactionValidateRequestDTO;

	@Override
	public TransactionDTO getTransactionById(Long id) {
		try {
			TransactionEntity transaction = transactionRepository.findById(id).orElse(null);
			return convertEntityTransactionDTO.convertToDTO(transaction);
		} catch (Exception e) {
			log.error("Error getProductById {}", e.getCause(), e.getMessage());
			return null;
		}
	}
  
	@Override
	public List<TransactionDTO> getAllTransaction() {
		try {
			List<TransactionEntity> transactionEntity = transactionRepository.findAll();

			List<TransactionDTO> transactionDTOs = transactionEntity.stream().map(convertEntityTransactionDTO::convertToDTO)
					.collect(Collectors.toList());

			return transactionDTOs;
		} catch (Exception e) {
			log.error("Error getAllProduct {}", e.getCause(), e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<TransactionDTO> getTransactionByIdProduct(Long idProduct) {
		
		try {		
			List<TransactionEntity> transactionEntity = transactionRepository.findBySourceAccountOrDestinationAccount(idProduct);;

			List<TransactionDTO> transactionDTOs = transactionEntity.stream().map(convertEntityTransactionDTO::convertToDTO)
					.collect(Collectors.toList());

			return transactionDTOs;
		} catch (Exception e) {
			log.error("Error getTransactionByIdProduct {}", e.getCause(), e.getMessage());
			return null;
		}
	}

	@Override
	public MainResponseDTO createTransaction(TransactionDTO transactionDTO) {
		
		ValidateDTO validateDTO = transactionValidateRequestDTO.validateDataTransaction(transactionDTO);
		if (validateDTO != null) {
			return new MainResponseDTO(validateDTO.getCode(), validateDTO.getField() + validateDTO.getError());
		}

		TransactionEntity transactionEntity = convertTransactionDTOEntity.convertToEntity(transactionDTO);

		try {
			transactionRepository.save(transactionEntity);
			log.info("createTransaction Ok {}", transactionEntity);
			return new MainResponseDTO(EnumMainResponse.OK.getCode(), EnumMainResponse.OK.getMessage());
		} catch (Exception e) {
			log.error("Error createTransaction {}", e.getCause(), e.getMessage());
			return new MainResponseDTO(EnumMainResponse.ERROR.getCode(), EnumMainResponse.ERROR.getMessage()+ " -> "+ e.getMessage());
		}
	}
	

	@Override
	public MainResponseDTO deleteTransaction(Long id) {
		try {
			Optional<TransactionEntity> existingTransactionOptional = transactionRepository.findById(id);
			if (existingTransactionOptional.isPresent()) {
				transactionRepository.deleteById(id);
				return new MainResponseDTO(EnumMainResponse.OK.getCode(), EnumMainResponse.OK.getMessage());
			} else {
				return new MainResponseDTO(EnumMainResponse.ERROR.getCode(), EnumMainResponse.ERROR.getMessage());
			}
		} catch (Exception e) {
			log.error("Error deleteTransaction: {}", e.getMessage());
			return new MainResponseDTO(EnumMainResponse.ERROR.getCode(), EnumMainResponse.ERROR.getMessage());
		}
	}

}
