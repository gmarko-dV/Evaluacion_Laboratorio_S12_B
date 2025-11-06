package pe.edu.uni.saborgourmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.uni.saborgourmet.entity.DetalleFactura;

import java.util.List;

@Repository
public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Integer> {
    List<DetalleFactura> findByFacturaIdFactura(Integer idFactura);
}

