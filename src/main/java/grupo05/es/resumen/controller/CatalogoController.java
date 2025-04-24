package grupo05.es.resumen.controller;

import grupo05.es.resumen.model.Resumen;
import grupo05.es.resumen.model.Usuario;
import grupo05.es.resumen.repository.ResumenRepository;
import grupo05.es.resumen.repository.UsuarioRepository;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;


@Controller
public class CatalogoController {

    private final ResumenRepository resumenRepository;
    private final UsuarioRepository usuarioRepository;

    public CatalogoController(ResumenRepository resumenRepository, UsuarioRepository usuarioRepository) {
        this.resumenRepository = resumenRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/catalogo")
    public String verCatalogo(Model model, Authentication auth) {
        List<Resumen> resúmenes;

        if (auth == null) {
            // Visitante anónimo: solo resúmenes gratuitos
            resúmenes = resumenRepository.findByGratuitoTrue();
        } else {
            String email = auth.getName();
            Usuario usuario = usuarioRepository.findById(email).orElse(null);

            if (usuario != null && usuario.getRol().equals("LECTOR") && usuario.isSuscrito()) {
                // Lector suscrito: acceso completo
                resúmenes = resumenRepository.findAll();
            } else {
                // No suscrito o con otro rol: solo gratuitos
                resúmenes = resumenRepository.findByGratuitoTrue();
            }
        }

        model.addAttribute("resumenes", resúmenes);
        return "catalogo"; // Thymeleaf lo buscará en templates/catalogo.html
    }

    @GetMapping("/resumen/{id}")
    public String verResumenTexto(@PathVariable Long id, Model model) {
    Optional<Resumen> opt = resumenRepository.findById(id);
    if (opt.isPresent()) {
        model.addAttribute("resumen", opt.get());
        return "resumen_detalle";
    }
    return "redirect:/catalogo";
}


}
