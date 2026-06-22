package com.shopnow.cl.shopnow;

import com.shopnow.cl.shopnow.model.Pedido;
import com.shopnow.cl.shopnow.repository.PedidoRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private final PedidoRepository pedidoRepository;

    public DataLoader(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public void run(String... args) {

        Faker faker = new Faker();

        String[] estados = {"Pendiente", "Pagado", "Enviado", "Entregado"};
        String[] mediosPago = {"Debito", "Credito", "Transferencia", "Efectivo"};

        if (pedidoRepository.count() == 0) {

            for (int i = 1; i <= 100; i++) {
                Pedido pedido = new Pedido();

                pedido.setClienteId(i);
                pedido.setEstado(estados[i % estados.length]);
                pedido.setDescripcion("Pedido de prueba " + i);
                pedido.setTotal(faker.number().numberBetween(5000, 50000));
                pedido.setMedioPago(mediosPago[i % mediosPago.length]);

                pedidoRepository.save(pedido);
            }

            System.out.println("Pedidos de prueba creados correctamente.");
        }
    }
}