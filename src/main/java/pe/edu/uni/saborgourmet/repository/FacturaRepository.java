package pe.edu.uni.saborgourmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.uni.saborgourmet.entity.Factura;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer> {
    List<Factura> findByEstado(Factura.EstadoFactura estado);
    List<Factura> findByFechaEmisionBetween(LocalDateTime inicio, LocalDateTime fin);
    Factura findByPedidoIdPedido(Integer idPedido);
}

