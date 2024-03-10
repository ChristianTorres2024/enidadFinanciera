package com.test.quind.bussines.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.test.quind.bussines.Iservices.client.IConvertClientDTOEntity;
import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.infrastructure.controller.rest.ClientRestController;
import com.test.quind.persistent.entity.ClientEntity;
import com.test.quind.persistent.entity.IdentificationTypeEntity;

@Component
public class ConvertClientDTOEntity implements  IConvertClientDTOEntity{

	private Logger log = LoggerFactory.getLogger(ClientRestController.class);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	
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
			log.error("Error Format Date ::  {}", e.getCause(), e.getMessage());
			// e.printStackTrace();
		}

		return clientEntity;
	}

}
