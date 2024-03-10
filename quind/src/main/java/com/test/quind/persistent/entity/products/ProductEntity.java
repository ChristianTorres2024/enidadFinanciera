package com.test.quind.persistent.entity.products;

import java.io.Serializable;
import java.util.Date;

import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.persistent.entity.client.ClientEntity;

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
@Table(name = "products")
public class ProductEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long idProduct;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_type_id")
    private AccountTypeEntity accountTypeEntity;
    

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private StatusEntity StatusEntity;

	@Column(name = "account_number")
	private Long accountNumber;	
	
	@Column(name = "balance")
	private Double balance;
	
	@Column(name = "GMF_exempt")
	private String GMFExempt;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_owner")
    private ClientEntity clientEntity;
	
}
