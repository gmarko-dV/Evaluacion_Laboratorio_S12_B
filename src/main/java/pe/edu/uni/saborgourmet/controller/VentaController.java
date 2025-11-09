package pe.edu.uni.saborgourmet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.edu.uni.saborgourmet.entity.Factura;
import pe.edu.uni.saborgourmet.entity.Pedido;
import pe.edu.uni.saborgourmet.service.FacturaService;
import pe.edu.uni.saborgourmet.service.PedidoService;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/ventas")
public class VentaController {
    
    private final FacturaService facturaService;
    private final PedidoService pedidoService;
    
    public VentaController(FacturaService facturaService, PedidoService pedidoService) {
        this.facturaService = facturaService;
        this.pedidoService = pedidoService;
    }
    
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("facturas", facturaService.listarTodos());
        return "ventas/listar";
    }
    
    @GetMapping("/nueva/{idPedido}")
    public String nueva(@PathVariable Integer idPedido, Model model, RedirectAttributes redirectAttributes) {
        Optional<Pedido> pedido = pedidoService.buscarPorId(idPedido);
        if (!pedido.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Pedido no encontrado");
            return "redirect:/ventas";
        }
        
        if (pedido.get().getEstado() != Pedido.EstadoPedido.CERRADO) {
            redirectAttributes.addFlashAttribute("error", "El pedido debe estar cerrado para generar la factura");
            return "redirect:/ventas";
        }
        
        // Verificar si ya existe una factura para este pedido
        boolean yaFacturado = facturaService.listarTodos().stream()
            .anyMatch(f -> f.getPedido().getIdPedido().equals(idPedido));
        
        if (yaFacturado) {
            redirectAttributes.addFlashAttribute("error", "Este pedido ya tiene una factura generada");
            return "redirect:/ventas";
        }
        
        Pedido pedidoObj = pedido.get();
        model.addAttribute("pedido", pedidoObj);
        model.addAttribute("metodosPago", Factura.MetodoPago.values());
        
        // Calcular total del pedido
        BigDecimal total = pedidoObj.getDetalles().stream()
            .map(pe.edu.uni.saborgourmet.entity.DetallePedido::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("totalPedido", total);
        
        return "ventas/formulario";
    }
    
    @PostMapping("/generar")
    public String generar(@RequestParam Integer idPedido,
                         @RequestParam Factura.MetodoPago metodoPago,
                         RedirectAttributes redirectAttributes) {
        try {
            facturaService.generarFactura(idPedido, metodoPago);
            redirectAttributes.addFlashAttribute("success", "Factura generada correctamente");
            return "redirect:/ventas";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/pedidos";
        }
    }
    
    @PostMapping("/pagar/{id}")
    public String pagar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        facturaService.marcarComoPagada(id);
        redirectAttributes.addFlashAttribute("success", "Factura marcada como pagada");
        return "redirect:/ventas";
    }
}

