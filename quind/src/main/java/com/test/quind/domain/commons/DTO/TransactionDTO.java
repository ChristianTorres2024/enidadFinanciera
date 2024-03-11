package com.test.quind.domain.commons.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
	
	@NotNull()
	@Size(min=4, max=10)
	private Long idTransaction;
	
	
	@NotNull()
	@Size(min=1, max=50)
	private String TransactionType;
	
	@NotNull()
	@Size(min=0)
	private Double amount;
	
	@NotNull()
	@Size(min=2, max=30)
	private String transactionDate;
	
	@NotNull()
	@Size(max=100)
	private String description;
	
	@NotNull()
	@Size(max=100)
	private String successful;
	
	private ProductDTO sourceAccount;
	
	private ProductDTO destinationAccount;
	

}