package com.test.quind.bussines.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.test.quind.bussines.Iservices.client.IConvertEntityClientDTO;
import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.infrastructure.controller.rest.ClientRestController;
import com.test.quind.persistent.entity.ClientEntity;

@Component
public class ConvertEntityClientDTO implements IConvertEntityClientDTO{

	private Logger log = LoggerFactory.getLogger(ClientRestController.class);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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

}
