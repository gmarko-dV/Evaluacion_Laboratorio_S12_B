package pe.edu.uni.saborgourmet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.edu.uni.saborgourmet.entity.Plato;
import pe.edu.uni.saborgourmet.service.PlatoService;

import jakarta.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/admin/platos")
public class PlatoController {
    
    private final PlatoService platoService;
    
    public PlatoController(PlatoService platoService) {
        this.platoService = platoService;
    }
    
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("platos", platoService.listarTodos());
        return "admin/platos/listar";
    }
    
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("plato", new Plato());
        model.addAttribute("tipos", new String[]{"ENTRADA", "FONDO", "POSTRE", "BEBIDA"});
        return "admin/platos/formulario";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Optional<Plato> plato = platoService.buscarPorId(id);
        if (plato.isPresent()) {
            model.addAttribute("plato", plato.get());
            model.addAttribute("tipos", new String[]{"ENTRADA", "FONDO", "POSTRE", "BEBIDA"});
            return "admin/platos/formulario";
        }
        return "redirect:/admin/platos";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Plato plato,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("tipos", new String[]{"ENTRADA", "FONDO", "POSTRE", "BEBIDA"});
            return "admin/platos/formulario";
        }
        
        platoService.guardar(plato);
        redirectAttributes.addFlashAttribute("success", "Plato guardado correctamente");
        return "redirect:/admin/platos";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        platoService.eliminar(id);
        redirectAttributes.addFlashAttribute("success", "Plato eliminado correctamente");
        return "redirect:/admin/platos";
    }
}

