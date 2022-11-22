package com.javasrping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javasrping.model.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

}
