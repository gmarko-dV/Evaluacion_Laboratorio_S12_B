package pe.edu.uni.saborgourmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.uni.saborgourmet.entity.Mesa;

import java.util.List;
import java.util.Optional;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer> {
    Optional<Mesa> findByNumero(String numero);
    List<Mesa> findByEstado(Mesa.EstadoMesa estado);
    boolean existsByNumero(String numero);
}

