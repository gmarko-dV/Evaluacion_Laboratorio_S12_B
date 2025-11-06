package pe.edu.uni.saborgourmet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.edu.uni.saborgourmet.entity.Cliente;
import pe.edu.uni.saborgourmet.service.ClienteService;

import jakarta.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/admin/clientes")
public class ClienteController {
    
    private final ClienteService clienteService;
    
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        return "admin/clientes/listar";
    }
    
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "admin/clientes/formulario";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        if (cliente.isPresent()) {
            model.addAttribute("cliente", cliente.get());
            return "admin/clientes/formulario";
        }
        return "redirect:/admin/clientes";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Cliente cliente, 
                         BindingResult result, 
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/clientes/formulario";
        }
        
        if (cliente.getIdCliente() == null && clienteService.existeDni(cliente.getDni())) {
            result.rejectValue("dni", "error.dni", "El DNI ya está registrado");
            return "admin/clientes/formulario";
        }
        
        clienteService.guardar(cliente);
        redirectAttributes.addFlashAttribute("success", "Cliente guardado correctamente");
        return "redirect:/admin/clientes";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        clienteService.eliminar(id);
        redirectAttributes.addFlashAttribute("success", "Cliente eliminado correctamente");
        return "redirect:/admin/clientes";
    }
}

