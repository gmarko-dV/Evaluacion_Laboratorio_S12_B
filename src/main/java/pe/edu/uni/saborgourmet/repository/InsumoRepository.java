package pe.edu.uni.saborgourmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.uni.saborgourmet.entity.Insumo;

import java.util.List;

@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Integer> {
    List<Insumo> findByEstado(Insumo.EstadoInsumo estado);
    
    @Query("SELECT i FROM Insumo i WHERE i.stock <= i.stockMinimo AND i.estado = 'ACTIVO'")
    List<Insumo> findInsumosStockBajo();
}

