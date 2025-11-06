package pe.edu.uni.saborgourmet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "insumo")
public class Insumo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idInsumo")
    private Integer idInsumo;
    
    @Column(name = "nombre", length = 100)
    @NotBlank(message = "El nombre del insumo es obligatorio")
    private String nombre;
    
    @Column(name = "unidadMedida", length = 20)
    @NotBlank(message = "La unidad de medida es obligatoria")
    private String unidadMedida; // kg, litros, unidades, etc.
    
    @Column(name = "stock")
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Double stock;
    
    @Column(name = "stockMinimo")
    @NotNull(message = "El stock mínimo es obligatorio")
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    private Double stockMinimo;
    
    @Column(name = "precioCompra", precision = 10, scale = 2)
    @NotNull(message = "El precio de compra es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio de compra debe ser mayor a 0")
    private BigDecimal precioCompra;
    
    @Column(name = "estado", length = 20)
    @Enumerated(EnumType.STRING)
    private EstadoInsumo estado;
    
    public enum EstadoInsumo {
        ACTIVO, INACTIVO
    }
    
    // Constructores
    public Insumo() {
        this.stock = 0.0;
        this.stockMinimo = 0.0;
        this.estado = EstadoInsumo.ACTIVO;
    }
    
    // Getters y Setters
    public Integer getIdInsumo() {
        return idInsumo;
    }
    
    public void setIdInsumo(Integer idInsumo) {
        this.idInsumo = idInsumo;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getUnidadMedida() {
        return unidadMedida;
    }
    
    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
    
    public Double getStock() {
        return stock;
    }
    
    public void setStock(Double stock) {
        this.stock = stock;
    }
    
    public Double getStockMinimo() {
        return stockMinimo;
    }
    
    public void setStockMinimo(Double stockMinimo) {
        this.stockMinimo = stockMinimo;
    }
    
    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }
    
    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }
    
    public EstadoInsumo getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoInsumo estado) {
        this.estado = estado;
    }
    
    public boolean tieneStockBajo() {
        return stock <= stockMinimo;
    }
}

