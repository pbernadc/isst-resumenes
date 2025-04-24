package grupo05.es.resumen.controller;

import grupo05.es.resumen.model.Resumen;
import grupo05.es.resumen.model.Usuario;
import grupo05.es.resumen.repository.ResumenRepository;
import grupo05.es.resumen.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/escritor")
public class EscritorController {

    private final ResumenRepository resumenRepository;

    public EscritorController(ResumenRepository resumenRepository) {
        this.resumenRepository = resumenRepository;
    }

    // Mostrar formulario para subir nuevo resumen
    @GetMapping("/subir")
    public String mostrarFormulario(Model model) {
        model.addAttribute("resumen", new Resumen());
        return "subir_resumen";
    }

    // Procesar formulario
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/subir")
    public String procesarResumen(@ModelAttribute Resumen resumen, Principal principal) {
    Usuario escritor = usuarioRepository.findById(principal.getName()).orElse(null);
    resumen.setEscritor(escritor);
    resumen.setValoracionMedia(0.0);
    resumen.setVisitas(0);
    resumenRepository.save(resumen);
    return "redirect:/escritor/mis-resumenes";
}

    // Ver resúmenes subidos por este escritor
    @GetMapping("/mis-resumenes")
    public String verMisResumenes(Model model, Principal principal) {
        List<Resumen> lista = resumenRepository.findByEscritorEmail(principal.getName());
        model.addAttribute("resumenes", lista);
        return "mis_resumenes";
    }
}
