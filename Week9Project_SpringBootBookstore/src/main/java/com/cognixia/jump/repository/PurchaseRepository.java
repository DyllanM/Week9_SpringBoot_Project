package com.cognixia.jump.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>{

	Optional<Purchase> findByDate(Date date);

}
