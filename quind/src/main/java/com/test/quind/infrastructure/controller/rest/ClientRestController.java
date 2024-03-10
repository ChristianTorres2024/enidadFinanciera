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

import com.test.quind.bussines.Iservices.client.IClientServices;
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
    private Logger log = LoggerFactory.getLogger(ClientRestController.class); 
	
    @Autowired
	private ClientService clientService;

    
    @GetMapping("/getClient/{id}")
    private ResponseEntity<ClientDTO> getClient(@PathVariable("id") Long clientID) {

    	ClientDTO client = clientService.getClientById(clientID);         

        if(client != null) {
        	return ResponseEntity.status(HttpStatus.OK).body(client);
        }else {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);        	
        }
    }
    
    @GetMapping("/getClient/{typeID}/{numberID}")
    private ResponseEntity<ClientDTO> getClient(@PathVariable("typeID") Long paramtypeID, @PathVariable("numberID") Long paramIdNumber) {
             
        ClientDTO client = clientService.findClientByIdTypeAndIdNumber(paramtypeID,paramIdNumber);         
        if(client != null) {
        	return ResponseEntity.status(HttpStatus.OK).body(client);
        }else {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);        	
        }

    }
    
    @GetMapping("/getAllClients")
    private ResponseEntity<List<ClientDTO>> getAllClients() {
            
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
        
        if (response.getCode() == 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else if (response.getCode() == 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @PutMapping("/updateClient")
    public ResponseEntity<MainResponseDTO> updateClient(@RequestBody ClientDTO clientDTO) {

    	log.info("Cliente creado: {}", clientDTO);
        MainResponseDTO response = new MainResponseDTO(0,"return OK");       
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @DeleteMapping("/deleteClient/{id}")
    private ResponseEntity<MainResponseDTO> deleteClient(@PathVariable("id") Long clientID) {
        log.info("Parametro::", clientID);
        
        clientService.deleteClient(clientID);      
        MainResponseDTO response = new MainResponseDTO(0,"return OK");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
