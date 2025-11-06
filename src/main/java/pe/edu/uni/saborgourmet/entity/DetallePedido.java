package pe.edu.uni.saborgourmet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDetallePedido")
    private Integer idDetallePedido;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPedido", nullable = false)
    @NotNull(message = "El pedido es obligatorio")
    private Pedido pedido;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPlato", nullable = false)
    @NotNull(message = "El plato es obligatorio")
    private Plato plato;
    
    @Column(name = "cantidad")
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;
    
    @Column(name = "subtotal", precision = 10, scale = 2)
    @NotNull(message = "El subtotal es obligatorio")
    @DecimalMin(value = "0.01", message = "El subtotal debe ser mayor a 0")
    private BigDecimal subtotal;
    
    // Constructores
    public DetallePedido() {
    }
    
    public DetallePedido(Plato plato, Integer cantidad) {
        this.plato = plato;
        this.cantidad = cantidad;
        calcularSubtotal();
    }
    
    // Métodos
    public void calcularSubtotal() {
        if (plato != null && cantidad != null && plato.getPrecio() != null) {
            this.subtotal = plato.getPrecio().multiply(BigDecimal.valueOf(cantidad));
        }
    }
    
    // Getters y Setters
    public Integer getIdDetallePedido() {
        return idDetallePedido;
    }
    
    public void setIdDetallePedido(Integer idDetallePedido) {
        this.idDetallePedido = idDetallePedido;
    }
    
    public Pedido getPedido() {
        return pedido;
    }
    
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    
    public Plato getPlato() {
        return plato;
    }
    
    public void setPlato(Plato plato) {
        this.plato = plato;
        calcularSubtotal();
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }
    
    public BigDecimal getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}

