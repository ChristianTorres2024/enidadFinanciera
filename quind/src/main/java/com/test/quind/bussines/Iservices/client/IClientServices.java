package com.test.quind.bussines.Iservices.client;

import java.util.List;

import com.test.quind.domain.commons.DTO.ClientDTO;

public interface IClientServices {

	ClientDTO getClientById(Long id);

	ClientDTO findClientByIdTypeAndIdNumber(Long idType, Long idNumber);

	List<ClientDTO> getAllClients();

	ClientDTO createClient(ClientDTO clientDTO);

	ClientDTO updateClient(Long id, ClientDTO clientDTO);

	void deleteClient(Long id);

}
