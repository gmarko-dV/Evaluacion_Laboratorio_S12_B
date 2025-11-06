package pe.edu.uni.saborgourmet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Integer idUsuario;
    
    @Column(name = "nombreUsuario", length = 50, unique = true)
    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String nombreUsuario;
    
    @Column(name = "contrasena", length = 255)
    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;
    
    @Column(name = "rol", length = 20)
    @Enumerated(EnumType.STRING)
    private RolUsuario rol;
    
    @Column(name = "estado", length = 20)
    @Enumerated(EnumType.STRING)
    private EstadoUsuario estado;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Bitacora> bitacoras = new ArrayList<>();
    
    public enum RolUsuario {
        ADMIN, MOZO, COCINERO, CAJERO
    }
    
    public enum EstadoUsuario {
        ACTIVO, INACTIVO
    }
    
    // Constructores
    public Usuario() {
        this.estado = EstadoUsuario.ACTIVO;
    }
    
    public Usuario(String nombreUsuario, String contrasena, RolUsuario rol) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.estado = EstadoUsuario.ACTIVO;
    }
    
    // Getters y Setters
    public Integer getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    public String getContrasena() {
        return contrasena;
    }
    
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    public RolUsuario getRol() {
        return rol;
    }
    
    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }
    
    public EstadoUsuario getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }
    
    public List<Bitacora> getBitacoras() {
        return bitacoras;
    }
    
    public void setBitacoras(List<Bitacora> bitacoras) {
        this.bitacoras = bitacoras;
    }
}

