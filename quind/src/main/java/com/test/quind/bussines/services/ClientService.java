package com.test.quind.bussines.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.quind.bussines.Iservices.client.IClientServices;
import com.test.quind.bussines.Iservices.client.IConvertEntityClientDTO;
import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.infrastructure.controller.rest.ClientRestController;
import com.test.quind.persistent.entity.ClientEntity;
import com.test.quind.persistent.repository.ClientRepository;

@Service
public class ClientService implements IClientServices, IConvertEntityClientDTO {

	private Logger log = LoggerFactory.getLogger(ClientRestController.class);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private ClientRepository clientRepository;

	@Override
	public ClientDTO getClientById(Long id) {

		log.info("getClientById Parametro: {}", id);
		ClientEntity client = clientRepository.findById(id).orElse(null);
		return convertToDTO(client);

	}

	@Override
	public ClientDTO findClientByIdTypeAndIdNumber(Long idType, Long idNumber) {
		ClientEntity client = clientRepository.findByIdentificationType_IdTypeAndIdNumber(idType, idNumber);
		return convertToDTO(client);
	}

	@Override
	public List<ClientDTO> getAllClients() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientDTO createClient(ClientDTO clientDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteClient(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public ClientDTO convertToDTO(ClientEntity clientEntity) {

		log.info("Convert entity to object :: ", clientEntity);
		if (clientEntity == null) return null;
		
		ClientDTO clientDTO = new ClientDTO();
		clientDTO.setIdentificationType(clientEntity.getIdentificationType().getTypeName());
		clientDTO.setFirstName(clientEntity.getFirstName());
		clientDTO.setLastName(clientEntity.getLastName());
		clientDTO.setEmail(clientEntity.getEmailAddress());
		try {
			
			Date dateOfBirth = dateFormat.parse(clientEntity.getDateOfBirth());
			clientDTO.setDateOfBirth(dateFormat.format(dateOfBirth));
		} catch (ParseException e) {
			log.error("Error Format getDateOfBirth {}", e.getCause(), e.getMessage());
			e.printStackTrace();
		}

		return clientDTO;
	}

}
