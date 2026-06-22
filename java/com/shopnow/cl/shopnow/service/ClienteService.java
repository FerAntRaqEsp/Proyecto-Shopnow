package com.shopnow.cl.shopnow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopnow.cl.shopnow.client.PedidoClient;
import com.shopnow.cl.shopnow.model.Cliente;
import com.shopnow.cl.shopnow.repository.ClienteRepository;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PedidoClient pedidoClient;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(Integer id) {
        return clienteRepository.findById(id).get();
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public String delete(Integer id, String token) {

    String resultado = pedidoClient.validarClienteSinPedidos(id, token);

        if (!resultado.equals("SIN_PEDIDOS")) {
            return resultado;
    }

    clienteRepository.deleteById(id);
    return "ELIMINADO";
    }
}