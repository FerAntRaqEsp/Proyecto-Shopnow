package com.shopnow.cl.shopnow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopnow.cl.shopnow.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}