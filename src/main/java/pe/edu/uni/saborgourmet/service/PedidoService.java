package pe.edu.uni.saborgourmet.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.uni.saborgourmet.entity.*;
import pe.edu.uni.saborgourmet.repository.*;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PedidoService {
    
    private final PedidoRepository pedidoRepository;
    private final InsumoRepository insumoRepository;
    private final PlatoInsumoRepository platoInsumoRepository;
    
    public PedidoService(PedidoRepository pedidoRepository,
                        InsumoRepository insumoRepository,
                        PlatoInsumoRepository platoInsumoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.insumoRepository = insumoRepository;
        this.platoInsumoRepository = platoInsumoRepository;
    }
    
    public Pedido guardar(Pedido pedido) {
        // Calcular subtotales de detalles
        for (DetallePedido detalle : pedido.getDetalles()) {
            detalle.calcularSubtotal();
        }
        return pedidoRepository.save(pedido);
    }
    
    public Pedido actualizar(Pedido pedido) {
        return guardar(pedido);
    }
    
    public void eliminar(Integer id) {
        pedidoRepository.deleteById(id);
    }
    
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }
    
    public Optional<Pedido> buscarPorId(Integer id) {
        return pedidoRepository.findById(id);
    }
    
    public List<Pedido> buscarPorEstado(Pedido.EstadoPedido estado) {
        return pedidoRepository.findByEstado(estado);
    }
    
    public Pedido cambiarEstado(Integer idPedido, Pedido.EstadoPedido nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(idPedido)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        
        pedido.setEstado(nuevoEstado);
        
        // Si se cierra el pedido, actualizar stock de insumos
        if (nuevoEstado == Pedido.EstadoPedido.CERRADO) {
            actualizarStockInsumos(pedido);
        }
        
        return pedidoRepository.save(pedido);
    }
    
    private void actualizarStockInsumos(Pedido pedido) {
        for (DetallePedido detalle : pedido.getDetalles()) {
            Plato plato = detalle.getPlato();
            List<PlatoInsumo> platoInsumos = platoInsumoRepository.findByPlatoIdPlato(plato.getIdPlato());
            
            for (PlatoInsumo platoInsumo : platoInsumos) {
                Insumo insumo = platoInsumo.getInsumo();
                Double cantidadUsada = platoInsumo.getCantidadUsada() * detalle.getCantidad();
                insumo.setStock(insumo.getStock() - cantidadUsada);
                insumoRepository.save(insumo);
            }
        }
    }
}

