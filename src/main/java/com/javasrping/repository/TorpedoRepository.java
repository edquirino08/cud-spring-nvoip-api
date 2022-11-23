package com.javasrping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javasrping.model.Torpedo;

@Repository
public interface TorpedoRepository extends JpaRepository<Torpedo, Long> {

}
