package pe.edu.uni.saborgourmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.uni.saborgourmet.entity.Bitacora;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BitacoraRepository extends JpaRepository<Bitacora, Integer> {
    List<Bitacora> findByUsuarioIdUsuario(Integer idUsuario);
    List<Bitacora> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Bitacora> findByTabla(String tabla);
}

