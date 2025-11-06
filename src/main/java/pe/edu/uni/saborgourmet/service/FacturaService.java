package pe.edu.uni.saborgourmet.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.uni.saborgourmet.entity.*;
import pe.edu.uni.saborgourmet.repository.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FacturaService {
    
    private final FacturaRepository facturaRepository;
    private final PedidoRepository pedidoRepository;
    
    public FacturaService(FacturaRepository facturaRepository,
                         PedidoRepository pedidoRepository) {
        this.facturaRepository = facturaRepository;
        this.pedidoRepository = pedidoRepository;
    }
    
    public Factura generarFactura(Integer idPedido, Factura.MetodoPago metodoPago) {
        Pedido pedido = pedidoRepository.findById(idPedido)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        
        if (pedido.getEstado() != Pedido.EstadoPedido.CERRADO) {
            throw new RuntimeException("El pedido debe estar cerrado para generar la factura");
        }
        
        Factura factura = new Factura();
        factura.setPedido(pedido);
        factura.setMetodoPago(metodoPago);
        
        BigDecimal total = BigDecimal.ZERO;
        
        // Crear detalles de factura desde detalles del pedido
        for (DetallePedido detallePedido : pedido.getDetalles()) {
            DetalleFactura detalleFactura = new DetalleFactura();
            detalleFactura.setConcepto(detallePedido.getPlato().getNombre() + " x" + detallePedido.getCantidad());
            detalleFactura.setMonto(detallePedido.getSubtotal());
            factura.agregarDetalle(detalleFactura);
            total = total.add(detallePedido.getSubtotal());
        }
        
        factura.setTotal(total);
        return guardar(factura);
    }
    
    public Factura guardar(Factura factura) {
        return facturaRepository.save(factura);
    }
    
    public Factura actualizar(Factura factura) {
        return guardar(factura);
    }
    
    public void eliminar(Integer id) {
        facturaRepository.deleteById(id);
    }
    
    public List<Factura> listarTodos() {
        return facturaRepository.findAll();
    }
    
    public Optional<Factura> buscarPorId(Integer id) {
        return facturaRepository.findById(id);
    }
    
    public Factura marcarComoPagada(Integer idFactura) {
        Factura factura = facturaRepository.findById(idFactura)
            .orElseThrow(() -> new RuntimeException("Factura no encontrada"));
        factura.setEstado(Factura.EstadoFactura.PAGADO);
        return facturaRepository.save(factura);
    }
}

