package com.test.quind.bussines.services.client.component;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.test.quind.bussines.Iservices.client.component.IClientValidateRequestDTO;
import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.domain.commons.DTO.ValidateDTO;
import com.test.quind.domain.commons.Enums.EnumValidateClientDTO;

@Component
public class ClientValidateRequestDTO implements IClientValidateRequestDTO {

	@Override
	public ValidateDTO validateDataClient(ClientDTO clientDTO, Boolean actionCrear) {
		if (actionCrear) {

			if (clientDTO.getIdentificationType() == null)
				return new ValidateDTO(EnumValidateClientDTO.NULL_ID_TYPE.getCode(),
						EnumValidateClientDTO.NULL_ID_TYPE.getField(),
						EnumValidateClientDTO.NULL_ID_TYPE.getDescription());

			if (clientDTO.getIdentificationNumber() == null)
				return new ValidateDTO(EnumValidateClientDTO.NULL_ID_NUMBER.getCode(),
						EnumValidateClientDTO.NULL_ID_NUMBER.getField(),
						EnumValidateClientDTO.NULL_ID_NUMBER.getDescription());
		}
		if (clientDTO.getDateOfBirth() == null)
			return new ValidateDTO(EnumValidateClientDTO.NULL_UNDERAGE.getCode(),
					EnumValidateClientDTO.NULL_UNDERAGE.getField(),
					EnumValidateClientDTO.NULL_UNDERAGE.getDescription());

		if (clientDTO.getEmail() == null)
			return new ValidateDTO(EnumValidateClientDTO.NULL_EMAIL.getCode(),
					EnumValidateClientDTO.NULL_EMAIL.getField(), EnumValidateClientDTO.NULL_EMAIL.getDescription());

		if (clientDTO.getFirstName() == null)
			return new ValidateDTO(EnumValidateClientDTO.NULL_FIRTSNAME.getCode(),
					EnumValidateClientDTO.NULL_FIRTSNAME.getField(),
					EnumValidateClientDTO.NULL_FIRTSNAME.getDescription());

		if (clientDTO.getLastName() == null)
			return new ValidateDTO(EnumValidateClientDTO.NULL_LASTNAME.getCode(),
					EnumValidateClientDTO.NULL_LASTNAME.getField(),
					EnumValidateClientDTO.NULL_LASTNAME.getDescription());

		try {
			if (Period.between(LocalDate.parse(clientDTO.getDateOfBirth()), LocalDate.now()).getYears() < 18)
				return new ValidateDTO(EnumValidateClientDTO.UNDERAGE.getCode(),
						EnumValidateClientDTO.UNDERAGE.getField(), EnumValidateClientDTO.UNDERAGE.getDescription());
		} catch (Exception e) {
			return new ValidateDTO(EnumValidateClientDTO.INVALID_DATEBIRTH.getCode(),
					EnumValidateClientDTO.INVALID_DATEBIRTH.getField(),
					EnumValidateClientDTO.INVALID_DATEBIRTH.getDescription());
		}

		Pattern emailPattern = Pattern.compile("^(.+)@([a-zA-Z0-9-]+\\.)+([a-zA-Z]{2,})$");
		if (!emailPattern.matcher(clientDTO.getEmail()).matches())
			return new ValidateDTO(EnumValidateClientDTO.INVALID_EMAIL.getCode(),
					EnumValidateClientDTO.INVALID_EMAIL.getField(),
					EnumValidateClientDTO.INVALID_EMAIL.getDescription());

		Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
		if (clientDTO.getFirstName().length() < 2 || clientDTO.getLastName().length() < 2
				|| !pattern.matcher(clientDTO.getFirstName()).matches()
				|| !pattern.matcher(clientDTO.getLastName()).matches())
			return new ValidateDTO(EnumValidateClientDTO.SHORT_NAME_LASTNAME.getCode(),
					EnumValidateClientDTO.SHORT_NAME_LASTNAME.getField(),
					EnumValidateClientDTO.SHORT_NAME_LASTNAME.getDescription());

		return null;
	}

}
