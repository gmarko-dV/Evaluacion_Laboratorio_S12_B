package pe.edu.uni.saborgourmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.uni.saborgourmet.entity.PlatoInsumo;

import java.util.List;

@Repository
public interface PlatoInsumoRepository extends JpaRepository<PlatoInsumo, Integer> {
    List<PlatoInsumo> findByPlatoIdPlato(Integer idPlato);
}

