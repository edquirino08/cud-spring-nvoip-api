package com.javasrping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javasrping.model.Error;

@Repository
public interface ErrorRepository extends JpaRepository<Error, Long> {

}
