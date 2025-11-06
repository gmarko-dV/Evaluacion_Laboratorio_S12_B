package pe.edu.uni.saborgourmet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.edu.uni.saborgourmet.entity.Usuario;
import pe.edu.uni.saborgourmet.service.UsuarioService;

import jakarta.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/admin/usuarios")
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "admin/usuarios/listar";
    }
    
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", Usuario.RolUsuario.values());
        return "admin/usuarios/formulario";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
            model.addAttribute("roles", Usuario.RolUsuario.values());
            return "admin/usuarios/formulario";
        }
        return "redirect:/admin/usuarios";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Usuario usuario,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("roles", Usuario.RolUsuario.values());
            return "admin/usuarios/formulario";
        }
        
        if (usuario.getIdUsuario() == null && usuarioService.existeNombreUsuario(usuario.getNombreUsuario())) {
            result.rejectValue("nombreUsuario", "error.usuario", "El nombre de usuario ya existe");
            redirectAttributes.addFlashAttribute("roles", Usuario.RolUsuario.values());
            return "admin/usuarios/formulario";
        }
        
        usuarioService.guardar(usuario);
        redirectAttributes.addFlashAttribute("success", "Usuario guardado correctamente");
        return "redirect:/admin/usuarios";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        usuarioService.eliminar(id);
        redirectAttributes.addFlashAttribute("success", "Usuario eliminado correctamente");
        return "redirect:/admin/usuarios";
    }
}

