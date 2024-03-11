package service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.test.quind.bussines.services.ClientService;
import com.test.quind.bussines.services.client.component.ClientValidateRequestDTO;
import com.test.quind.bussines.services.client.component.ConvertClientDTOEntity;
import com.test.quind.bussines.services.client.component.ConvertEntityClientDTO;
import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.domain.commons.DTO.MainResponseDTO;
import com.test.quind.domain.commons.Enums.EnumMainResponse;
import com.test.quind.persistent.entity.client.ClientEntity;
import com.test.quind.persistent.entity.client.IdentificationTypeEntity;
import com.test.quind.persistent.repository.ClientRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ConvertEntityClientDTO convertEntityClientDTO;

    @Mock
	private ConvertClientDTOEntity convertClientDTOEntity;
   
    @Mock
    private ClientValidateRequestDTO clientValidateRequestDTO;
    
    @SuppressWarnings("deprecation")
	@BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetClientById() {
        ClientEntity clientEntity = new ClientEntity();
        ClientDTO clientDTO = new ClientDTO();

        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(clientEntity));
        when(convertEntityClientDTO.convertToDTO(any())).thenReturn(clientDTO);

        ClientDTO result = clientService.getClientById(1L);

        assertEquals(clientDTO, result);
        verify(clientRepository, times(1)).findById(anyLong());
        verify(convertEntityClientDTO, times(1)).convertToDTO(any());
    }

    @Test
    void testFindClientByIdTypeAndIdNumber() {
        ClientEntity clientEntity = new ClientEntity();
        ClientDTO clientDTO = new ClientDTO();

        when(clientRepository.findByIdentificationType_IdTypeAndIdNumber(anyLong(), anyLong())).thenReturn(clientEntity);
        when(convertEntityClientDTO.convertToDTO(any())).thenReturn(clientDTO);

        ClientDTO result = clientService.findClientByIdTypeAndIdNumber(1L, 1L);

        assertEquals(clientDTO, result);
        verify(clientRepository, times(1)).findByIdentificationType_IdTypeAndIdNumber(anyLong(), anyLong());
        verify(convertEntityClientDTO, times(1)).convertToDTO(any());
    }

    @Test
    void testGetAllClients() {
        List<ClientEntity> clientEntities = Arrays.asList(new ClientEntity(), new ClientEntity());
        List<ClientDTO> clientDTOs = Arrays.asList(new ClientDTO(), new ClientDTO());

        when(clientRepository.findAll()).thenReturn(clientEntities);
        when(convertEntityClientDTO.convertToDTO(any())).thenReturn(clientDTOs.get(0), clientDTOs.get(1));

        List<ClientDTO> result = clientService.getAllClients();

        assertEquals(clientDTOs, result);
        verify(clientRepository, times(1)).findAll();
        verify(convertEntityClientDTO, times(2)).convertToDTO(any());
    }
    
    
    @Test
    void testCreateClient() {
        ClientDTO clientDTO = new ClientDTO();
        ClientEntity clientEntity = new ClientEntity();
        IdentificationTypeEntity identificationTypeEntity = new IdentificationTypeEntity();
        MainResponseDTO mainResponseDTO = new MainResponseDTO(EnumMainResponse.OK.getCode(), EnumMainResponse.OK.getMessage());
        clientEntity.setIdNumber(123456789);
        clientEntity.setIdentificationType(identificationTypeEntity);

        when(convertClientDTOEntity.convertToEntity(any())).thenReturn(clientEntity);
        when(clientRepository.save(any())).thenReturn(clientEntity);
        when(clientRepository.findByIdentificationType_IdTypeAndIdNumber(anyLong(), anyLong())).thenReturn(null);

        MainResponseDTO result = clientService.createClient(clientDTO);

        assertEquals(mainResponseDTO, result);
        verify(clientRepository, times(1)).save(any());
    }


    @Test
    void testUpdateClient() {
        ClientDTO clientDTO = new ClientDTO();
        ClientEntity clientEntity = new ClientEntity();
        MainResponseDTO mainResponseDTO = new MainResponseDTO(EnumMainResponse.OK.getCode(), EnumMainResponse.OK.getMessage());

        clientDTO.setIdClient(1L); 

        when(clientValidateRequestDTO.validateDataClient(any(), anyBoolean())).thenReturn(null);
        when(convertClientDTOEntity.convertToEntity(any())).thenReturn(clientEntity);
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(clientEntity));
        when(clientRepository.save(any())).thenReturn(clientEntity);

        MainResponseDTO result = clientService.updateClient(clientDTO);

        assertEquals(mainResponseDTO, result);
        verify(clientRepository, times(1)).save(any());
    }


    @Test
    void testUpdateDataExistingClient() {
        ClientEntity existingClient = new ClientEntity();
        ClientEntity clientEntity = new ClientEntity();

        ClientEntity result = clientService.updateDataExistingClient(existingClient, clientEntity);

        assertEquals(existingClient, result);
    }

    @Test
    void testDeleteClient() {
        ClientEntity clientEntity = new ClientEntity();
        MainResponseDTO mainResponseDTO = new MainResponseDTO(EnumMainResponse.OK.getCode(), EnumMainResponse.OK.getMessage());

        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(clientEntity));

        MainResponseDTO result = clientService.deleteClient(1L);

        assertEquals(mainResponseDTO, result);
        verify(clientRepository, times(1)).deleteById(anyLong());
    }
}
