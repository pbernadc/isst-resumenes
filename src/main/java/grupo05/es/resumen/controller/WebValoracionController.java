package grupo05.es.resumen.controller;

import grupo05.es.resumen.model.Valoracion;
import grupo05.es.resumen.model.Resumen;
import grupo05.es.resumen.repository.ValoracionRepository;
import grupo05.es.resumen.repository.ResumenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/valoraciones")
public class WebValoracionController {

    @Autowired
    private ValoracionRepository valoracionRepository;

    @Autowired
    private ResumenRepository resumenRepository;

    @PostMapping("/guardar")
    public String guardarValoracion(@RequestParam Long resumenId,
                                     @RequestParam int puntuacion,
                                     Principal principal,
                                     Model model) {

        // Obtener el email del usuario logueado
        String email = principal.getName();

        // Crear nueva valoración
        Valoracion valoracion = new Valoracion();
        valoracion.setResumenId(resumenId);
        valoracion.setUsuarioEmail(email);
        valoracion.setPuntuacion(puntuacion);

        // Guardar en la base de datos
        valoracionRepository.save(valoracion);

        // Calcular la nueva media de valoraciones
        List<Valoracion> valoracionesResumen = valoracionRepository.findByResumenId(resumenId);
        double media = valoracionesResumen.stream()
                .mapToInt(Valoracion::getPuntuacion)
                .average()
                .orElse(0.0);

        // Actualizar el resumen con la nueva media
        Resumen resumen = resumenRepository.findById(resumenId).orElse(null);
        if (resumen != null) {
            resumen.setValoracionMedia(media);
            resumenRepository.save(resumen);
        }

        // Redirigir al resumen
        return "redirect:/resumen/" + resumenId;
    }
}
