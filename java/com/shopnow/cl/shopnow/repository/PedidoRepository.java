package com.shopnow.cl.shopnow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopnow.cl.shopnow.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByClienteId(Integer clienteId);
}

