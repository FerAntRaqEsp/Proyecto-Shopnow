package com.shopnow.cl.shopnow.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
public class PedidoClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public String validarClienteSinPedidos(Integer clienteId, String token) {
        String url = "http://localhost:8081/api/v1/pedidos/cliente/" + clienteId + "/pedidos";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Object[]> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity, Object[].class);

            if (response.getBody() != null && response.getBody().length > 0) {
                return "TIENE_PEDIDOS";
            }

            return "SIN_PEDIDOS";

        } catch (HttpClientErrorException.Unauthorized e) {
            return "ERROR_TOKEN_INVALIDO";

        } catch (HttpClientErrorException.Forbidden e) {
            return "ERROR_SIN_PERMISO";

        } catch (ResourceAccessException e) {
            return "ERROR_PEDIDOS_APAGADO";

        } catch (Exception e) {
            return "ERROR_DESCONOCIDO";
        }
    }
}