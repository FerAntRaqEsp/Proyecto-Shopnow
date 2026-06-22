package com.shopnow.cl.shopnow.dto;

import lombok.Data;

@Data
public class PedidoDTO {
    private Integer clienteId;
    private String estado;
    private String descripcion;
    private Integer total;
    private String medioPago;
}
