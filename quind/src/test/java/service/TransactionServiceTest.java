package service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.test.quind.bussines.services.TransactionService;
import com.test.quind.bussines.services.transaction.component.ConvertEntityTransactionDTO;
import com.test.quind.bussines.services.transaction.component.ConvertTransactionDTOEntity;
import com.test.quind.bussines.services.transaction.component.TransactionValidateRequestDTO;
import com.test.quind.domain.commons.DTO.MainResponseDTO;
import com.test.quind.domain.commons.DTO.TransactionDTO;
import com.test.quind.domain.commons.Enums.EnumMainResponse;
import com.test.quind.persistent.entity.transsaction.TransactionEntity;
import com.test.quind.persistent.repository.TransactionRepository;

class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ConvertEntityTransactionDTO convertEntityTransactionDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    private ConvertTransactionDTOEntity convertTransactionDTOEntity;

    @Mock
    private TransactionValidateRequestDTO transactionValidateRequestDTO;

    
    
    @Test
    void testGetTransactionById() {
        TransactionEntity transactionEntity = new TransactionEntity();
        TransactionDTO transactionDTO = new TransactionDTO();

        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transactionEntity));
        when(convertEntityTransactionDTO.convertToDTO(any())).thenReturn(transactionDTO);

        TransactionDTO result = transactionService.getTransactionById(1L);

        assertEquals(transactionDTO, result);
        verify(transactionRepository, times(1)).findById(anyLong());
        verify(convertEntityTransactionDTO, times(1)).convertToDTO(any());
    }

    @Test
    void testGetAllTransaction() {
        List<TransactionEntity> transactionEntities = Arrays.asList(new TransactionEntity(), new TransactionEntity());
        List<TransactionDTO> transactionDTOs = Arrays.asList(new TransactionDTO(), new TransactionDTO());

        when(transactionRepository.findAll()).thenReturn(transactionEntities);
        when(convertEntityTransactionDTO.convertToDTO(any())).thenReturn(transactionDTOs.get(0), transactionDTOs.get(1));

        List<TransactionDTO> result = transactionService.getAllTransaction();

        assertEquals(transactionDTOs, result);
        verify(transactionRepository, times(1)).findAll();
        verify(convertEntityTransactionDTO, times(2)).convertToDTO(any());
    }

    @Test
    void testGetTransactionByIdProduct() {
        List<TransactionEntity> transactionEntities = Arrays.asList(new TransactionEntity(), new TransactionEntity());
        List<TransactionDTO> transactionDTOs = Arrays.asList(new TransactionDTO(), new TransactionDTO());

        when(transactionRepository.findBySourceAccountOrDestinationAccount(anyLong())).thenReturn(transactionEntities);
        when(convertEntityTransactionDTO.convertToDTO(any())).thenReturn(transactionDTOs.get(0), transactionDTOs.get(1));

        List<TransactionDTO> result = transactionService.getTransactionByIdProduct(1L);

        assertEquals(transactionDTOs, result);
        verify(transactionRepository, times(1)).findBySourceAccountOrDestinationAccount(anyLong());
        verify(convertEntityTransactionDTO, times(2)).convertToDTO(any());
    }
    
    @Test
    void testCreateTransaction() {
        TransactionDTO transactionDTO = new TransactionDTO();
        TransactionEntity transactionEntity = new TransactionEntity();
        MainResponseDTO mainResponseDTO = new MainResponseDTO(EnumMainResponse.OK.getCode(), EnumMainResponse.OK.getMessage());

        when(transactionValidateRequestDTO.validateDataTransaction(any())).thenReturn(null);
        when(convertTransactionDTOEntity.convertToEntity(any())).thenReturn(transactionEntity);
        when(transactionRepository.save(any())).thenReturn(transactionEntity);

        MainResponseDTO result = transactionService.createTransaction(transactionDTO);

        assertEquals(mainResponseDTO, result);
        verify(transactionRepository, times(1)).save(any());
    }

    @Test
    void testDeleteTransaction() {
        TransactionEntity transactionEntity = new TransactionEntity();
        MainResponseDTO mainResponseDTO = new MainResponseDTO(EnumMainResponse.OK.getCode(), EnumMainResponse.OK.getMessage());

        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transactionEntity));

        MainResponseDTO result = transactionService.deleteTransaction(1L);

        assertEquals(mainResponseDTO, result);
        verify(transactionRepository, times(1)).deleteById(anyLong());
    }
}
