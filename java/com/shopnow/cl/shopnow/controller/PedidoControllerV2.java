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

import com.shopnow.cl.shopnow.assembler.PedidoModelAssembler;
import com.shopnow.cl.shopnow.model.Pedido;
import com.shopnow.cl.shopnow.service.PedidoService;

@RestController
@RequestMapping("/api/v2/pedidos")
public class PedidoControllerV2 {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> listar() {

        List<EntityModel<Pedido>> pedidos = pedidoService.findAll()
                .stream()
                .map(assembler::toModel)
                .toList();

        CollectionModel<EntityModel<Pedido>> respuesta = CollectionModel.of(
                pedidos,
                linkTo(methodOn(PedidoControllerV2.class).listar()).withSelfRel()
        );

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Pedido>> buscar(@PathVariable Integer id) {

        try {
            Pedido pedido = pedidoService.findById(id);
            return ResponseEntity.ok(assembler.toModel(pedido));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> buscarPorCliente(@PathVariable Integer clienteId) {

        List<EntityModel<Pedido>> pedidos = pedidoService.findByClienteId(clienteId)
                .stream()
                .map(assembler::toModel)
                .toList();

        CollectionModel<EntityModel<Pedido>> respuesta = CollectionModel.of(
                pedidos,
                linkTo(methodOn(PedidoControllerV2.class).buscarPorCliente(clienteId)).withSelfRel(),
                linkTo(methodOn(PedidoControllerV2.class).listar()).withRel("pedidos")
        );

        return ResponseEntity.ok(respuesta);
    }
}