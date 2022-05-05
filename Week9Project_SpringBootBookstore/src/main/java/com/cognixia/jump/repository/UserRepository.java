package com.cognixia.jump.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
	

	// @Query("select a from Address a where a.city = ?1 and a.state = ?2")
	// List<Address> allAddressesInSameCity(String city, String state);
	
}
