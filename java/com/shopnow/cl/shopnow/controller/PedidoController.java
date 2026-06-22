package com.shopnow.cl.shopnow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopnow.cl.shopnow.Client.ClienteClient;
import com.shopnow.cl.shopnow.dto.ClienteDTO;
import com.shopnow.cl.shopnow.model.Pedido;
import com.shopnow.cl.shopnow.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pedidos", description = "Operaciones relacionadas con los pedidos")
@RestController
@RequestMapping("/api/v1/pedidos")

public class PedidoController {

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private ClienteClient clienteClient;

    @Operation(summary = "Listar pedidos", description = "Obtiene una lista de todos los pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos"),
            @ApiResponse(responseCode = "204", description = "No hay pedidos para mostrar")
    })
    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        List<Pedido> pedidos = pedidoService.findAll();

        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Guardar pedido", description = "Crea un nuevo pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creado"),
            @ApiResponse(responseCode = "400", description = "Datos del pedido inválidos")
    })
    @PostMapping
    public ResponseEntity<Pedido> guardar(@RequestBody Pedido pedido) {
        Pedido pedidoNuevo = pedidoService.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoNuevo);
    }


    @Operation(summary = "Buscar pedido", description = "Obtiene los detalles de un pedido por su ID")    
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscar(@PathVariable Integer id) {
        try {
            Pedido pedido = pedidoService.findById(id);
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualizar pedido", description = "Actualiza los datos de un pedido existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido actualizado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizar(@PathVariable Integer id, @RequestBody Pedido pedido) {
        try {
            Pedido ped = pedidoService.findById(id);

            ped.setClienteId(pedido.getClienteId());
            ped.setEstado(pedido.getEstado());
            ped.setDescripcion(pedido.getDescripcion());
            ped.setTotal(pedido.getTotal());
            ped.setMedioPago(pedido.getMedioPago());

            pedidoService.save(ped);
            return ResponseEntity.ok(ped);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(summary = "Eliminar pedido", description = "Elimina un pedido existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido eliminado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            pedidoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener cliente desde pedido", description = "Obtiene los detalles de un cliente asociado a un pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/cliente/{id}")
    public ResponseEntity<ClienteDTO> obtenerClienteDesdePedido(@PathVariable Integer id) {
    ClienteDTO cliente = clienteClient.obtenerCliente(id);
    return ResponseEntity.ok(cliente);
    }

    @Operation(summary = "Listar pedidos por cliente", description = "Obtiene la lista de pedidos asociados a un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/cliente/{clienteId}/pedidos")
    public ResponseEntity<List<Pedido>> listarPedidosPorCliente(@PathVariable Integer clienteId) {
        List<Pedido> pedidos = pedidoService.findByClienteId(clienteId);
        return ResponseEntity.ok(pedidos);
    }
}
