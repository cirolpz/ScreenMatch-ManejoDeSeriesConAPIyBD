package com.aluracursos.screenmatch.service;

import com.aluracursos.screenmatch.dto.EpisodioDTO;
import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.model.Serie;
import com.aluracursos.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> obtenerTodasLasSeries() {
        return convertidorDeDatosDTOSerie(repository.findAll());
    }

    public List<SerieDTO> obtenerTop5Series() {
        return convertidorDeDatosDTOSerie(repository.findTop5ByOrderByEvaluacionDesc());
    }

    public List<SerieDTO> obtenerLanzamientosRecientes() {
        return convertidorDeDatosDTOSerie(repository.lanzamientosMasRecientes());
    }

    public SerieDTO obtenerSeriePorId(Long id) {
        Optional<Serie> serie= repository.findById(id);
    if(serie.isPresent()){
        Serie s = serie.get();
        return new SerieDTO(s.getId(),s.getTitulo(), s.getTotalTemporadas(), s.getEvaluacion(), s.getPoster(), s.getGenero(), s.getActores(), s.getSinopsis());
    }
    return null;
    }

    public List<EpisodioDTO> obtenerTodasLasTemporadas(Long id) {
        Optional<Serie> serie=repository.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return s.getEpisodios().stream().map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio())).collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDTO> obtenerTemporadasPorNumero(Long id, Long numeroTemporada) {
        return repository.obtenerTemporadasPorNumero(id,numeroTemporada).stream().map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio())).collect(Collectors.toList());
    }

    public List<SerieDTO> obtenerSeriesPorGenero(String genero) {
        Categoria categoria = Categoria.fromEspanol(genero);
        return convertidorDeDatosDTOSerie(repository.findByGenero(categoria));
    }

    public List<EpisodioDTO> obtenerTop5Episodios(Long id) {
        return  convertidorDeDatosDTOEpisodio(repository.findTop5EpisodesByOrderByEvaluacionDesc(id));
    }

    public List<SerieDTO> convertidorDeDatosDTOSerie(List<Serie> serie){
        return serie.stream()
                .map(s -> new SerieDTO(s.getId(),s.getTitulo(), s.getTotalTemporadas(), s.getEvaluacion(), s.getPoster(), s.getGenero(), s.getActores(), s.getSinopsis()))
                .collect(Collectors.toList());
    }

    public List<EpisodioDTO> convertidorDeDatosDTOEpisodio(List<Episodio> episodio){
        return episodio.stream()
                .map(e -> new EpisodioDTO(e.getTemporada(),e.getTitulo(), e.getNumeroEpisodio()))
                .collect(Collectors.toList());
    }
}
