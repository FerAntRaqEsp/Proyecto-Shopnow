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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopnow.cl.shopnow.model.Cliente;
import com.shopnow.cl.shopnow.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Clientes", description = "Operaciones relacionadas con los clientes")
@RestController
@RequestMapping("/api/v1/clientes")

public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    
    @Operation(summary = "Listar clientes", description = "Obtiene una lista de todos los clientes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Clientes encontrados"),
            @ApiResponse(responseCode = "204", description = "No hay clientes para mostrar")
    })
    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        List<Cliente> clientes = clienteService.findAll();

        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(clientes);
    }
    
    
    
    @Operation(summary = "Guardar cliente", description = "Crea un nuevo cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente creado"),
            @ApiResponse(responseCode = "400", description = "Datos del cliente inválidos")
    })
    @PostMapping
    public ResponseEntity<Cliente> guardar(@RequestBody Cliente cliente) {
        Cliente clienteNuevo = clienteService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteNuevo);
    }

    
    @Operation(summary = "Buscar cliente", description = "Obtiene los detalles de un cliente por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscar(@PathVariable Integer id) {
        try {
            Cliente cliente = clienteService.findById(id);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    
    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente actualizado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Integer id, @RequestBody Cliente cliente) {
        try {
            Cliente cli = clienteService.findById(id);

            cli.setRun(cliente.getRun());
            cli.setNombre(cliente.getNombre());
            cli.setApellido(cliente.getApellido());
            cli.setFechaNac(cliente.getFechaNac());
            cli.setCorreo(cliente.getCorreo());

            clienteService.save(cli);
            return ResponseEntity.ok(cli);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    
    
    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente eliminado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(
        @PathVariable Integer id,
        @RequestHeader("Authorization") String token) {

    try {
        String resultado = clienteService.delete(id, token);

        if (resultado.equals("TIENE_PEDIDOS")) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("No se puede eliminar el cliente porque tiene pedidos asociados");
        }

        if (resultado.equals("ERROR_TOKEN_INVALIDO")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Token inválido o vencido");
        }

        if (resultado.equals("ERROR_SIN_PERMISO")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Token sin permisos para consultar pedidos");
        }

        if (resultado.equals("ERROR_PEDIDOS_APAGADO")) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("El microservicio pedidos está apagado");
        }

        if (resultado.equals("ELIMINADO")) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error inesperado al eliminar cliente");

    } catch (Exception e) {
        return ResponseEntity.notFound().build();
        }
    }
}
