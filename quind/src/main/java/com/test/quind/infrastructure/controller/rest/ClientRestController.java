package com.test.quind.infrastructure.controller.rest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.quind.bussines.Iservices.IClientServices;
import com.test.quind.bussines.services.ClientService;
import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.domain.commons.DTO.MainResponseDTO;
import com.test.quind.persistent.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/client")
public class ClientRestController {
	
    @Autowired
	private ClientService clientService;


    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }
    
    
   


	@GetMapping("/getClient/{id}")
	public ResponseEntity<ClientDTO> getClient(@PathVariable("id") Long clientID) {

    	ClientDTO client = clientService.getClientById(clientID);         
    	return responseClient(client,HttpStatus.OK,HttpStatus.NOT_FOUND);
        
    }
    
    @GetMapping("/getClient/{typeID}/{numberID}")
	public ResponseEntity<ClientDTO> getClient(@PathVariable("typeID") Long paramtypeID, @PathVariable("numberID") Long paramIdNumber) {
             
        ClientDTO client = clientService.findClientByIdTypeAndIdNumber(paramtypeID,paramIdNumber);         
        return responseClient(client,HttpStatus.OK,HttpStatus.NOT_FOUND);

    }
    
    @GetMapping("/getAllClients")
	public ResponseEntity<List<ClientDTO>> getAllClients() {
            
        List<ClientDTO> clients = clientService.getAllClients();
        if(clients != null && clients.size() > 0) {
        	return ResponseEntity.status(HttpStatus.OK).body(clients);
        }else {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);        	
        }
        
    }    	
    
    @PostMapping("/createClient")
    public ResponseEntity<MainResponseDTO> createClient(@RequestBody ClientDTO clientDTO) {
        MainResponseDTO response = clientService.createClient(clientDTO);
        return responseMain(response,HttpStatus.CREATED,HttpStatus.INTERNAL_SERVER_ERROR,HttpStatus.BAD_REQUEST);        
    }
    
    @PutMapping("/updateClient")
    public ResponseEntity<MainResponseDTO> updateClient(@RequestBody ClientDTO clientDTO) {

        MainResponseDTO response = clientService.updateClient(clientDTO);
        return responseMain(response,HttpStatus.OK,HttpStatus.INTERNAL_SERVER_ERROR,HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/deleteClient/{id}")
	public ResponseEntity<MainResponseDTO> deleteClient(@PathVariable("id") Long clientID) {
    	             
        MainResponseDTO response = clientService.deleteClient(clientID);  
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
    
	private ResponseEntity<ClientDTO> responseClient(ClientDTO clientDTO,HttpStatus successStatus, HttpStatus errorStatus) {
    
		if(clientDTO != null) {
        	return ResponseEntity.status(successStatus).body(clientDTO);
        }else {
        	return ResponseEntity.status(errorStatus).body(null);        	
        }    
    }
    	

    
}
