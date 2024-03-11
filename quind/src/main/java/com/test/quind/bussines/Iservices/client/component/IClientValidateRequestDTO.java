package com.test.quind.bussines.Iservices.client.component;

import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.domain.commons.DTO.ValidateDTO;

@FunctionalInterface
public interface IClientValidateRequestDTO {
	ValidateDTO validateDataClient(ClientDTO clientDTO,Boolean actionCrear); 

}
