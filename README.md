# Proyecto-Shopnow
Trabajo subido a esta plataforma

- Descripción del contexto o dominio del proyecto.
ShopNow es una plataforma de comercio electrónico basada en microservicios

- Nombres de los/las estudiantes.

- Listado de microservicios implementados.
  - API Gateway
 - Servicio Usuarios
 - Servicio Productos
 - Servicio Pedidos
 - 
- Rutas principales del Gateway (cuando aplique).
| Microservicio | Ruta |
|--------------|-------|
| Usuarios | `/api/usuarios/**` |
| Productos | `/api/productos/**` |
| Pedidos | `/api/pedidos/**` |

- Enlaces a la documentación Swagger (local o remota).
```text
Proyecto-ShopNow/
│
├── api-gateway/
├── servicio-usuarios/
├── servicio-productos/
├── servicio-pedidos/
└── README.md
```

- Instrucciones básicas de ejecución local y remota
- Usuarios: http://localhost:8081/swagger-ui/index.html
- Productos: http://localhost:8082/swagger-ui/index.html

---

## Ejecución Local

```bash
mvn clean install
mvn spring-boot:run
```
