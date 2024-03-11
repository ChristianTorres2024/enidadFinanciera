package com.test.quind.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.test.quind.bussines.services.ClientService;
import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.domain.commons.DTO.MainResponseDTO;
import com.test.quind.infrastructure.controller.rest.ClientRestController;

class ClientRestControllerTest {

    @Test
    void testGetClientById() {
        ClientService clientServiceMock = mock(ClientService.class);
        ClientRestController clientController = new ClientRestController(clientServiceMock);

        Long clientId = 1L;
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setIdClient(clientId);
        when(clientServiceMock.getClientById(clientId)).thenReturn(clientDTO);

        ResponseEntity<ClientDTO> responseEntity = clientController.getClient(clientId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(clientDTO, responseEntity.getBody());
    }

    @Test
    void testGetClientByIdTypeAndIdNumber() {
        ClientService clientServiceMock = mock(ClientService.class);
        ClientRestController clientController = new ClientRestController(clientServiceMock);

        Long typeId = 1L;
        Long idNumber = 12345L;
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setIdClient(1L);
        when(clientServiceMock.findClientByIdTypeAndIdNumber(typeId, idNumber)).thenReturn(clientDTO);

        ResponseEntity<ClientDTO> responseEntity = clientController.getClient(typeId, idNumber);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(clientDTO, responseEntity.getBody());
    }

    @Test
    void testGetAllClients() {
        ClientService clientServiceMock = mock(ClientService.class);
        ClientRestController clientController = new ClientRestController(clientServiceMock);

        List<ClientDTO> clients = new ArrayList<>();
        when(clientServiceMock.getAllClients()).thenReturn(clients);

        ResponseEntity<List<ClientDTO>> responseEntity = clientController.getAllClients();
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testCreateClient() {
        ClientService clientServiceMock = mock(ClientService.class);
        ClientRestController clientController = new ClientRestController(clientServiceMock);

        ClientDTO clientDTO = new ClientDTO();
        MainResponseDTO responseDTO = new MainResponseDTO();
        responseDTO.setCode(0); // Suponiendo que el código 0 indica éxito en la operación

        when(clientServiceMock.createClient(clientDTO)).thenReturn(responseDTO);

        ResponseEntity<MainResponseDTO> responseEntity = clientController.createClient(clientDTO);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(responseDTO, responseEntity.getBody());
    }

    @Test
    void testUpdateClient() {
        ClientService clientServiceMock = mock(ClientService.class);
        ClientRestController clientController = new ClientRestController(clientServiceMock);

        ClientDTO clientDTO = new ClientDTO();
        MainResponseDTO responseDTO = new MainResponseDTO();
        responseDTO.setCode(0); // Suponiendo que el código 0 indica éxito en la operación

        when(clientServiceMock.updateClient(clientDTO)).thenReturn(responseDTO);

        ResponseEntity<MainResponseDTO> responseEntity = clientController.updateClient(clientDTO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDTO, responseEntity.getBody());
    }

    @Test
    void testDeleteClient() {
        ClientService clientServiceMock = mock(ClientService.class);
        ClientRestController clientController = new ClientRestController(clientServiceMock);

        Long clientId = 1L;
        MainResponseDTO responseDTO = new MainResponseDTO();
        responseDTO.setCode(0); // Suponiendo que el código 0 indica éxito en la operación

        when(clientServiceMock.deleteClient(clientId)).thenReturn(responseDTO);

        ResponseEntity<MainResponseDTO> responseEntity = clientController.deleteClient(clientId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDTO, responseEntity.getBody());
    }}
