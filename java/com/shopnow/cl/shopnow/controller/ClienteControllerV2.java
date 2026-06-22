package com.shopnow.cl.shopnow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopnow.cl.shopnow.assembler.ClienteModelAssembler;
import com.shopnow.cl.shopnow.model.Cliente;
import com.shopnow.cl.shopnow.service.ClienteService;

@RestController
@RequestMapping("/api/v2/clientes")
public class ClienteControllerV2 {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Cliente>>> listar() {

        List<EntityModel<Cliente>> clientes = clienteService.findAll()
                .stream()
                .map(assembler::toModel)
                .toList();

        CollectionModel<EntityModel<Cliente>> respuesta = CollectionModel.of(
                clientes,
                linkTo(methodOn(ClienteControllerV2.class).listar()).withSelfRel()
        );

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Cliente>> buscar(@PathVariable Integer id) {

        try {
            Cliente cliente = clienteService.findById(id);

            return ResponseEntity.ok(
                    assembler.toModel(cliente)
            );

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}