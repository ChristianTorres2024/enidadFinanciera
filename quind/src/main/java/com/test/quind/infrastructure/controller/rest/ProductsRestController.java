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
import com.test.quind.domain.commons.DTO.MainResponseDTO;
import com.test.quind.domain.commons.DTO.ProductDTO;


@RestController
@RequestMapping("/product")
public class ProductsRestController {
	
    @Autowired
	private ProductService productService;

    
    public ProductsRestController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping("/getProduct/{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") Long productID) {

    	ProductDTO product = productService.getProductById(productID);         
    	return responseProduct(product,HttpStatus.OK,HttpStatus.NOT_FOUND);
        
    }
    
    @GetMapping("/getProductClientId/{id}")
	public ResponseEntity<List<ProductDTO>> getProductClient(@PathVariable("id") Long clientID) {

    	List<ProductDTO> products = productService.getProductByIdClient(clientID);
         if(products != null && products.size() > 0) {
         	return ResponseEntity.status(HttpStatus.OK).body(products);
         }else {
         	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);        	
         }
        
    }
    
    
    @GetMapping("/getAllProducts")
	public ResponseEntity<List<ProductDTO>> getAllProducts() {
            
        List<ProductDTO> products = productService.getAllProduct();
        if(products != null && products.size() > 0) {
        	return ResponseEntity.status(HttpStatus.OK).body(products);
        }else {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);        	
        }
        
    }    	
    
    @PostMapping("/createProduct")
    public ResponseEntity<MainResponseDTO> createProduct(@RequestBody ProductDTO productDTO) {
        MainResponseDTO response = productService.createProduct(productDTO);
        return responseMain(response,HttpStatus.CREATED,HttpStatus.INTERNAL_SERVER_ERROR,HttpStatus.BAD_REQUEST);        
    }
    
    @PutMapping("/updateProduct")
    public ResponseEntity<MainResponseDTO> updateProduct(@RequestBody ProductDTO clientDTO) {

        MainResponseDTO response = productService.updateProduct(clientDTO);
        return responseMain(response,HttpStatus.OK,HttpStatus.INTERNAL_SERVER_ERROR,HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<MainResponseDTO> deleteProduct(@PathVariable("id") Long productID) {
    	             
        MainResponseDTO response = productService.deleteProduct(productID);  
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
    
	private ResponseEntity<ProductDTO> responseProduct(ProductDTO productsDTO,HttpStatus successStatus, HttpStatus errorStatus) {
    
		if(productsDTO != null) {
        	return ResponseEntity.status(successStatus).body(productsDTO);
        }else {
        	return ResponseEntity.status(errorStatus).body(null);        	
        }    
    }
	
}
    	