package com.test.quind.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.quind.persistent.entity.client.ClientEntity;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
	
	ClientEntity findByIdentificationType_IdTypeAndIdNumber(Long idType, Long idNumber);
}
