package com.test.quind.persistent.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.test.quind.persistent.entity.transsaction.TransactionEntity;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
	
	  @Query("SELECT t FROM TransactionEntity t WHERE t.sourceAccount.id = :idProduct OR t.destinationAccount.id = :idProduct")
	    List<TransactionEntity> findBySourceAccountOrDestinationAccount(@Param("idProduct") Long accountId);
	  
}
 