package pe.edu.uni.saborgourmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.uni.saborgourmet.entity.Compra;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {
    List<Compra> findByFechaCompraBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Compra> findByProveedorIdProveedor(Integer idProveedor);
}

