package grupo05.es.resumen.controller;

import grupo05.es.resumen.model.Usuario;
import grupo05.es.resumen.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class WebUsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/cambiar-contrasena")
    public String mostrarFormularioCambio() {
        return "cambiar_contrasena";
    }

    @PostMapping("/cambiar-contrasena")
    public String cambiarContrasena(@RequestParam String actual,
                                    @RequestParam String nueva,
                                    @RequestParam String confirmar,
                                    Principal principal,
                                    Model model) {

        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(principal.getName());

        if (optionalUsuario.isEmpty()) {
            model.addAttribute("error", "No se encontró el usuario.");
            return "cambiar_contrasena";
        }

        Usuario usuario = optionalUsuario.get();

        if (!passwordEncoder.matches(actual, usuario.getPassword())) {
            model.addAttribute("error", "La contraseña actual es incorrecta.");
            return "cambiar_contrasena";
        }

        if (!nueva.equals(confirmar)) {
            model.addAttribute("error", "Las nuevas contraseñas no coinciden.");
            return "cambiar_contrasena";
        }

        usuario.setPassword(passwordEncoder.encode(nueva));
        usuarioRepository.save(usuario);

        model.addAttribute("mensaje", "Contraseña cambiada correctamente.");
        return "cambiar_contrasena";
    }
}
