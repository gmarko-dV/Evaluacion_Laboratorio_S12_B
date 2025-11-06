package pe.edu.uni.saborgourmet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.edu.uni.saborgourmet.entity.Factura;
import pe.edu.uni.saborgourmet.entity.Pedido;
import pe.edu.uni.saborgourmet.service.FacturaService;
import pe.edu.uni.saborgourmet.service.PedidoService;

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
    public String nueva(@PathVariable Integer idPedido, Model model) {
        Optional<Pedido> pedido = pedidoService.buscarPorId(idPedido);
        if (pedido.isPresent() && pedido.get().getEstado() == Pedido.EstadoPedido.CERRADO) {
            model.addAttribute("pedido", pedido.get());
            model.addAttribute("metodosPago", Factura.MetodoPago.values());
            return "ventas/formulario";
        }
        return "redirect:/ventas";
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

