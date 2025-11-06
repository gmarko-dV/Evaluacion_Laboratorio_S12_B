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
                         @RequestParam(required = false) Integer[] platoIds,
                         @RequestParam(required = false) Integer[] cantidades,
                         RedirectAttributes redirectAttributes) {
        try {
            // Si hay detalles desde el formulario
            if (platoIds != null && cantidades != null && platoIds.length == cantidades.length) {
                pedido.getDetalles().clear();
                for (int i = 0; i < platoIds.length; i++) {
                    if (platoIds[i] != null && cantidades[i] != null && cantidades[i] > 0) {
                        platoService.buscarPorId(platoIds[i]).ifPresent(plato -> {
                            DetallePedido detalle = new DetallePedido(plato, cantidades[i]);
                            pedido.agregarDetalle(detalle);
                        });
                    }
                }
            }
            
            pedidoService.guardar(pedido);
            redirectAttributes.addFlashAttribute("success", "Pedido guardado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el pedido: " + e.getMessage());
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

