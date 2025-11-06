package pe.edu.uni.saborgourmet.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.uni.saborgourmet.entity.Usuario;
import pe.edu.uni.saborgourmet.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public Usuario guardar(Usuario usuario) {
        if (usuario.getIdUsuario() == null) {
            // Nuevo usuario - cifrar contraseña
            if (usuario.getContrasena() != null && !usuario.getContrasena().isEmpty()) {
                usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
            }
        } else {
            // Actualizar usuario - solo cifrar si la contraseña cambió
            Optional<Usuario> usuarioExistente = usuarioRepository.findById(usuario.getIdUsuario());
            if (usuarioExistente.isPresent()) {
                Usuario existente = usuarioExistente.get();
                if (!usuario.getContrasena().equals(existente.getContrasena()) && 
                    !usuario.getContrasena().startsWith("$2a$")) {
                    usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
                } else {
                    usuario.setContrasena(existente.getContrasena());
                }
            }
        }
        return usuarioRepository.save(usuario);
    }
    
    public Usuario actualizar(Usuario usuario) {
        return guardar(usuario);
    }
    
    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);
    }
    
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
    
    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }
    
    public boolean existeNombreUsuario(String nombreUsuario) {
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }
}

