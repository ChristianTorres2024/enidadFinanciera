package com.test.quind.persistent.entity.transsaction;


import java.io.Serializable;
import java.util.Date;

import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.persistent.entity.client.ClientEntity;
import com.test.quind.persistent.entity.products.ProductEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class TransactionEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long idTransaction;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_type_id")
    private TransactionTypeEntity transactionTypeEntity;
    
	@Column(name = "amount")
	private Double amount;
	
	@Column(name = "transaction_date")
	private String transactionDate;
	
	
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "successful")
	private String successful;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id")
    private ProductEntity sourceAccount;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_account_id")
    private ProductEntity destinationAccount;
	
	
}
