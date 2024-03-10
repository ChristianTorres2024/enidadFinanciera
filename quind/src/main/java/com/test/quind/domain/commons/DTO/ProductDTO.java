package com.test.quind.domain.commons.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
	
	@NotNull()
	@Size(min=4, max=10)
	private Long idProduct;
	
	
	@NotNull()
	@Size(min=1, max=50)
	private String accountType;
	
	@NotNull()
	@Size(min=10, max=10)
	private Long accountNummber;
	
	@NotNull()
	@Size(min=2, max=50)
	private String status;
	
	@NotNull()
	@Size(min=2, max=3)
	private Double balance;
	
	@NotNull()
	@Size(min=2, max=3)
	private String gmfExempt;
	
	private ClientDTO clientDTO; 
	

}