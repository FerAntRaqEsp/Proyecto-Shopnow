package com.shopnow.cl.shopnow.assembler;

import com.shopnow.cl.shopnow.controller.PedidoControllerV2;
import com.shopnow.cl.shopnow.model.Pedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PedidoModelAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @Override
    public EntityModel<Pedido> toModel(Pedido pedido) {
        return EntityModel.of(pedido,
                linkTo(methodOn(PedidoControllerV2.class).buscar(pedido.getId())).withSelfRel(),
                linkTo(methodOn(PedidoControllerV2.class).listar()).withRel("pedidos"),
                linkTo(methodOn(PedidoControllerV2.class).buscarPorCliente(pedido.getClienteId())).withRel("pedidos-del-cliente")
        );
    }
}