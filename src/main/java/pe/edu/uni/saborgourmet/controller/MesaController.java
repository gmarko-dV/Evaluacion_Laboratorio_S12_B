package pe.edu.uni.saborgourmet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.edu.uni.saborgourmet.entity.Mesa;
import pe.edu.uni.saborgourmet.service.MesaService;

import jakarta.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/admin/mesas")
public class MesaController {
    
    private final MesaService mesaService;
    
    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }
    
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("mesas", mesaService.listarTodos());
        model.addAttribute("disponibles", mesaService.buscarDisponibles().size());
        return "admin/mesas/listar";
    }
    
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("mesa", new Mesa());
        return "admin/mesas/formulario";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Optional<Mesa> mesa = mesaService.buscarPorId(id);
        if (mesa.isPresent()) {
            model.addAttribute("mesa", mesa.get());
            return "admin/mesas/formulario";
        }
        return "redirect:/admin/mesas";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Mesa mesa,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/mesas/formulario";
        }
        
        if (mesa.getIdMesa() == null && mesaService.existeNumero(mesa.getNumero())) {
            result.rejectValue("numero", "error.numero", "El número de mesa ya está registrado");
            return "admin/mesas/formulario";
        }
        
        mesaService.guardar(mesa);
        redirectAttributes.addFlashAttribute("success", "Mesa guardada correctamente");
        return "redirect:/admin/mesas";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        mesaService.eliminar(id);
        redirectAttributes.addFlashAttribute("success", "Mesa eliminada correctamente");
        return "redirect:/admin/mesas";
    }
}

