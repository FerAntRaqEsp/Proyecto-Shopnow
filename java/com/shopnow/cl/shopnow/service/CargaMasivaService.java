package com.shopnow.cl.shopnow.service;

import com.shopnow.cl.shopnow.dto.PedidoDTO;
import com.shopnow.cl.shopnow.model.Pedido;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CargaMasivaService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void procesarCarga(List<PedidoDTO> listaDto) {
        int batchSize = 50;

        for (int i = 0; i < listaDto.size(); i++) {
            PedidoDTO dto = listaDto.get(i);

            Pedido pedido = new Pedido();
            pedido.setClienteId(dto.getClienteId());
            pedido.setEstado(dto.getEstado());
            pedido.setDescripcion(dto.getDescripcion());
            pedido.setTotal(dto.getTotal());
            pedido.setMedioPago(dto.getMedioPago());

            entityManager.persist(pedido);

            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }
}
