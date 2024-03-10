package com.test.quind.bussines.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.quind.bussines.Iservices.client.IClientServices;
import com.test.quind.bussines.Iservices.client.IConvertClientDTOEntity;
import com.test.quind.bussines.Iservices.client.IConvertEntityClientDTO;
import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.domain.commons.DTO.MainResponseDTO;
import com.test.quind.domain.commons.Enums.EnumMainResponse;
import com.test.quind.infrastructure.controller.rest.ClientRestController;
import com.test.quind.persistent.entity.ClientEntity;
import com.test.quind.persistent.entity.IdentificationTypeEntity;
import com.test.quind.persistent.repository.ClientRepository;

@Service
public class ClientService implements IClientServices, IConvertEntityClientDTO, IConvertClientDTOEntity {

	private Logger log = LoggerFactory.getLogger(ClientRestController.class);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private ClientRepository clientRepository;

	@Override
	public ClientDTO getClientById(Long id) {

		try {
			ClientEntity client = clientRepository.findById(id).orElse(null);
			return convertToDTO(client);
		} catch (Exception e) {
			log.error("Error create client {}", e.getCause(), e.getMessage());
			return null;
		}

	}

	@Override
	public ClientDTO findClientByIdTypeAndIdNumber(Long idType, Long idNumber) {

		try {
			ClientEntity client = clientRepository.findByIdentificationType_IdTypeAndIdNumber(idType, idNumber);
			return convertToDTO(client);
		} catch (Exception e) {
			log.error("Error create client {}", e.getCause(), e.getMessage());
			return null;
		}
	}

	@Override
	public List<ClientDTO> getAllClients() {

		try {
			List<ClientEntity> clientEntities = clientRepository.findAll();
			List<ClientDTO> clientDTOs = clientEntities.stream().map(this::convertToDTO).collect(Collectors.toList());

			return clientDTOs;
		} catch (Exception e) {
			log.error("Error create client {}", e.getCause(), e.getMessage());
			return null;
		}

	}

	@Override
	public MainResponseDTO createClient(ClientDTO clientDTO) {

		ClientEntity clientEntity = convertToEntity(clientDTO);

		try {
			clientRepository.save(clientEntity);
			log.info("create client Ok {}", clientEntity);
			return new MainResponseDTO(EnumMainResponse.OK.getCode(), EnumMainResponse.OK.getMessage());
		} catch (Exception e) {
			log.error("Error create client {}", e.getCause(), e.getMessage());
			return new MainResponseDTO(EnumMainResponse.ERROR.getCode(), EnumMainResponse.ERROR.getMessage());
		}
	}

	@Override
	public MainResponseDTO updateClient(Long id, ClientDTO clientDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MainResponseDTO deleteClient(Long id) {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public ClientDTO convertToDTO(ClientEntity clientEntity) {

		log.info("Convert entity to object :: {}", clientEntity);
		if (clientEntity == null)
			return null;

		ClientDTO clientDTO = new ClientDTO();
		clientDTO.setIdentificationType(clientEntity.getIdentificationType().getTypeName());
		clientDTO.setFirstName(clientEntity.getFirstName());
		clientDTO.setLastName(clientEntity.getLastName());
		clientDTO.setEmail(clientEntity.getEmailAddress());
		clientDTO.setIdentificationNumber(clientEntity.getIdNumber());
		try {

			Date dateOfBirth = dateFormat.parse(clientEntity.getDateOfBirth());
			clientDTO.setDateOfBirth(dateFormat.format(dateOfBirth));
		} catch (ParseException e) {
			log.error("Error Format getDateOfBirth {}", e.getCause(), e.getMessage());
			e.printStackTrace();
		}

		return clientDTO;
	}

	@Override
	public ClientEntity convertToEntity(ClientDTO clientDTO) {
		log.info("Convert DTO to entity :: {}", clientDTO);
		if (clientDTO == null)
			return null;

		ClientEntity clientEntity = new ClientEntity();
		clientEntity.setFirstName(clientDTO.getFirstName());
		clientEntity.setLastName(clientDTO.getLastName());
		clientEntity.setEmailAddress(clientDTO.getEmail());
		clientEntity.setIdNumber(clientDTO.getIdentificationNumber());

		try {

			Date dateOfBirth = dateFormat.parse(clientDTO.getDateOfBirth());
			clientEntity.setDateOfBirth(dateFormat.format(dateOfBirth));

			// Buscar el tipo de identificación en la tabla identification_type
			IdentificationTypeEntity identificationTypeEntity = new IdentificationTypeEntity();
			identificationTypeEntity.setIdType(Long.valueOf(clientDTO.getIdentificationType()));
			clientEntity.setIdentificationType(identificationTypeEntity);

		} catch (Exception e) {
			log.error("Error Format {}", e.getCause(), e.getMessage());
			// e.printStackTrace();
		}

		return clientEntity;
	}

}
