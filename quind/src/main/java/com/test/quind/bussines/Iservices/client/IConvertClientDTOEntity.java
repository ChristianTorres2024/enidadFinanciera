package com.test.quind.bussines.Iservices.client;

import java.text.ParseException;

import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.persistent.entity.ClientEntity;


@FunctionalInterface
public interface IConvertClientDTOEntity {
	ClientEntity convertToEntity(ClientDTO clientDTO) throws ParseException; 
}
