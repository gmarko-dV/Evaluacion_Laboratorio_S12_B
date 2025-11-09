package pe.edu.uni.saborgourmet.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pe.edu.uni.saborgourmet.entity.*;
import pe.edu.uni.saborgourmet.repository.*;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final MesaRepository mesaRepository;
    private final PlatoRepository platoRepository;
    private final InsumoRepository insumoRepository;
    private final PlatoInsumoRepository platoInsumoRepository;
    private final ProveedorRepository proveedorRepository;
    private final PasswordEncoder passwordEncoder;
    
    public DataInitializer(UsuarioRepository usuarioRepository,
                          ClienteRepository clienteRepository,
                          MesaRepository mesaRepository,
                          PlatoRepository platoRepository,
                          InsumoRepository insumoRepository,
                          PlatoInsumoRepository platoInsumoRepository,
                          ProveedorRepository proveedorRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.mesaRepository = mesaRepository;
        this.platoRepository = platoRepository;
        this.insumoRepository = insumoRepository;
        this.platoInsumoRepository = platoInsumoRepository;
        this.proveedorRepository = proveedorRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Inicializando datos de prueba ===");
        
        inicializarUsuarios();
        inicializarClientes();
        inicializarMesas();
        inicializarInsumos();
        inicializarProveedores();
        inicializarPlatos();
        asociarPlatosInsumos();
        
        System.out.println("=== Datos de prueba inicializados correctamente ===");
    }
    
    private void inicializarUsuarios() {
        if (usuarioRepository.count() == 0) {
            // Usuario administrador
            Usuario admin = new Usuario();
            admin.setNombreUsuario("admin");
            admin.setContrasena(passwordEncoder.encode("admin123"));
            admin.setRol(Usuario.RolUsuario.ADMIN);
            admin.setEstado(Usuario.EstadoUsuario.ACTIVO);
            usuarioRepository.save(admin);
            System.out.println("✓ Usuario administrador creado: admin / admin123");
        
            // Usuario mozo
            Usuario mozo = new Usuario();
            mozo.setNombreUsuario("mozo");
            mozo.setContrasena(passwordEncoder.encode("mozo123"));
            mozo.setRol(Usuario.RolUsuario.MOZO);
            mozo.setEstado(Usuario.EstadoUsuario.ACTIVO);
            usuarioRepository.save(mozo);
            System.out.println("✓ Usuario mozo creado: mozo / mozo123");
        
            // Usuario cajero
            Usuario cajero = new Usuario();
            cajero.setNombreUsuario("cajero");
            cajero.setContrasena(passwordEncoder.encode("cajero123"));
            cajero.setRol(Usuario.RolUsuario.CAJERO);
            cajero.setEstado(Usuario.EstadoUsuario.ACTIVO);
            usuarioRepository.save(cajero);
            System.out.println("✓ Usuario cajero creado: cajero / cajero123");
        
            // Usuario cocinero
            Usuario cocinero = new Usuario();
            cocinero.setNombreUsuario("cocinero");
            cocinero.setContrasena(passwordEncoder.encode("cocinero123"));
            cocinero.setRol(Usuario.RolUsuario.COCINERO);
            cocinero.setEstado(Usuario.EstadoUsuario.ACTIVO);
            usuarioRepository.save(cocinero);
            System.out.println("✓ Usuario cocinero creado: cocinero / cocinero123");
        }
    }
    
    private void inicializarClientes() {
        if (clienteRepository.count() == 0) {
            Cliente cliente1 = new Cliente();
            cliente1.setDni("12345678");
            cliente1.setNombres("Juan");
            cliente1.setApellidos("Pérez García");
            cliente1.setTelefono("987654321");
            cliente1.setCorreo("juan.perez@email.com");
            cliente1.setEstado(Cliente.EstadoCliente.ACTIVO);
            clienteRepository.save(cliente1);
            
            Cliente cliente2 = new Cliente();
            cliente2.setDni("23456789");
            cliente2.setNombres("María");
            cliente2.setApellidos("González López");
            cliente2.setTelefono("987654322");
            cliente2.setCorreo("maria.gonzalez@email.com");
            cliente2.setEstado(Cliente.EstadoCliente.ACTIVO);
            clienteRepository.save(cliente2);
            
            Cliente cliente3 = new Cliente();
            cliente3.setDni("34567890");
            cliente3.setNombres("Carlos");
            cliente3.setApellidos("Rodríguez Martínez");
            cliente3.setTelefono("987654323");
            cliente3.setCorreo("carlos.rodriguez@email.com");
            cliente3.setEstado(Cliente.EstadoCliente.ACTIVO);
            clienteRepository.save(cliente3);
            
            Cliente cliente4 = new Cliente();
            cliente4.setDni("45678901");
            cliente4.setNombres("Ana");
            cliente4.setApellidos("Sánchez Fernández");
            cliente4.setTelefono("987654324");
            cliente4.setCorreo("ana.sanchez@email.com");
            cliente4.setEstado(Cliente.EstadoCliente.ACTIVO);
            clienteRepository.save(cliente4);
            
            Cliente cliente5 = new Cliente();
            cliente5.setDni("56789012");
            cliente5.setNombres("Luis");
            cliente5.setApellidos("Torres Díaz");
            cliente5.setTelefono("987654325");
            cliente5.setCorreo("luis.torres@email.com");
            cliente5.setEstado(Cliente.EstadoCliente.ACTIVO);
            clienteRepository.save(cliente5);
            
            System.out.println("✓ 5 clientes de prueba creados");
        }
    }
    
    private void inicializarMesas() {
        if (mesaRepository.count() == 0) {
            // Mesas para 2 personas
            for (int i = 1; i <= 5; i++) {
                Mesa mesa = new Mesa();
                mesa.setNumero("M" + String.format("%02d", i));
                mesa.setCapacidad(2);
                mesa.setEstado(Mesa.EstadoMesa.DISPONIBLE);
                mesaRepository.save(mesa);
            }
            
            // Mesas para 4 personas
            for (int i = 6; i <= 10; i++) {
                Mesa mesa = new Mesa();
                mesa.setNumero("M" + String.format("%02d", i));
                mesa.setCapacidad(4);
                mesa.setEstado(Mesa.EstadoMesa.DISPONIBLE);
                mesaRepository.save(mesa);
            }
            
            // Mesas para 6 personas
            for (int i = 11; i <= 12; i++) {
                Mesa mesa = new Mesa();
                mesa.setNumero("M" + String.format("%02d", i));
                mesa.setCapacidad(6);
                mesa.setEstado(Mesa.EstadoMesa.DISPONIBLE);
                mesaRepository.save(mesa);
            }
            
            // Mesa en mantenimiento
            Mesa mesaMantenimiento = new Mesa();
            mesaMantenimiento.setNumero("M13");
            mesaMantenimiento.setCapacidad(4);
            mesaMantenimiento.setEstado(Mesa.EstadoMesa.MANTENIMIENTO);
            mesaRepository.save(mesaMantenimiento);
            
            System.out.println("✓ 13 mesas de prueba creadas");
        }
    }
    
    private void inicializarInsumos() {
        if (insumoRepository.count() == 0) {
            // Carnes
            Insumo pollo = crearInsumo("Pollo", "kg", 50.0, 10.0, new BigDecimal("12.50"));
            insumoRepository.save(pollo);
            
            Insumo res = crearInsumo("Carne de Res", "kg", 30.0, 8.0, new BigDecimal("25.00"));
            insumoRepository.save(res);
            
            Insumo cerdo = crearInsumo("Cerdo", "kg", 25.0, 5.0, new BigDecimal("18.00"));
            insumoRepository.save(cerdo);
            
            // Verduras
            Insumo cebolla = crearInsumo("Cebolla", "kg", 100.0, 20.0, new BigDecimal("3.50"));
            insumoRepository.save(cebolla);
            
            Insumo tomate = crearInsumo("Tomate", "kg", 80.0, 15.0, new BigDecimal("4.00"));
            insumoRepository.save(tomate);
            
            Insumo papa = crearInsumo("Papa", "kg", 150.0, 30.0, new BigDecimal("2.50"));
            insumoRepository.save(papa);
            
            Insumo arroz = crearInsumo("Arroz", "kg", 200.0, 50.0, new BigDecimal("5.00"));
            insumoRepository.save(arroz);
            
            // Condimentos
            Insumo sal = crearInsumo("Sal", "kg", 50.0, 10.0, new BigDecimal("1.50"));
            insumoRepository.save(sal);
            
            Insumo aceite = crearInsumo("Aceite", "litros", 40.0, 10.0, new BigDecimal("8.00"));
            insumoRepository.save(aceite);
            
            Insumo ajo = crearInsumo("Ajo", "kg", 20.0, 5.0, new BigDecimal("15.00"));
            insumoRepository.save(ajo);
            
            // Bebidas
            Insumo cocaCola = crearInsumo("Coca Cola", "unidades", 100.0, 20.0, new BigDecimal("3.50"));
            insumoRepository.save(cocaCola);
            
            Insumo agua = crearInsumo("Agua Mineral", "unidades", 150.0, 30.0, new BigDecimal("1.50"));
            insumoRepository.save(agua);
            
            Insumo limon = crearInsumo("Limón", "kg", 30.0, 10.0, new BigDecimal("6.00"));
            insumoRepository.save(limon);
            
            // Insumo con stock bajo (para probar alertas)
            Insumo stockBajo = crearInsumo("Pimienta", "kg", 2.0, 5.0, new BigDecimal("20.00"));
            insumoRepository.save(stockBajo);
            
            System.out.println("✓ 14 insumos de prueba creados");
        }
    }
    
    private Insumo crearInsumo(String nombre, String unidad, Double stock, Double stockMinimo, BigDecimal precio) {
        Insumo insumo = new Insumo();
        insumo.setNombre(nombre);
        insumo.setUnidadMedida(unidad);
        insumo.setStock(stock);
        insumo.setStockMinimo(stockMinimo);
        insumo.setPrecioCompra(precio);
        insumo.setEstado(Insumo.EstadoInsumo.ACTIVO);
        return insumo;
    }
    
    private void inicializarProveedores() {
        if (proveedorRepository.count() == 0) {
            Proveedor proveedor1 = new Proveedor();
            proveedor1.setRuc("20123456789");
            proveedor1.setNombre("Distribuidora de Alimentos S.A.");
            proveedor1.setTelefono("01-2345678");
            proveedor1.setCorreo("contacto@distribuidora.com");
            proveedor1.setDireccion("Av. Principal 123, Lima");
            proveedorRepository.save(proveedor1);
            
            Proveedor proveedor2 = new Proveedor();
            proveedor2.setRuc("20987654321");
            proveedor2.setNombre("Carnes Premium S.A.C.");
            proveedor2.setTelefono("01-8765432");
            proveedor2.setCorreo("ventas@carnespremium.com");
            proveedor2.setDireccion("Jr. Los Olivos 456, Lima");
            proveedorRepository.save(proveedor2);
            
            Proveedor proveedor3 = new Proveedor();
            proveedor3.setRuc("20555123456");
            proveedor3.setNombre("Bebidas y Refrescos E.I.R.L.");
            proveedor3.setTelefono("01-5551234");
            proveedor3.setCorreo("info@bebidas.com");
            proveedor3.setDireccion("Av. Industrial 789, Lima");
            proveedorRepository.save(proveedor3);
            
            System.out.println("✓ 3 proveedores de prueba creados");
        }
    }
    
    private void inicializarPlatos() {
        if (platoRepository.count() == 0) {
            // ENTRADAS
            Plato ceviche = crearPlato("Ceviche de Pescado", "ENTRADA", 
                new BigDecimal("28.00"), "Fresco ceviche de pescado con cebolla, ají y limón");
            platoRepository.save(ceviche);
            
            Plato causa = crearPlato("Causa Limeña", "ENTRADA", 
                new BigDecimal("18.00"), "Causa rellena con pollo, palta y mayonesa");
            platoRepository.save(causa);
            
            Plato tequeños = crearPlato("Tequeños", "ENTRADA", 
                new BigDecimal("15.00"), "Tequeños de queso con salsa de ají");
            platoRepository.save(tequeños);
            
            // FONDOS
            Plato lomoSaltado = crearPlato("Lomo Saltado", "FONDO", 
                new BigDecimal("35.00"), "Lomo saltado con papas fritas y arroz");
            platoRepository.save(lomoSaltado);
            
            Plato arrozConPollo = crearPlato("Arroz con Pollo", "FONDO", 
                new BigDecimal("25.00"), "Arroz con pollo estilo peruano");
            platoRepository.save(arrozConPollo);
            
            Plato ajíDeGallina = crearPlato("Ají de Gallina", "FONDO", 
                new BigDecimal("28.00"), "Ají de gallina con arroz y papas");
            platoRepository.save(ajíDeGallina);
            
            Plato polloAlaBrasa = crearPlato("Pollo a la Brasa", "FONDO", 
                new BigDecimal("32.00"), "1/4 de pollo a la brasa con papas y ensalada");
            platoRepository.save(polloAlaBrasa);
            
            Plato tallarines = crearPlato("Tallarines Verdes", "FONDO", 
                new BigDecimal("22.00"), "Tallarines con salsa verde y carne");
            platoRepository.save(tallarines);
            
            // POSTRES
            Plato mazamorra = crearPlato("Mazamorra Morada", "POSTRE", 
                new BigDecimal("8.00"), "Mazamorra morada con arroz con leche");
            platoRepository.save(mazamorra);
            
            Plato suspiro = crearPlato("Suspiro Limeño", "POSTRE", 
                new BigDecimal("12.00"), "Suspiro limeño tradicional");
            platoRepository.save(suspiro);
            
            Plato flan = crearPlato("Flan", "POSTRE", 
                new BigDecimal("10.00"), "Flan casero con caramelo");
            platoRepository.save(flan);
            
            // BEBIDAS
            Plato chicha = crearPlato("Chicha Morada", "BEBIDA", 
                new BigDecimal("6.00"), "Chicha morada natural");
            platoRepository.save(chicha);
            
            Plato limonada = crearPlato("Limonada", "BEBIDA", 
                new BigDecimal("5.00"), "Limonada fresca natural");
            platoRepository.save(limonada);
            
            Plato gaseosa = crearPlato("Gaseosa", "BEBIDA", 
                new BigDecimal("4.00"), "Gaseosa 500ml");
            platoRepository.save(gaseosa);
            
            Plato agua = crearPlato("Agua Mineral", "BEBIDA", 
                new BigDecimal("3.00"), "Agua mineral 500ml");
            platoRepository.save(agua);
            
            System.out.println("✓ 15 platos de prueba creados");
        }
    }
    
    private Plato crearPlato(String nombre, String tipo, BigDecimal precio, String descripcion) {
        Plato plato = new Plato();
        plato.setNombre(nombre);
        plato.setTipo(tipo);
        plato.setPrecio(precio);
        plato.setDescripcion(descripcion);
        plato.setEstado(Plato.EstadoPlato.ACTIVO);
        return plato;
    }
    
    private void asociarPlatosInsumos() {
        if (platoInsumoRepository.count() == 0) {
            List<Plato> platos = platoRepository.findAll();
            List<Insumo> insumos = insumoRepository.findAll();
            
            // Buscar insumos específicos
            Insumo pollo = insumos.stream().filter(i -> i.getNombre().equals("Pollo")).findFirst().orElse(null);
            Insumo res = insumos.stream().filter(i -> i.getNombre().equals("Carne de Res")).findFirst().orElse(null);
            Insumo cebolla = insumos.stream().filter(i -> i.getNombre().equals("Cebolla")).findFirst().orElse(null);
            Insumo papa = insumos.stream().filter(i -> i.getNombre().equals("Papa")).findFirst().orElse(null);
            Insumo arroz = insumos.stream().filter(i -> i.getNombre().equals("Arroz")).findFirst().orElse(null);
            Insumo aceite = insumos.stream().filter(i -> i.getNombre().equals("Aceite")).findFirst().orElse(null);
            Insumo ajo = insumos.stream().filter(i -> i.getNombre().equals("Ajo")).findFirst().orElse(null);
            Insumo sal = insumos.stream().filter(i -> i.getNombre().equals("Sal")).findFirst().orElse(null);
            Insumo limon = insumos.stream().filter(i -> i.getNombre().equals("Limón")).findFirst().orElse(null);
            Insumo cocaCola = insumos.stream().filter(i -> i.getNombre().equals("Coca Cola")).findFirst().orElse(null);
            Insumo agua = insumos.stream().filter(i -> i.getNombre().equals("Agua Mineral")).findFirst().orElse(null);
            
            // Asociar Lomo Saltado
            Plato lomoSaltado = platos.stream().filter(p -> p.getNombre().equals("Lomo Saltado")).findFirst().orElse(null);
            if (lomoSaltado != null && res != null && cebolla != null && papa != null && arroz != null && aceite != null && ajo != null && sal != null) {
                crearPlatoInsumo(lomoSaltado, res, 0.3);
                crearPlatoInsumo(lomoSaltado, cebolla, 0.1);
                crearPlatoInsumo(lomoSaltado, papa, 0.2);
                crearPlatoInsumo(lomoSaltado, arroz, 0.15);
                crearPlatoInsumo(lomoSaltado, aceite, 0.05);
                crearPlatoInsumo(lomoSaltado, ajo, 0.01);
                crearPlatoInsumo(lomoSaltado, sal, 0.01);
            }
            
            // Asociar Arroz con Pollo
            Plato arrozConPollo = platos.stream().filter(p -> p.getNombre().equals("Arroz con Pollo")).findFirst().orElse(null);
            if (arrozConPollo != null && pollo != null && arroz != null && cebolla != null && ajo != null && aceite != null && sal != null) {
                crearPlatoInsumo(arrozConPollo, pollo, 0.25);
                crearPlatoInsumo(arrozConPollo, arroz, 0.2);
                crearPlatoInsumo(arrozConPollo, cebolla, 0.08);
                crearPlatoInsumo(arrozConPollo, ajo, 0.01);
                crearPlatoInsumo(arrozConPollo, aceite, 0.03);
                crearPlatoInsumo(arrozConPollo, sal, 0.01);
            }
            
            // Asociar Ají de Gallina
            Plato ajíDeGallina = platos.stream().filter(p -> p.getNombre().equals("Ají de Gallina")).findFirst().orElse(null);
            if (ajíDeGallina != null && pollo != null && arroz != null && papa != null && ajo != null && aceite != null && sal != null) {
                crearPlatoInsumo(ajíDeGallina, pollo, 0.2);
                crearPlatoInsumo(ajíDeGallina, arroz, 0.15);
                crearPlatoInsumo(ajíDeGallina, papa, 0.15);
                crearPlatoInsumo(ajíDeGallina, ajo, 0.01);
                crearPlatoInsumo(ajíDeGallina, aceite, 0.02);
                crearPlatoInsumo(ajíDeGallina, sal, 0.01);
            }
            
            // Asociar Pollo a la Brasa
            Plato polloAlaBrasa = platos.stream().filter(p -> p.getNombre().equals("Pollo a la Brasa")).findFirst().orElse(null);
            if (polloAlaBrasa != null && pollo != null && papa != null && ajo != null && sal != null) {
                crearPlatoInsumo(polloAlaBrasa, pollo, 0.3);
                crearPlatoInsumo(polloAlaBrasa, papa, 0.2);
                crearPlatoInsumo(polloAlaBrasa, ajo, 0.02);
                crearPlatoInsumo(polloAlaBrasa, sal, 0.01);
            }
            
            // Asociar Limonada
            Plato limonada = platos.stream().filter(p -> p.getNombre().equals("Limonada")).findFirst().orElse(null);
            if (limonada != null && limon != null && agua != null && sal != null) {
                crearPlatoInsumo(limonada, limon, 0.1);
                crearPlatoInsumo(limonada, agua, 1.0);
                crearPlatoInsumo(limonada, sal, 0.01);
            }
            
            // Asociar Gaseosa
            Plato gaseosa = platos.stream().filter(p -> p.getNombre().equals("Gaseosa")).findFirst().orElse(null);
            if (gaseosa != null && cocaCola != null) {
                crearPlatoInsumo(gaseosa, cocaCola, 1.0);
            }
            
            // Asociar Agua Mineral
            Plato aguaPlato = platos.stream().filter(p -> p.getNombre().equals("Agua Mineral")).findFirst().orElse(null);
            if (aguaPlato != null && agua != null) {
                crearPlatoInsumo(aguaPlato, agua, 1.0);
            }
            
            System.out.println("✓ Asociaciones plato-insumo creadas");
        }
    }
    
    private void crearPlatoInsumo(Plato plato, Insumo insumo, Double cantidad) {
        PlatoInsumo platoInsumo = new PlatoInsumo();
        platoInsumo.setPlato(plato);
        platoInsumo.setInsumo(insumo);
        platoInsumo.setCantidadUsada(cantidad);
        platoInsumoRepository.save(platoInsumo);
    }
}

