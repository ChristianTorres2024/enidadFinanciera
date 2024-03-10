package com.test.quind.bussines.services.products;


import org.springframework.stereotype.Component;
import com.test.quind.bussines.Iservices.products.IProductValidateRequestDTO;
import com.test.quind.domain.commons.DTO.ProductDTO;
import com.test.quind.domain.commons.DTO.ValidateDTO;
import com.test.quind.domain.commons.Enums.EnumValidatePoductDTO;

@Component
public class ProductValidateRequestDTO implements IProductValidateRequestDTO {

	@Override
	public ValidateDTO validateDataProduct(ProductDTO productDTO) {

		if (productDTO.getAccountType() == null)
			return new ValidateDTO(EnumValidatePoductDTO.NULL_ACCOUNT_TYPE.getCode(),
					EnumValidatePoductDTO.NULL_ACCOUNT_TYPE.getField(),
					EnumValidatePoductDTO.NULL_ACCOUNT_TYPE.getDescription());

		if (productDTO.getStatus() == null)
			return new ValidateDTO(EnumValidatePoductDTO.NULL_STATUS.getCode(),
					EnumValidatePoductDTO.NULL_STATUS.getField(), EnumValidatePoductDTO.NULL_STATUS.getDescription());

		if (productDTO.getBalance() == null)
			return new ValidateDTO(EnumValidatePoductDTO.NULL_BALANCE.getCode(),
					EnumValidatePoductDTO.NULL_BALANCE.getField(), EnumValidatePoductDTO.NULL_BALANCE.getDescription());

		if (productDTO.getGmfExempt() == null)
			return new ValidateDTO(EnumValidatePoductDTO.NULL_GMFEXEMPT.getCode(),
					EnumValidatePoductDTO.NULL_GMFEXEMPT.getField(),
					EnumValidatePoductDTO.NULL_GMFEXEMPT.getDescription());

		if (productDTO.getClientDTO() == null || productDTO.getClientDTO().getIdClient() == null)
			return new ValidateDTO(EnumValidatePoductDTO.NULL_CLIENTdto.getCode(),
					EnumValidatePoductDTO.NULL_CLIENTdto.getField(),
					EnumValidatePoductDTO.NULL_CLIENTdto.getDescription());

		if (!productDTO.getAccountType().equalsIgnoreCase("1")
				&& !productDTO.getAccountType().equalsIgnoreCase("2")) {
			return new ValidateDTO(EnumValidatePoductDTO.INVALID_ACCOUNT_TYPE.getCode(),
					EnumValidatePoductDTO.INVALID_ACCOUNT_TYPE.getField(),
					EnumValidatePoductDTO.INVALID_ACCOUNT_TYPE.getDescription());
		}

		if (productDTO.getAccountType().equalsIgnoreCase("2")
				&& (productDTO.getBalance() != null && productDTO.getBalance() < 0)) {
			return new ValidateDTO(EnumValidatePoductDTO.NEGATIVE_BALANCE.getCode(),
					EnumValidatePoductDTO.NEGATIVE_BALANCE.getField(),
					EnumValidatePoductDTO.NEGATIVE_BALANCE.getDescription());
		}

		return null;
	}

}
