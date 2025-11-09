package pe.edu.uni.saborgourmet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.edu.uni.saborgourmet.entity.DetallePedido;
import pe.edu.uni.saborgourmet.entity.Pedido;
import pe.edu.uni.saborgourmet.service.*;

import java.util.Optional;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {
    
    private final PedidoService pedidoService;
    private final MesaService mesaService;
    private final ClienteService clienteService;
    private final PlatoService platoService;
    
    public PedidoController(PedidoService pedidoService,
                           MesaService mesaService,
                           ClienteService clienteService,
                           PlatoService platoService) {
        this.pedidoService = pedidoService;
        this.mesaService = mesaService;
        this.clienteService = clienteService;
        this.platoService = platoService;
    }
    
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pedidos", pedidoService.listarTodos());
        return "pedidos/listar";
    }
    
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("mesas", mesaService.buscarDisponibles());
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("platos", platoService.buscarActivos());
        return "pedidos/formulario";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Optional<Pedido> pedido = pedidoService.buscarPorId(id);
        if (pedido.isPresent()) {
            model.addAttribute("pedido", pedido.get());
            model.addAttribute("mesas", mesaService.listarTodos());
            model.addAttribute("clientes", clienteService.listarTodos());
            model.addAttribute("platos", platoService.buscarActivos());
            return "pedidos/formulario";
        }
        return "redirect:/pedidos";
    }
    
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Pedido pedido,
                         @RequestParam(required = false) Integer idMesa,
                         @RequestParam(required = false) Integer idCliente,
                         @RequestParam(required = false) Integer[] platoIds,
                         @RequestParam(required = false) Integer[] cantidades,
                         RedirectAttributes redirectAttributes) {
        try {
            // Validar que se haya seleccionado una mesa
            if (idMesa == null) {
                redirectAttributes.addFlashAttribute("error", "Debe seleccionar una mesa");
                return "redirect:/pedidos/nuevo";
            }
            
            // Buscar y asignar la mesa
            mesaService.buscarPorId(idMesa).ifPresentOrElse(
                pedido::setMesa,
                () -> {
                    throw new RuntimeException("Mesa no encontrada");
                }
            );
            
            // Buscar y asignar el cliente (opcional)
            if (idCliente != null && idCliente > 0) {
                clienteService.buscarPorId(idCliente).ifPresent(pedido::setCliente);
            } else {
                pedido.setCliente(null);
            }
            
            // Inicializar fecha y estado si es un pedido nuevo
            if (pedido.getIdPedido() == null) {
                pedido.setFechaHora(java.time.LocalDateTime.now());
                pedido.setEstado(Pedido.EstadoPedido.PENDIENTE);
            }
            
            // Limpiar detalles existentes
            pedido.getDetalles().clear();
            
            // Agregar detalles desde el formulario
            if (platoIds != null && cantidades != null && platoIds.length == cantidades.length) {
                for (int i = 0; i < platoIds.length; i++) {
                    final Integer platoId = platoIds[i];
                    final Integer cantidad = cantidades[i];
                    if (platoId != null && cantidad != null && cantidad > 0) {
                        platoService.buscarPorId(platoId).ifPresent(plato -> {
                            DetallePedido detalle = new DetallePedido(plato, cantidad);
                            pedido.agregarDetalle(detalle);
                        });
                    }
                }
            }
            
            // Validar que tenga al menos un detalle
            if (pedido.getDetalles().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Debe agregar al menos un plato al pedido");
                return "redirect:/pedidos/nuevo";
            }
            
            pedidoService.guardar(pedido);
            redirectAttributes.addFlashAttribute("success", "Pedido guardado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el pedido: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/pedidos";
    }
    
    @PostMapping("/cambiar-estado/{id}")
    public String cambiarEstado(@PathVariable Integer id,
                               @RequestParam Pedido.EstadoPedido estado,
                               RedirectAttributes redirectAttributes) {
        pedidoService.cambiarEstado(id, estado);
        redirectAttributes.addFlashAttribute("success", "Estado del pedido actualizado");
        return "redirect:/pedidos";
    }
    
    @GetMapping("/cocina")
    public String cocina(Model model) {
        model.addAttribute("pedidos", pedidoService.buscarPorEstado(Pedido.EstadoPedido.EN_PREPARACION));
        return "pedidos/cocina";
    }
}

