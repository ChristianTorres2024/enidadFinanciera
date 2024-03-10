package com.test.quind.bussines.Iservices.client;

import java.util.List;

import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.domain.commons.DTO.MainResponseDTO;

public interface IClientServices {

	ClientDTO getClientById(Long id);

	ClientDTO findClientByIdTypeAndIdNumber(Long idType, Long idNumber);

	List<ClientDTO> getAllClients();

	MainResponseDTO createClient(ClientDTO clientDTO);

	MainResponseDTO updateClient(Long id, ClientDTO clientDTO);

	MainResponseDTO deleteClient(Long id);

}
