package com.shopnow.cl.shopnow.service;

import com.shopnow.cl.shopnow.model.Pedido;
import com.shopnow.cl.shopnow.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Pedido findById(Integer id) {
        return pedidoRepository.findById(id).get();
    }

    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void delete(Integer id) {
        pedidoRepository.deleteById(id);
    }

    public List<Pedido> findByClienteId(Integer clienteId) {
    return pedidoRepository.findByClienteId(clienteId);
    }
}
