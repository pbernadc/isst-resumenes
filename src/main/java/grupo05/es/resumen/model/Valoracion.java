package grupo05.es.resumen.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Valoracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuarioEmail;

    private Long resumenId;

    private int puntuacion; // de 1 a 5

    @Column(length = 1000)
    private String comentario;
}
