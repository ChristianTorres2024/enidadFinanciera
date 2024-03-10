package com.test.quind.bussines.services.products;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.test.quind.bussines.Iservices.client.IClientValidateRequestDTO;
import com.test.quind.bussines.Iservices.products.IProductValidateRequestDTO;
import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.domain.commons.DTO.ProductDTO;
import com.test.quind.domain.commons.DTO.ValidateDTO;
import com.test.quind.domain.commons.Enums.EnumValidateClientDTO;

@Component
public class ProductValidateRequestDTO implements IProductValidateRequestDTO {

	@Override
	public ValidateDTO validateDataProduct(ProductDTO productDTO, Boolean actionCrear) {
        //Pendiente
		return null;
	}

}
