package pe.edu.uni.saborgourmet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "factura")
public class Factura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFactura")
    private Integer idFactura;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPedido", nullable = false)
    @NotNull(message = "El pedido es obligatorio")
    private Pedido pedido;
    
    @Column(name = "fechaEmision")
    @NotNull(message = "La fecha de emisión es obligatoria")
    private LocalDateTime fechaEmision;
    
    @Column(name = "total", precision = 10, scale = 2)
    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.01", message = "El total debe ser mayor a 0")
    private BigDecimal total;
    
    @Column(name = "metodoPago", length = 20)
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;
    
    @Column(name = "estado", length = 20)
    @Enumerated(EnumType.STRING)
    private EstadoFactura estado;
    
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleFactura> detalles = new ArrayList<>();
    
    public enum MetodoPago {
        EFECTIVO, TARJETA, YAPE
    }
    
    public enum EstadoFactura {
        PENDIENTE, PAGADO
    }
    
    // Constructores
    public Factura() {
        this.fechaEmision = LocalDateTime.now();
        this.estado = EstadoFactura.PENDIENTE;
    }
    
    // Getters y Setters
    public Integer getIdFactura() {
        return idFactura;
    }
    
    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }
    
    public Pedido getPedido() {
        return pedido;
    }
    
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    
    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }
    
    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }
    
    public BigDecimal getTotal() {
        return total;
    }
    
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    public MetodoPago getMetodoPago() {
        return metodoPago;
    }
    
    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }
    
    public EstadoFactura getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoFactura estado) {
        this.estado = estado;
    }
    
    public List<DetalleFactura> getDetalles() {
        return detalles;
    }
    
    public void setDetalles(List<DetalleFactura> detalles) {
        this.detalles = detalles;
    }
    
    public void agregarDetalle(DetalleFactura detalle) {
        detalles.add(detalle);
        detalle.setFactura(this);
    }
}

