package com.aluracursos.screenmatch.controller;

import com.aluracursos.screenmatch.dto.EpisodioDTO;
import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {
    @Autowired
    private SerieService serieService;

    @GetMapping()
    public List<SerieDTO> obtenerTodasLasSeries() {
      return serieService.obtenerTodasLasSeries();
    }
    @GetMapping("/top5")
    public List<SerieDTO> obtenerTop5Series() {
      return serieService.obtenerTop5Series();
    }
    @GetMapping("/lanzamientos")
    public List<SerieDTO> obtenerLanzamientosRecientes() {
      return serieService.obtenerLanzamientosRecientes();
    }
    @GetMapping("/{id}")
    public SerieDTO obtenerSeriePorId(@PathVariable Long id) {
      return serieService.obtenerSeriePorId(id);
    }
    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obtenerTodasLasTemporadas(@PathVariable Long id){
        return serieService.obtenerTodasLasTemporadas(id);
    }
    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    public List<EpisodioDTO> obtenerTemporadasPorNumero(@PathVariable Long id, @PathVariable Long numeroTemporada){
        return serieService.obtenerTemporadasPorNumero(id, numeroTemporada);
    }
    @GetMapping("/categoria/{genero}")
    public List<SerieDTO> obtenerSeriesPorGenero(@PathVariable String genero){
        return serieService.obtenerSeriesPorGenero(genero);
    }
    @GetMapping("/{id}/temporadas/top")
    public List<EpisodioDTO> obtenerTemporadasPorNumero(@PathVariable Long id ){
        return serieService.obtenerTop5Episodios(id);
    }
}
