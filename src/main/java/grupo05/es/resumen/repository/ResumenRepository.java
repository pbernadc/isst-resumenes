package grupo05.es.resumen.repository;

import grupo05.es.resumen.model.Resumen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResumenRepository extends JpaRepository<Resumen, Long> {
    List<Resumen> findByEscritorEmail(String email);
    List<Resumen> findByGratuitoTrue();
}
