package grupo05.es.resumen.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Usuario {

    @Id
    @Column(nullable = false, unique = true)
    private String email;

    private String nombre;

    @Column(nullable = false)
    private String rol; // VISITANTE, LECTOR, ESCRITOR, ADMIN

    private boolean suscrito; // Solo true si es LECTOR y paga

    private String password; // si quieres añadir login real en el futuro
}

