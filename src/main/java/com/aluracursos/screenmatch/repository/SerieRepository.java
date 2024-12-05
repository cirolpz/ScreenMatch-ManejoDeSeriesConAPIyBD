package com.aluracursos.screenmatch.repository;

import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Serie findByTitulo(String titulo);
    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);
    List<Serie> findTop5ByOrderByEvaluacionDesc();
    List<Serie> findByGenero(Categoria categoria);

    //@Query(value = "SELECT * FROM series WHERE series.total_temporadas <= 6 AND series.evaluacion >= 7.5", nativeQuery = true) //SQL, puede no correr en todas las bases de datos
    @Query(value = "SELECT s FROM Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.evaluacion >= :evaluacion")//JPQL, los : significa que son parametros
    List <Serie> seriesPorTemporadaYEvaluacion(int totalTemporadas, double evaluacion);

    @Query("SELECT e FROM Episodio e WHERE e.titulo ILIKE :nombreEpisodio")
    List<Episodio> episodiosPorNombre(String nombreEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episodio> top5Episodios(Serie serie);

    @Query("SELECT s FROM Serie s " + "JOIN s.episodios e " + "GROUP BY s " + "ORDER BY MAX(e.fechaDeLanzamiento) DESC LIMIT 5")
    List<Serie> lanzamientosMasRecientes();

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :numeroTemporada")
    List<Episodio> obtenerTemporadasPorNumero(Long id, Long numeroTemporada);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episodio> findTop5EpisodesByOrderByEvaluacionDesc(Long id);
}
