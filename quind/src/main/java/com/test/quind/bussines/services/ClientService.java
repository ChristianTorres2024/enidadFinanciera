package com.test.quind.bussines.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.test.quind.bussines.Iservices.IClientServices;
import com.test.quind.bussines.services.client.component.ClientValidateRequestDTO;
import com.test.quind.bussines.services.client.component.ConvertClientDTOEntity;
import com.test.quind.bussines.services.client.component.ConvertEntityClientDTO;
import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.domain.commons.DTO.MainResponseDTO;
import com.test.quind.domain.commons.DTO.ValidateDTO;
import com.test.quind.domain.commons.Enums.EnumMainResponse;
import com.test.quind.domain.commons.Enums.EnumValidateClientDTO;
import com.test.quind.infrastructure.controller.rest.ClientRestController;
import com.test.quind.persistent.entity.client.ClientEntity;
import com.test.quind.persistent.repository.ClientRepository;

@Service
@Configuration
public class ClientService implements IClientServices {

	private Logger log = LoggerFactory.getLogger(ClientRestController.class);

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ConvertClientDTOEntity convertClientDTOEntity;

	@Autowired
	private ConvertEntityClientDTO convertEntityClientDTO;

	@Autowired
	private ClientValidateRequestDTO clientValidateRequestDTO;

	@Override
	public ClientDTO getClientById(Long id) {

		try {
			ClientEntity client = clientRepository.findById(id).orElse(null);
			return convertEntityClientDTO.convertToDTO(client);
		} catch (Exception e) {
			log.error("Error create client {}", e.getCause(), e.getMessage());
			return null;
		}

	}

	@Override
	public ClientDTO findClientByIdTypeAndIdNumber(Long idType, Long idNumber) {

		try {
			ClientEntity client = clientRepository.findByIdentificationType_IdTypeAndIdNumber(idType, idNumber);
			return convertEntityClientDTO.convertToDTO(client);
		} catch (Exception e) {
			log.error("Error create client {}", e.getCause(), e.getMessage());
			return null;
		}
	}

	@Override
	public List<ClientDTO> getAllClients() {

		try {
			List<ClientEntity> clientEntities = clientRepository.findAll();
			List<ClientDTO> clientDTOs = clientEntities.stream().map(convertEntityClientDTO::convertToDTO)
					.collect(Collectors.toList());

			return clientDTOs;
		} catch (Exception e) {
			log.error("Error create client {}", e.getCause(), e.getMessage());
			return null;
		}

	}

	@Override
	public MainResponseDTO createClient(ClientDTO clientDTO) {

		ValidateDTO validateDTO = clientValidateRequestDTO.validateDataClient(clientDTO, true);
		if (validateDTO != null) {
			return new MainResponseDTO(validateDTO.getCode(), validateDTO.getField() + validateDTO.getError());
		}

		ClientEntity clientEntity = convertClientDTOEntity.convertToEntity(clientDTO);

		//verificar que no exista
		ClientDTO clientDTOFind = findClientByIdTypeAndIdNumber(clientEntity.getIdentificationType().getIdType(),
				Long.valueOf(clientEntity.getIdNumber()));
		
		if (clientDTOFind != null)
			return new MainResponseDTO(EnumValidateClientDTO.DUPLICATEKEYS.getCode(),
					EnumValidateClientDTO.DUPLICATEKEYS.getField()
							+ EnumValidateClientDTO.DUPLICATEKEYS.getDescription());

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
	public MainResponseDTO updateClient(ClientDTO clientDTO) {

		ValidateDTO validateDTO = clientValidateRequestDTO.validateDataClient(clientDTO, false);
		if (validateDTO != null) {
			return new MainResponseDTO(validateDTO.getCode(), validateDTO.getField() + validateDTO.getError());
		}
		try {

			ClientEntity clientEntity = convertClientDTOEntity.convertToEntity(clientDTO);

			Optional<ClientEntity> existingClientOptional = clientRepository.findById(clientDTO.getIdClient());
			if (existingClientOptional.isPresent()) {

				clientRepository.save(updateDataExistingClient(existingClientOptional.get(), clientEntity));
				return new MainResponseDTO(EnumMainResponse.OK.getCode(), EnumMainResponse.OK.getMessage());
			} else {

				return new MainResponseDTO(EnumMainResponse.ERROR.getCode(), EnumMainResponse.ERROR.getMessage());
			}
		} catch (Exception e) {

			log.error("Error al actualizar el cliente: {}", e.getMessage());
			return new MainResponseDTO(EnumMainResponse.ERROR.getCode(), EnumMainResponse.ERROR.getMessage());
		}
	}

	@Override
	public ClientEntity updateDataExistingClient(ClientEntity existingClient, ClientEntity clientEntity) {

		existingClient.setFirstName(
				clientEntity.getFirstName() != null ? clientEntity.getFirstName() : existingClient.getFirstName());
		existingClient.setLastName(
				clientEntity.getLastName() != null ? clientEntity.getLastName() : existingClient.getLastName());
		existingClient.setEmailAddress(clientEntity.getEmailAddress() != null ? clientEntity.getEmailAddress()
				: existingClient.getEmailAddress());
		existingClient.setDateOfBirth(clientEntity.getDateOfBirth() != null ? clientEntity.getDateOfBirth()
				: existingClient.getDateOfBirth());
		return existingClient;
	}

	@Override
	public MainResponseDTO deleteClient(Long id) {
		try {
			Optional<ClientEntity> existingClientOptional = clientRepository.findById(id);
			if (existingClientOptional.isPresent()) {
				clientRepository.deleteById(id);
				return new MainResponseDTO(EnumMainResponse.OK.getCode(), EnumMainResponse.OK.getMessage());
			} else {
				return new MainResponseDTO(EnumMainResponse.ERROR.getCode(), EnumMainResponse.ERROR.getMessage());
			}
		} catch (Exception e) {
			log.error("Error al eliminar el cliente: {}", e.getMessage());
			return new MainResponseDTO(EnumMainResponse.ERROR.getCode(), EnumMainResponse.ERROR.getMessage());
		}
	}

}
