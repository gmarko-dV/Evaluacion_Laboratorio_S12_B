package pe.edu.uni.saborgourmet.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.uni.saborgourmet.entity.Cliente;
import pe.edu.uni.saborgourmet.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {
    
    private final ClienteRepository clienteRepository;
    
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
    public Cliente actualizar(Cliente cliente) {
        return guardar(cliente);
    }
    
    public void eliminar(Integer id) {
        clienteRepository.deleteById(id);
    }
    
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
    
    public Optional<Cliente> buscarPorId(Integer id) {
        return clienteRepository.findById(id);
    }
    
    public Optional<Cliente> buscarPorDni(String dni) {
        return clienteRepository.findByDni(dni);
    }
    
    public boolean existeDni(String dni) {
        return clienteRepository.existsByDni(dni);
    }
}

