package com.test.quind.bussines.Iservices.client;

import java.text.ParseException;

import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.persistent.entity.client.ClientEntity;

@FunctionalInterface
public interface IConvertEntityClientDTO {

    ClientDTO convertToDTO(ClientEntity clientEntity) throws ParseException;

	
}
