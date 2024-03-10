package com.test.quind.domain.commons.DTO;

import java.util.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
	
	@NotNull(message="id type no puede ser nulo")
	@Size(min=2, max=50)
	private String identificationType;
	
	@NotNull(message="identificationNumber no puede ser nulo")
	@Size(min=5, max=19)
	private Integer identificationNumber;
	
	@NotNull(message="El nombre no puede ser nulo")
	@Size(min=2, max=50, message="El nombre no puede ser menor a 2 y ser mayor a 50 caracteres")
	private String firstName;
	
	@NotNull(message="descripcion no puede ser nulo")
	@Size(min=2, max=50, message="El apellido no puede ser menor a 2 y ser mayor a 50 caracteres")
	private String lastName;
	
	@Email
	@NotBlank
	@NotNull(message="descripcion no puede ser nulo")
	@Size(min=2, max=50, message="El apellido no puede ser menor a 2 y ser mayor a 50 caracteres")
	private String email;
	
	@NotNull(message="fecha de nacimiento no puede ser nulo")
	private String dateOfBirth;

}
