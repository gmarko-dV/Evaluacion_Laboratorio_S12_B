package pe.edu.uni.saborgourmet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pe.edu.uni.saborgourmet.service.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;

@Controller
public class LoginController {
    
    private final ClienteService clienteService;
    private final MesaService mesaService;
    private final PedidoService pedidoService;
    private final FacturaService facturaService;
    
    public LoginController(ClienteService clienteService,
                          MesaService mesaService,
                          PedidoService pedidoService,
                          FacturaService facturaService) {
        this.clienteService = clienteService;
        this.mesaService = mesaService;
        this.pedidoService = pedidoService;
        this.facturaService = facturaService;
    }
    
    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
        }
        if (logout != null) {
            model.addAttribute("message", "Sesión cerrada correctamente");
        }
        return "login";
    }
    
    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Estadísticas para el dashboard
        model.addAttribute("totalClientes", clienteService.listarTodos().size());
        model.addAttribute("totalMesas", mesaService.listarTodos().size());
        model.addAttribute("totalPedidos", pedidoService.listarTodos().size());
        model.addAttribute("totalVentas", facturaService.listarTodos().size());
        
        // Estadísticas adicionales para cajero
        List<pe.edu.uni.saborgourmet.entity.Pedido> pedidosCerradosList = pedidoService.listarTodos().stream()
            .filter(p -> p.getEstado() == pe.edu.uni.saborgourmet.entity.Pedido.EstadoPedido.CERRADO)
            .collect(java.util.stream.Collectors.toList());
        model.addAttribute("pedidosCerrados", pedidosCerradosList.size());
        model.addAttribute("pedidosCerradosList", pedidosCerradosList);
        
        // Calcular totales para cada pedido cerrado
        Map<Integer, BigDecimal> totalesPedidos = new HashMap<>();
        for (pe.edu.uni.saborgourmet.entity.Pedido pedido : pedidosCerradosList) {
            BigDecimal total = pedido.getDetalles().stream()
                .map(pe.edu.uni.saborgourmet.entity.DetallePedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            totalesPedidos.put(pedido.getIdPedido(), total);
        }
        model.addAttribute("totalesPedidos", totalesPedidos);
        
        long ventasPagadas = facturaService.listarTodos().stream()
            .filter(f -> f.getEstado() == pe.edu.uni.saborgourmet.entity.Factura.EstadoFactura.PAGADO)
            .count();
        model.addAttribute("ventasPagadas", ventasPagadas);
        
        // Calcular total de ventas del día
        java.math.BigDecimal totalVentasHoy = facturaService.listarTodos().stream()
            .filter(f -> f.getFechaEmision().toLocalDate().equals(java.time.LocalDate.now()))
            .map(pe.edu.uni.saborgourmet.entity.Factura::getTotal)
            .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
        model.addAttribute("totalVentasHoy", totalVentasHoy);
        
        return "dashboard";
    }
}

