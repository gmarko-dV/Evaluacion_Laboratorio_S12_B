package pe.edu.uni.saborgourmet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "bitacora")
public class Bitacora {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBitacora")
    private Integer idBitacora;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    
    @Column(name = "accion", length = 500)
    @NotBlank(message = "La acción es obligatoria")
    private String accion;
    
    @Column(name = "fechaHora")
    @NotNull(message = "La fecha y hora son obligatorias")
    private LocalDateTime fechaHora;
    
    @Column(name = "tabla", length = 50)
    private String tabla;
    
    @Column(name = "idRegistro")
    private Integer idRegistro;
    
    @Column(name = "tipoAccion", length = 20)
    private String tipoAccion; // CREAR, ACTUALIZAR, ELIMINAR
    
    // Constructores
    public Bitacora() {
        this.fechaHora = LocalDateTime.now();
    }
    
    public Bitacora(Usuario usuario, String accion, String tabla, Integer idRegistro, String tipoAccion) {
        this.usuario = usuario;
        this.accion = accion;
        this.tabla = tabla;
        this.idRegistro = idRegistro;
        this.tipoAccion = tipoAccion;
        this.fechaHora = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Integer getIdBitacora() {
        return idBitacora;
    }
    
    public void setIdBitacora(Integer idBitacora) {
        this.idBitacora = idBitacora;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public String getAccion() {
        return accion;
    }
    
    public void setAccion(String accion) {
        this.accion = accion;
    }
    
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
    
    public String getTabla() {
        return tabla;
    }
    
    public void setTabla(String tabla) {
        this.tabla = tabla;
    }
    
    public Integer getIdRegistro() {
        return idRegistro;
    }
    
    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }
    
    public String getTipoAccion() {
        return tipoAccion;
    }
    
    public void setTipoAccion(String tipoAccion) {
        this.tipoAccion = tipoAccion;
    }
}

