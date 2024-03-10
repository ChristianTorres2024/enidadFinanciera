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
        log.info("Parametro: {}", clientID);
        ClientDTO client = clientService.getClientById(clientID);         
        return ResponseEntity.status(HttpStatus.OK).body(client);
    }
    
    @GetMapping("/getClient/{typeID}/{numberID}")
    private ResponseEntity<ClientDTO> getClient(@PathVariable("typeID") Long paramtypeID, @PathVariable("numberID") Long paramIdNumber) {
        log.info("Parametro 1: {}", paramtypeID);
        log.info("Parametro 2: {}", paramIdNumber);        
        ClientDTO client = clientService.findClientByIdTypeAndIdNumber(paramtypeID,paramIdNumber);         
        return ResponseEntity.status(HttpStatus.OK).body(client);
    }
    
    @GetMapping("/getAllClients")
    private ResponseEntity<List<ClientDTO>> getAllClients() {
            
        List<ClientDTO> clients = clientService.getAllClients();     
        return ResponseEntity.status(HttpStatus.OK).body(clients);
    }    	
    
    @PostMapping("/createClient")
    public ResponseEntity<MainResponseDTO> createClient(@RequestBody ClientDTO clientDTO) {

    	log.info("Cliente creado: {}", clientDTO);
        MainResponseDTO response = new MainResponseDTO();
        response.setCode(0);
        response.setMessage("return OK");
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
