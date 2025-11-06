package pe.edu.uni.saborgourmet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plato")
public class Plato {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPlato")
    private Integer idPlato;
    
    @Column(name = "nombre", length = 100)
    @NotBlank(message = "El nombre del plato es obligatorio")
    private String nombre;
    
    @Column(name = "tipo", length = 50)
    @NotBlank(message = "El tipo del plato es obligatorio")
    private String tipo; // ENTRADA, FONDO, POSTRE, BEBIDA
    
    @Column(name = "precio", precision = 10, scale = 2)
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private BigDecimal precio;
    
    @Column(name = "descripcion", length = 500)
    private String descripcion;
    
    @Column(name = "estado", length = 20)
    @Enumerated(EnumType.STRING)
    private EstadoPlato estado;
    
    @OneToMany(mappedBy = "plato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlatoInsumo> platoInsumos = new ArrayList<>();
    
    public enum EstadoPlato {
        ACTIVO, INACTIVO
    }
    
    // Constructores
    public Plato() {
        this.estado = EstadoPlato.ACTIVO;
    }
    
    // Getters y Setters
    public Integer getIdPlato() {
        return idPlato;
    }
    
    public void setIdPlato(Integer idPlato) {
        this.idPlato = idPlato;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public BigDecimal getPrecio() {
        return precio;
    }
    
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public EstadoPlato getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoPlato estado) {
        this.estado = estado;
    }
    
    public List<PlatoInsumo> getPlatoInsumos() {
        return platoInsumos;
    }
    
    public void setPlatoInsumos(List<PlatoInsumo> platoInsumos) {
        this.platoInsumos = platoInsumos;
    }
}

