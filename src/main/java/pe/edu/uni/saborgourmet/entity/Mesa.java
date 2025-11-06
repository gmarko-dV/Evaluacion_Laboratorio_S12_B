package pe.edu.uni.saborgourmet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "mesa")
public class Mesa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMesa")
    private Integer idMesa;
    
    @Column(name = "numero", unique = true)
    @NotBlank(message = "El número de mesa es obligatorio")
    private String numero;
    
    @Column(name = "capacidad")
    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    private Integer capacidad;
    
    @Column(name = "estado", length = 20)
    @Enumerated(EnumType.STRING)
    private EstadoMesa estado;
    
    public enum EstadoMesa {
        DISPONIBLE, OCUPADA, RESERVADA, MANTENIMIENTO
    }
    
    // Constructores
    public Mesa() {
        this.estado = EstadoMesa.DISPONIBLE;
    }
    
    public Mesa(String numero, Integer capacidad) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.estado = EstadoMesa.DISPONIBLE;
    }
    
    // Getters y Setters
    public Integer getIdMesa() {
        return idMesa;
    }
    
    public void setIdMesa(Integer idMesa) {
        this.idMesa = idMesa;
    }
    
    public String getNumero() {
        return numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public Integer getCapacidad() {
        return capacidad;
    }
    
    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }
    
    public EstadoMesa getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoMesa estado) {
        this.estado = estado;
    }
}

