package com.shopnow.cl.shopnow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopnow.cl.shopnow.dto.PedidoDTO;
import com.shopnow.cl.shopnow.service.CargaMasivaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/pedidos")
public class CargaController {

    @Autowired
    private CargaMasivaService service;

    @Operation(summary = "Carga masiva de pedidos", description = "Permite cargar una lista de pedidos en el sistema")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "Carga exitosa"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/masivo")
    public ResponseEntity<?> cargar(@RequestBody List<PedidoDTO> pedidos) {
        try {
            if (pedidos == null || pedidos.isEmpty()) {
                return ResponseEntity.badRequest().body("La lista está vacía");
            }

            long inicio = System.currentTimeMillis();
            service.procesarCarga(pedidos);
            long fin = System.currentTimeMillis();

            return ResponseEntity.ok("Éxito: " + pedidos.size() + " procesados en " + (fin - inicio) + "ms");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en la carga: " + e.getMessage());
        }
    }
}
