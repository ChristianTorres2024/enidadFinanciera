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

import com.test.quind.bussines.services.products.ProductService;
import com.test.quind.bussines.services.transaction.TransactionService;
import com.test.quind.domain.commons.DTO.MainResponseDTO;
import com.test.quind.domain.commons.DTO.ProductDTO;
import com.test.quind.domain.commons.DTO.TransactionDTO;


@RestController
@RequestMapping("/transaction")
public class TransactionRestController {
	
    @Autowired
	private TransactionService transactionService;

    
    @GetMapping("/getTransaction/{id}")
    private ResponseEntity<TransactionDTO> getTransaction(@PathVariable("id") Long TransactionID) {

    	TransactionDTO transaction = transactionService.getTransactionById(TransactionID);         
    	return responseProduct(transaction,HttpStatus.OK,HttpStatus.NOT_FOUND);
        
    }
    
    
    @GetMapping("/getTransactionProductId/{id}")
    private ResponseEntity<List<TransactionDTO>> getTransactionProductId(@PathVariable("id") Long productID) {

    	List<TransactionDTO> products = transactionService.getTransactionByIdProduct(productID);
         if(products != null && products.size() > 0) {
         	return ResponseEntity.status(HttpStatus.OK).body(products);
         }else {
         	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);        	
         }
        
    }
    
    
    @GetMapping("/getAllTransaction")
    private ResponseEntity<List<TransactionDTO>> getAllTransaction() {
            
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
    
    @PutMapping("/updateTransaction")
    public ResponseEntity<MainResponseDTO> updateTransaction(@RequestBody TransactionDTO transactionDTO) {

        MainResponseDTO response = new MainResponseDTO(2,"no se permite Actualizar una Transaccion");
        return responseMain(response,HttpStatus.OK,HttpStatus.INTERNAL_SERVER_ERROR,HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/deleteTransaction/{id}")
    private ResponseEntity<MainResponseDTO> deleteTransaction(@PathVariable("id") Long transactionID) {
    	             
        MainResponseDTO response = transactionService.deleteTransaction(transactionID);  
        return responseMain(response,HttpStatus.OK,HttpStatus.INTERNAL_SERVER_ERROR,HttpStatus.BAD_REQUEST);
              
    }
    
    private ResponseEntity<MainResponseDTO> responseMain(MainResponseDTO mainResponseDTO,HttpStatus successStatus, HttpStatus errorStatus, HttpStatus errorBadrequest) {
        
    	if (mainResponseDTO.getCode() == 0) {
            return ResponseEntity.status(successStatus).body(mainResponseDTO);
        } else if (mainResponseDTO.getCode() == 1) {
            return ResponseEntity.status(errorBadrequest).body(mainResponseDTO);
        } else {
            return ResponseEntity.status(errorStatus).body(mainResponseDTO);
        }
    	
    }
    
	private ResponseEntity<TransactionDTO> responseProduct(TransactionDTO transactionDTO,HttpStatus successStatus, HttpStatus errorStatus) {
    
		if(transactionDTO != null) {
        	return ResponseEntity.status(successStatus).body(transactionDTO);
        }else {
        	return ResponseEntity.status(errorStatus).body(null);        	
        }    
    }
	
}
    	