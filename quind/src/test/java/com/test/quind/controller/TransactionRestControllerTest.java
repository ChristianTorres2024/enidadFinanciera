package com.test.quind.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.test.quind.bussines.services.TransactionService;
import com.test.quind.domain.commons.DTO.MainResponseDTO;
import com.test.quind.domain.commons.DTO.TransactionDTO;
import com.test.quind.infrastructure.controller.rest.TransactionRestController;

class TransactionRestControllerTest {

    @Test
    void testGetTransaction() {
        TransactionService transactionServiceMock = mock(TransactionService.class);
        TransactionRestController transactionController = new TransactionRestController(transactionServiceMock);

        Long transactionId = 1L;
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setIdTransaction(transactionId);
        when(transactionServiceMock.getTransactionById(transactionId)).thenReturn(transactionDTO);

        ResponseEntity<TransactionDTO> responseEntity = transactionController.getTransaction(transactionId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(transactionDTO, responseEntity.getBody());
    }

    @Test
    void testGetTransactionProductId() {
        TransactionService transactionServiceMock = mock(TransactionService.class);
        TransactionRestController transactionController = new TransactionRestController(transactionServiceMock);

        Long productId = 1L;
        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setIdTransaction(1L);
        transactionDTOList.add(transactionDTO);
        when(transactionServiceMock.getTransactionByIdProduct(productId)).thenReturn(transactionDTOList);

        ResponseEntity<List<TransactionDTO>> responseEntity = transactionController.getTransactionProductId(productId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(transactionDTOList, responseEntity.getBody());
    }

    @Test
    void testGetAllTransaction() {
        TransactionService transactionServiceMock = mock(TransactionService.class);
        TransactionRestController transactionController = new TransactionRestController(transactionServiceMock);

        List<TransactionDTO> transactions = new ArrayList<>();
        when(transactionServiceMock.getAllTransaction()).thenReturn(transactions);

        ResponseEntity<List<TransactionDTO>> responseEntity = transactionController.getAllTransaction();
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testCreateTransaction() {
        TransactionService transactionServiceMock = mock(TransactionService.class);
        TransactionRestController transactionController = new TransactionRestController(transactionServiceMock);

        TransactionDTO transactionDTO = new TransactionDTO();
        MainResponseDTO responseDTO = new MainResponseDTO();
        responseDTO.setCode(0); // Suponiendo que el código 0 indica éxito en la operación

        when(transactionServiceMock.createTransaction(transactionDTO)).thenReturn(responseDTO);

        ResponseEntity<MainResponseDTO> responseEntity = transactionController.createTransaction(transactionDTO);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(responseDTO, responseEntity.getBody());
    }

    @Test
    void testUpdateTransaction() {
        TransactionService transactionServiceMock = mock(TransactionService.class);
        TransactionRestController transactionController = new TransactionRestController(transactionServiceMock);

        TransactionDTO transactionDTO = new TransactionDTO();

        ResponseEntity<MainResponseDTO> responseEntity = transactionController.updateTransaction(transactionDTO);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().getCode()); // Suponiendo que el código 2 indica que no se puede actualizar la transacción
    }

    @Test
    void testDeleteTransaction() {
        TransactionService transactionServiceMock = mock(TransactionService.class);
        TransactionRestController transactionController = new TransactionRestController(transactionServiceMock);

        Long transactionId = 1L;
        MainResponseDTO responseDTO = new MainResponseDTO();
        responseDTO.setCode(0); // Suponiendo que el código 0 indica éxito en la operación

        when(transactionServiceMock.deleteTransaction(transactionId)).thenReturn(responseDTO);

        ResponseEntity<MainResponseDTO> responseEntity = transactionController.deleteTransaction(transactionId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDTO, responseEntity.getBody());
    }
}
