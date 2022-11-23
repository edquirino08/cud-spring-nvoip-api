package com.javasrping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javasrping.model.User;

@Repository
public interface QueryRepository extends JpaRepository<User, Long> {

	User findBynumbersip(String numbersip);
	
	User findIdBynumbersip(String numbersip);
	
	User deleteBynumbersip(String numbersip);
	
}


