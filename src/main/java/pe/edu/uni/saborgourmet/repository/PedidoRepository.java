package pe.edu.uni.saborgourmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.uni.saborgourmet.entity.Pedido;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByEstado(Pedido.EstadoPedido estado);
    List<Pedido> findByMesaIdMesa(Integer idMesa);
    List<Pedido> findByClienteIdCliente(Integer idCliente);
}

