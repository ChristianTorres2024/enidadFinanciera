package com.test.quind.infrastructure.controller.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.quind.bussines.services.ProductService;
import com.test.quind.bussines.services.TransactionService;
import com.test.quind.domain.commons.DTO.MainResponseDTO;
import com.test.quind.domain.commons.DTO.ProductDTO;
import com.test.quind.domain.commons.DTO.TransactionDTO;


@RestController
@RequestMapping("/transaction")
public class TransactionRestController {
	
    @Autowired
	private TransactionService transactionService;

    public TransactionRestController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    @GetMapping("/getTransaction/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable("id") Long TransactionID) {

    	TransactionDTO transaction = transactionService.getTransactionById(TransactionID);         
    	return responseProduct(transaction,HttpStatus.OK,HttpStatus.NOT_FOUND);
        
    }
    
    
    @GetMapping("/getTransactionProductId/{id}")
    public ResponseEntity<List<TransactionDTO>> getTransactionProductId(@PathVariable("id") Long productID) {

    	List<TransactionDTO> products = transactionService.getTransactionByIdProduct(productID);
         if(products != null && products.size() > 0) {
         	return ResponseEntity.status(HttpStatus.OK).body(products);
         }else {
         	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);        	
         }
        
    }
    
    
    @GetMapping("/getAllTransaction")
    public ResponseEntity<List<TransactionDTO>> getAllTransaction() {
            
        List<TransactionDTO> transaction = transactionService.getAllTransaction();
        if(transaction != null && transaction.size() > 0) {
        	return ResponseEntity.status(HttpStatus.OK).body(transaction);
        }else {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);        	
        }
        
    }    	
    
    @PostMapping("/createTransaction")
    public ResponseEntity<MainResponseDTO> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        MainResponseDTO response = transactionService.createTransaction(transactionDTO);
        return responseMain(response,HttpStatus.CREATED,HttpStatus.INTERNAL_SERVER_ERROR,HttpStatus.BAD_REQUEST);        
    }
    
    public ResponseEntity<MainResponseDTO> updateTransaction(@RequestBody TransactionDTO transactionDTO) {
        MainResponseDTO response = new MainResponseDTO();
        response.setCode(2); 
        response.setMessage("No se permite actualizar una transacción");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    @DeleteMapping("/deleteTransaction/{id}")
    public ResponseEntity<MainResponseDTO> deleteTransaction(@PathVariable("id") Long transactionID) {
    	             
        MainResponseDTO response = transactionService.deleteTransaction(transactionID);  
        return responseMain(response,HttpStatus.OK,HttpStatus.INTERNAL_SERVER_ERROR,HttpStatus.BAD_REQUEST);
              
    }
    
    public ResponseEntity<MainResponseDTO> responseMain(MainResponseDTO mainResponseDTO,HttpStatus successStatus, HttpStatus errorStatus, HttpStatus errorBadrequest) {
        
    	if (mainResponseDTO.getCode() == 0) {
            return ResponseEntity.status(successStatus).body(mainResponseDTO);
        } else if (mainResponseDTO.getCode() == 1) {
            return ResponseEntity.status(errorBadrequest).body(mainResponseDTO);
        } else {
            return ResponseEntity.status(errorStatus).body(mainResponseDTO);
        }
    	
    }
    
    public ResponseEntity<TransactionDTO> responseProduct(TransactionDTO transactionDTO,HttpStatus successStatus, HttpStatus errorStatus) {
    
		if(transactionDTO != null) {
        	return ResponseEntity.status(successStatus).body(transactionDTO);
        }else {
        	return ResponseEntity.status(errorStatus).body(null);        	
        }    
    }
	
}
    	