package pe.edu.uni.saborgourmet.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.uni.saborgourmet.entity.Plato;
import pe.edu.uni.saborgourmet.repository.PlatoRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlatoService {
    
    private final PlatoRepository platoRepository;
    
    public PlatoService(PlatoRepository platoRepository) {
        this.platoRepository = platoRepository;
    }
    
    public Plato guardar(Plato plato) {
        return platoRepository.save(plato);
    }
    
    public Plato actualizar(Plato plato) {
        return guardar(plato);
    }
    
    public void eliminar(Integer id) {
        platoRepository.deleteById(id);
    }
    
    public List<Plato> listarTodos() {
        return platoRepository.findAll();
    }
    
    public Optional<Plato> buscarPorId(Integer id) {
        return platoRepository.findById(id);
    }
    
    public List<Plato> buscarActivos() {
        return platoRepository.findByEstado(Plato.EstadoPlato.ACTIVO);
    }
    
    public List<Plato> buscarPorTipo(String tipo) {
        return platoRepository.findByTipo(tipo);
    }
}

