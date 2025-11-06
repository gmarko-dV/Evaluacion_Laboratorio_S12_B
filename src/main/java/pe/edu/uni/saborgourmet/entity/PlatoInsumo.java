package pe.edu.uni.saborgourmet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "plato_insumo")
public class PlatoInsumo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPlatoInsumo")
    private Integer idPlatoInsumo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPlato", nullable = false)
    @NotNull(message = "El plato es obligatorio")
    private Plato plato;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idInsumo", nullable = false)
    @NotNull(message = "El insumo es obligatorio")
    private Insumo insumo;
    
    @Column(name = "cantidadUsada")
    @NotNull(message = "La cantidad usada es obligatoria")
    @DecimalMin(value = "0.01", message = "La cantidad debe ser mayor a 0")
    private Double cantidadUsada;
    
    // Constructores
    public PlatoInsumo() {
    }
    
    public PlatoInsumo(Plato plato, Insumo insumo, Double cantidadUsada) {
        this.plato = plato;
        this.insumo = insumo;
        this.cantidadUsada = cantidadUsada;
    }
    
    // Getters y Setters
    public Integer getIdPlatoInsumo() {
        return idPlatoInsumo;
    }
    
    public void setIdPlatoInsumo(Integer idPlatoInsumo) {
        this.idPlatoInsumo = idPlatoInsumo;
    }
    
    public Plato getPlato() {
        return plato;
    }
    
    public void setPlato(Plato plato) {
        this.plato = plato;
    }
    
    public Insumo getInsumo() {
        return insumo;
    }
    
    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
    }
    
    public Double getCantidadUsada() {
        return cantidadUsada;
    }
    
    public void setCantidadUsada(Double cantidadUsada) {
        this.cantidadUsada = cantidadUsada;
    }
}

