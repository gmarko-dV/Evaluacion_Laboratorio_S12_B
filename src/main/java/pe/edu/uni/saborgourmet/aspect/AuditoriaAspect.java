package pe.edu.uni.saborgourmet.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pe.edu.uni.saborgourmet.entity.*;
import pe.edu.uni.saborgourmet.repository.BitacoraRepository;
import pe.edu.uni.saborgourmet.repository.UsuarioRepository;

import java.time.LocalDateTime;

@Aspect
@Component
public class AuditoriaAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(AuditoriaAspect.class);
    
    @Autowired
    private BitacoraRepository bitacoraRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    // Pointcut para métodos de creación
    @Pointcut("execution(* pe.edu.uni.saborgourmet.service.*.guardar(..)) || " +
              "execution(* pe.edu.uni.saborgourmet.service.*.crear(..)) || " +
              "execution(* pe.edu.uni.saborgourmet.service.*.save(..))")
    public void operacionCrear() {}
    
    // Pointcut para métodos de actualización
    @Pointcut("execution(* pe.edu.uni.saborgourmet.service.*.actualizar(..)) || " +
              "execution(* pe.edu.uni.saborgourmet.service.*.update(..))")
    public void operacionActualizar() {}
    
    // Pointcut para métodos de eliminación
    @Pointcut("execution(* pe.edu.uni.saborgourmet.service.*.eliminar(..)) || " +
              "execution(* pe.edu.uni.saborgourmet.service.*.delete(..))")
    public void operacionEliminar() {}
    
    @AfterReturning(pointcut = "operacionCrear()", returning = "resultado")
    public void registrarCreacion(JoinPoint joinPoint, Object resultado) {
        try {
            if (resultado != null) {
                String tabla = obtenerNombreTabla(resultado);
                Integer idRegistro = obtenerIdRegistro(resultado);
                
                if (tabla != null && idRegistro != null) {
                    String accion = String.format("Creación de registro en %s con ID: %d", tabla, idRegistro);
                    registrarBitacora(accion, tabla, idRegistro, "CREAR");
                    logger.info("Auditoría: {}", accion);
                }
            }
        } catch (Exception e) {
            logger.error("Error al registrar auditoría de creación", e);
        }
    }
    
    @AfterReturning(pointcut = "operacionActualizar()", returning = "resultado")
    public void registrarActualizacion(JoinPoint joinPoint, Object resultado) {
        try {
            if (resultado != null) {
                String tabla = obtenerNombreTabla(resultado);
                Integer idRegistro = obtenerIdRegistro(resultado);
                
                if (tabla != null && idRegistro != null) {
                    String accion = String.format("Actualización de registro en %s con ID: %d", tabla, idRegistro);
                    registrarBitacora(accion, tabla, idRegistro, "ACTUALIZAR");
                    logger.info("Auditoría: {}", accion);
                }
            }
        } catch (Exception e) {
            logger.error("Error al registrar auditoría de actualización", e);
        }
    }
    
    @AfterReturning(pointcut = "operacionEliminar()")
    public void registrarEliminacion(JoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();
            if (args.length > 0 && args[0] instanceof Integer) {
                Integer idRegistro = (Integer) args[0];
                String tabla = obtenerNombreTablaDesdeMetodo(joinPoint.getSignature().getName());
                
                if (tabla != null && idRegistro != null) {
                    String accion = String.format("Eliminación de registro en %s con ID: %d", tabla, idRegistro);
                    registrarBitacora(accion, tabla, idRegistro, "ELIMINAR");
                    logger.info("Auditoría: {}", accion);
                }
            }
        } catch (Exception e) {
            logger.error("Error al registrar auditoría de eliminación", e);
        }
    }
    
    private void registrarBitacora(String accion, String tabla, Integer idRegistro, String tipoAccion) {
        try {
            Bitacora bitacora = new Bitacora();
            bitacora.setAccion(accion);
            bitacora.setTabla(tabla);
            bitacora.setIdRegistro(idRegistro);
            bitacora.setTipoAccion(tipoAccion);
            bitacora.setFechaHora(LocalDateTime.now());
            
            // Obtener usuario autenticado
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && 
                !authentication.getName().equals("anonymousUser")) {
                usuarioRepository.findByNombreUsuario(authentication.getName())
                    .ifPresent(bitacora::setUsuario);
            }
            
            bitacoraRepository.save(bitacora);
        } catch (Exception e) {
            logger.error("Error al guardar en bitácora", e);
        }
    }
    
    private String obtenerNombreTabla(Object entidad) {
        if (entidad instanceof Cliente) return "cliente";
        if (entidad instanceof Mesa) return "mesa";
        if (entidad instanceof Plato) return "plato";
        if (entidad instanceof Insumo) return "insumo";
        if (entidad instanceof Pedido) return "pedido";
        if (entidad instanceof Factura) return "factura";
        if (entidad instanceof Proveedor) return "proveedor";
        if (entidad instanceof Compra) return "compra";
        if (entidad instanceof Usuario) return "usuario";
        return null;
    }
    
    private Integer obtenerIdRegistro(Object entidad) {
        if (entidad instanceof Cliente) return ((Cliente) entidad).getIdCliente();
        if (entidad instanceof Mesa) return ((Mesa) entidad).getIdMesa();
        if (entidad instanceof Plato) return ((Plato) entidad).getIdPlato();
        if (entidad instanceof Insumo) return ((Insumo) entidad).getIdInsumo();
        if (entidad instanceof Pedido) return ((Pedido) entidad).getIdPedido();
        if (entidad instanceof Factura) return ((Factura) entidad).getIdFactura();
        if (entidad instanceof Proveedor) return ((Proveedor) entidad).getIdProveedor();
        if (entidad instanceof Compra) return ((Compra) entidad).getIdCompra();
        if (entidad instanceof Usuario) return ((Usuario) entidad).getIdUsuario();
        return null;
    }
    
    private String obtenerNombreTablaDesdeMetodo(String nombreMetodo) {
        String metodo = nombreMetodo.toLowerCase();
        if (metodo.contains("cliente")) return "cliente";
        if (metodo.contains("mesa")) return "mesa";
        if (metodo.contains("plato")) return "plato";
        if (metodo.contains("insumo")) return "insumo";
        if (metodo.contains("pedido")) return "pedido";
        if (metodo.contains("factura")) return "factura";
        if (metodo.contains("proveedor")) return "proveedor";
        if (metodo.contains("compra")) return "compra";
        if (metodo.contains("usuario")) return "usuario";
        return null;
    }
}

