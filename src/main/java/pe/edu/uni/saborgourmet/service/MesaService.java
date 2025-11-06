package pe.edu.uni.saborgourmet.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.uni.saborgourmet.entity.Mesa;
import pe.edu.uni.saborgourmet.repository.MesaRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MesaService {
    
    private final MesaRepository mesaRepository;
    
    public MesaService(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }
    
    public Mesa guardar(Mesa mesa) {
        return mesaRepository.save(mesa);
    }
    
    public Mesa actualizar(Mesa mesa) {
        return guardar(mesa);
    }
    
    public void eliminar(Integer id) {
        mesaRepository.deleteById(id);
    }
    
    public List<Mesa> listarTodos() {
        return mesaRepository.findAll();
    }
    
    public Optional<Mesa> buscarPorId(Integer id) {
        return mesaRepository.findById(id);
    }
    
    public List<Mesa> buscarPorEstado(Mesa.EstadoMesa estado) {
        return mesaRepository.findByEstado(estado);
    }
    
    public List<Mesa> buscarDisponibles() {
        return buscarPorEstado(Mesa.EstadoMesa.DISPONIBLE);
    }
    
    public boolean existeNumero(String numero) {
        return mesaRepository.existsByNumero(numero);
    }
}

