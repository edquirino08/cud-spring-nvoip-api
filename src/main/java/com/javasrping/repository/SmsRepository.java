package com.javasrping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javasrping.model.Sms;

@Repository
public interface SmsRepository extends JpaRepository<Sms, Long> {

}
