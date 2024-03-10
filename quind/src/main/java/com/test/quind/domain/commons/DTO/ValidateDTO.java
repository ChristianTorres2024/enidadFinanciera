package com.test.quind.domain.commons.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateDTO {
		
	@NotNull
	private int code;
	
	@NotNull
	private String field;
	
	@NotNull
	private String error;
	
}

