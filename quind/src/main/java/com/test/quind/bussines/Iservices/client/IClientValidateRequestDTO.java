package com.test.quind.bussines.Iservices.client;

import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.domain.commons.DTO.ValidateDTO;

public interface IClientValidateRequestDTO {
	ValidateDTO validateDataClient(ClientDTO clientDTO,Boolean actionCrear); 

}