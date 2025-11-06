package pe.edu.uni.saborgourmet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_factura")
public class DetalleFactura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDetalleFactura")
    private Integer idDetalleFactura;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idFactura", nullable = false)
    @NotNull(message = "La factura es obligatoria")
    private Factura factura;
    
    @Column(name = "concepto", length = 200)
    @NotBlank(message = "El concepto es obligatorio")
    private String concepto;
    
    @Column(name = "monto", precision = 10, scale = 2)
    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private BigDecimal monto;
    
    // Constructores
    public DetalleFactura() {
    }
    
    public DetalleFactura(String concepto, BigDecimal monto) {
        this.concepto = concepto;
        this.monto = monto;
    }
    
    // Getters y Setters
    public Integer getIdDetalleFactura() {
        return idDetalleFactura;
    }
    
    public void setIdDetalleFactura(Integer idDetalleFactura) {
        this.idDetalleFactura = idDetalleFactura;
    }
    
    public Factura getFactura() {
        return factura;
    }
    
    public void setFactura(Factura factura) {
        this.factura = factura;
    }
    
    public String getConcepto() {
        return concepto;
    }
    
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
    
    public BigDecimal getMonto() {
        return monto;
    }
    
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}

