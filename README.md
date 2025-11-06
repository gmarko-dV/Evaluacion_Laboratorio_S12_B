# Sistema de Gestión de Restaurante "Sabor Gourmet"

Sistema desarrollado con Spring Boot 3.2.0, implementando AOP (Programación Orientada a Aspectos) para auditoría y Spring Security para la gestión de autenticación y autorización.

## Características Principales

- ✅ Spring Boot 3.2.0 con arquitectura MVC
- ✅ Spring Data JPA con MySQL
- ✅ Spring Security con roles (ADMIN, MOZO, COCINERO, CAJERO)
- ✅ AOP para auditoría automática de operaciones CRUD
- ✅ Thymeleaf para vistas
- ✅ Bootstrap 5 para diseño responsive

## Requisitos Previos

- Java 17 o superior
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

## Configuración de la Base de Datos

1. Crear la base de datos en MySQL:
```sql
CREATE DATABASE sabor_gourmet CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. Configurar las credenciales en `src/main/resources/application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=tu_password
```

## Instalación y Ejecución

1. Clonar o descargar el proyecto

2. Configurar la base de datos MySQL en `application.properties`

3. Ejecutar el proyecto:
```bash
mvn spring-boot:run
```

O desde el IDE ejecutar la clase `SaborGourmetApplication.java`

4. Acceder a la aplicación:
```
http://localhost:8080
```

## Usuarios por Defecto

El sistema crea automáticamente los siguientes usuarios al iniciar:

| Usuario   | Contraseña   | Rol      |
|-----------|--------------|----------|
| admin     | admin123     | ADMIN    |
| mozo      | mozo123      | MOZO     |
| cajero    | cajero123    | CAJERO   |
| cocinero  | cocinero123  | COCINERO |

## Estructura del Proyecto

```
src/main/java/pe/edu/uni/saborgourmet/
├── aspect/          # Aspectos AOP (Auditoría)
├── config/          # Configuraciones (Security, Data Initializer)
├── controller/      # Controladores MVC
├── entity/          # Entidades JPA
├── repository/      # Repositorios Spring Data JPA
└── service/         # Servicios de negocio

src/main/resources/
├── templates/       # Vistas Thymeleaf
└── application.properties
```

## Roles y Permisos

### ADMIN
- Acceso completo al sistema
- Gestión de clientes, mesas, platos, usuarios
- Acceso a inventario

### MOZO
- Gestión de pedidos
- Consulta de mesas

### COCINERO
- Visualización de pedidos en cocina
- Cambio de estado de pedidos

### CAJERO
- Gestión de ventas
- Generación de facturas
- Registro de pagos

## Módulos del Sistema

### 1. Módulo de Clientes y Mesas
- Registro y consulta de clientes
- Gestión de mesas (disponible, ocupada, reservada, mantenimiento)

### 2. Módulo de Menú y Platos
- Registro de platos y bebidas
- Asociación de insumos a platos
- Control de precios

### 3. Módulo de Pedidos
- Registro de pedidos
- Gestión de estados (pendiente, en preparación, servido, cerrado)
- Vista de cocina

### 4. Módulo de Ventas y Facturación
- Generación automática de facturas
- Registro de métodos de pago (efectivo, tarjeta, yape)
- Control de pagos

### 5. Módulo de Inventario
- Gestión de insumos
- Control de stock
- Alertas de stock bajo

### 6. Módulo de Administración y Seguridad
- Gestión de usuarios y roles
- Bitácora de auditoría (registro automático de acciones)

## Auditoría con AOP

El sistema registra automáticamente en la tabla `bitacora` todas las operaciones de:
- **CREAR**: Cuando se crea un nuevo registro
- **ACTUALIZAR**: Cuando se modifica un registro existente
- **ELIMINAR**: Cuando se elimina un registro

El aspecto `AuditoriaAspect` intercepta los métodos de los servicios y registra:
- Usuario que realizó la acción
- Tabla afectada
- ID del registro
- Tipo de acción
- Fecha y hora

## Tecnologías Utilizadas

- **Spring Boot 3.2.0**
- **Spring Security**: Autenticación y autorización
- **Spring Data JPA**: Persistencia de datos
- **Spring AOP**: Programación orientada a aspectos
- **MySQL**: Base de datos
- **Thymeleaf**: Motor de plantillas
- **Bootstrap 5**: Framework CSS
- **BCrypt**: Cifrado de contraseñas

## Desarrollo

### Compilar el proyecto
```bash
mvn clean install
```

### Ejecutar tests
```bash
mvn test
```

### Empaquetar para producción
```bash
mvn clean package
```

El archivo JAR se generará en `target/sabor-gourmet-1.0.0.jar`

### Ejecutar JAR
```bash
java -jar target/sabor-gourmet-1.0.0.jar
```

## Despliegue

1. Configurar variables de entorno o `application.properties` para producción
2. Actualizar credenciales de base de datos
3. Compilar el proyecto: `mvn clean package`
4. Ejecutar el JAR generado

## Notas Importantes

- Las contraseñas se almacenan cifradas con BCrypt
- Todas las acciones CRUD se registran automáticamente en la bitácora
- El sistema requiere autenticación para todas las rutas excepto `/login`
- Los roles determinan el acceso a las diferentes funcionalidades

## Autor

Desarrollado para el curso de Desarrollo de Aplicaciones Web - Ciclo IV

## Licencia

Este proyecto es de uso educativo.

