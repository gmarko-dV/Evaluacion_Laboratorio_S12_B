package pe.edu.uni.saborgourmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.uni.saborgourmet.entity.Plato;

import java.util.List;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Integer> {
    List<Plato> findByEstado(Plato.EstadoPlato estado);
    List<Plato> findByTipo(String tipo);
}

