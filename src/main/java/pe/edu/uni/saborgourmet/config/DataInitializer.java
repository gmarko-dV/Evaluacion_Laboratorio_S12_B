package pe.edu.uni.saborgourmet.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pe.edu.uni.saborgourmet.entity.Usuario;
import pe.edu.uni.saborgourmet.repository.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    
    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Crear usuario administrador por defecto si no existe
        if (usuarioRepository.findByNombreUsuario("admin").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombreUsuario("admin");
            admin.setContrasena(passwordEncoder.encode("admin123"));
            admin.setRol(Usuario.RolUsuario.ADMIN);
            admin.setEstado(Usuario.EstadoUsuario.ACTIVO);
            usuarioRepository.save(admin);
            System.out.println("Usuario administrador creado: admin / admin123");
        }
        
        // Crear usuario mozo por defecto
        if (usuarioRepository.findByNombreUsuario("mozo").isEmpty()) {
            Usuario mozo = new Usuario();
            mozo.setNombreUsuario("mozo");
            mozo.setContrasena(passwordEncoder.encode("mozo123"));
            mozo.setRol(Usuario.RolUsuario.MOZO);
            mozo.setEstado(Usuario.EstadoUsuario.ACTIVO);
            usuarioRepository.save(mozo);
            System.out.println("Usuario mozo creado: mozo / mozo123");
        }
        
        // Crear usuario cajero por defecto
        if (usuarioRepository.findByNombreUsuario("cajero").isEmpty()) {
            Usuario cajero = new Usuario();
            cajero.setNombreUsuario("cajero");
            cajero.setContrasena(passwordEncoder.encode("cajero123"));
            cajero.setRol(Usuario.RolUsuario.CAJERO);
            cajero.setEstado(Usuario.EstadoUsuario.ACTIVO);
            usuarioRepository.save(cajero);
            System.out.println("Usuario cajero creado: cajero / cajero123");
        }
        
        // Crear usuario cocinero por defecto
        if (usuarioRepository.findByNombreUsuario("cocinero").isEmpty()) {
            Usuario cocinero = new Usuario();
            cocinero.setNombreUsuario("cocinero");
            cocinero.setContrasena(passwordEncoder.encode("cocinero123"));
            cocinero.setRol(Usuario.RolUsuario.COCINERO);
            cocinero.setEstado(Usuario.EstadoUsuario.ACTIVO);
            usuarioRepository.save(cocinero);
            System.out.println("Usuario cocinero creado: cocinero / cocinero123");
        }
    }
}

