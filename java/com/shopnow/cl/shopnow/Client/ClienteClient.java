package com.shopnow.cl.shopnow.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shopnow.cl.shopnow.config.FeignConfig;
import com.shopnow.cl.shopnow.dto.ClienteDTO;

@FeignClient(
    name = "cliente-service",
    url = "http://localhost:8080",
    configuration = FeignConfig.class
)
public interface ClienteClient {

    @GetMapping("/api/v1/clientes/{id}")
    ClienteDTO obtenerCliente(@PathVariable("id") Integer id);
}