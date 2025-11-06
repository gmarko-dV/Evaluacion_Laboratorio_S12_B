package pe.edu.uni.saborgourmet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cliente")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCliente")
    private Integer idCliente;
    
    @Column(name = "dni", length = 8, unique = true)
    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 8, max = 8, message = "El DNI debe tener 8 dígitos")
    private String dni;
    
    @Column(name = "nombres", length = 100)
    @NotBlank(message = "Los nombres son obligatorios")
    private String nombres;
    
    @Column(name = "apellidos", length = 100)
    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;
    
    @Column(name = "telefono", length = 15)
    private String telefono;
    
    @Column(name = "correo", length = 100)
    @Email(message = "El correo debe ser válido")
    private String correo;
    
    @Column(name = "estado", length = 20)
    @Enumerated(EnumType.STRING)
    private EstadoCliente estado;
    
    public enum EstadoCliente {
        ACTIVO, INACTIVO
    }
    
    // Constructores
    public Cliente() {
        this.estado = EstadoCliente.ACTIVO;
    }
    
    public Cliente(String dni, String nombres, String apellidos) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.estado = EstadoCliente.ACTIVO;
    }
    
    // Getters y Setters
    public Integer getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }
    
    public String getDni() {
        return dni;
    }
    
    public void setDni(String dni) {
        this.dni = dni;
    }
    
    public String getNombres() {
        return nombres;
    }
    
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    
    public String getApellidos() {
        return apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public EstadoCliente getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoCliente estado) {
        this.estado = estado;
    }
    
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}

