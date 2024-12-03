package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.*;
import com.aluracursos.screenmatch.repository.SerieRepository;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE="https://www.omdbapi.com/?t=";
    private final String API_KEY = System.getenv("API_KEY_SERIES");//getenv es un metodo de la clase System que permite obtener variables de entorno
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSeries = new ArrayList<>();
    private List<Serie> series;
    private Optional<Serie> serieBuscada;
    private SerieRepository serieRepository;

    public Principal(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar series 
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    4 - Buscar serie por titulo
                    5 - Top 5 mejores series
                    6 - Buscar serie por categoria
                    7 - Filtrar series por temporadas y evaluiación
                    8 - Buscar episodios por titulo
                    9 - Top 5 episodios                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarTop5Series();
                    break;
                case 6:
                    buscarSeriePorCategoria();
                    break;
                case 7:
                    filtrarSeriesPorTemporadasYEvaluacion();
                    break;
                case 8:
                    buscarEpisodioPorTitulo();
                    break;
                case 9:
                    buscarTop5Episodios();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private DatosSerie getDatosSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        System.out.println(json);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }

    private void buscarEpisodioPorSerie() {
        mostrarSeriesBuscadas();
        System.out.println("Escribe el número de la serie que deseas buscar episodios: ");
        var nombreSerie = teclado.nextLine();
        List<DatosTemporadas> temporadas = new ArrayList<>();
        Optional<Serie> serie= series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();
        if(serie.isPresent()){
            var serieEncontrada = serie.get();
            try{
                for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                    var json = consumoApi.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                    DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
                    temporadas.add(datosTemporada);
                }
                temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(t -> t.episodios().stream()
                            .map(d -> new Episodio(t.numero(), d)))
                    .collect(Collectors.toList());
            serieEncontrada.setEpisodios(episodios);
                serieRepository.save(serieEncontrada);
        }
            catch (NullPointerException e) {
                System.out.println("No se encontraron episodios para la serie: "+serieEncontrada.getTotalTemporadas());
            }
        }
        else {
            System.out.println("No se encontró la serie");
        }
    }

    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();
        Serie serie = new Serie(datos);
        serieRepository.save(serie);
        datosSeries.add(datos);
        System.out.println(datos);
    }

    private void mostrarSeriesBuscadas() {
        series = serieRepository.findAll();
        series.stream().sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
        datosSeries.forEach(System.out::println);
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        serieBuscada = serieRepository.findByTituloContainsIgnoreCase(nombreSerie);
        if (serieBuscada.isPresent()) {
            System.out.println("La serie buscada es: "+serieBuscada.get());
        } else {
            System.out.println("No se encontró la serie");
        }
    }

    private void buscarTop5Series() {
        List<Serie> topSeries = serieRepository.findTop5ByOrderByEvaluacionDesc();
        topSeries.forEach(s -> System.out.println("Serie: "+s.getTitulo()+ " Evaluación: "+s.getEvaluacion()));
    }

    private void buscarSeriePorCategoria(){
        System.out.println("Escribe el generó de la categoria que deseas buscar");
        var genero = teclado.nextLine();
        var categoria = Categoria.fromEspanol(genero);
        List<Serie> seriesPorCategoria = serieRepository.findByGenero(categoria);
        seriesPorCategoria.forEach(s -> System.out.println("Serie: "+s.getTitulo()+ " Evaluación: "+s.getEvaluacion()));
    }

    private void filtrarSeriesPorTemporadasYEvaluacion(){
        try{
            System.out.println("Escribe el número de temporadas");
            var totalTemporadas = teclado.nextInt();
            System.out.println("Escribe la evaluación mínima");
            var evaluacion = teclado.nextDouble();
            teclado.nextLine();
            List<Serie> seriesFiltradas = serieRepository.seriesPorTemporadaYEvaluacion(totalTemporadas, evaluacion);
            seriesFiltradas.forEach(s -> System.out.println("Serie: "+s.getTitulo()+ " Evaluación: "+s.getEvaluacion()));
        }
        catch (InputMismatchException e) {
            System.out.println("Por favor, ingresa un número válido.");
            teclado.nextLine();
            }
        }

    private void buscarEpisodioPorTitulo(){
        System.out.println("Escribe el nombre del episodio que deseas buscar");
        var nombreEpisodio = teclado.nextLine();
        List<Episodio> episodiosEncontrados = serieRepository.episodiosPorNombre(nombreEpisodio);
        episodiosEncontrados.forEach(s -> System.out.printf("Serie: %s Temnporada %s Episodio %s Evaluacion %s \n",
                s.getSerie().getTitulo(), s.getTemporada(), s.getTitulo(), s.getEvaluacion()));
    }

    private void buscarTop5Episodios() {
        buscarSeriePorTitulo();
        if(serieBuscada.isPresent()){
            Serie serie = serieBuscada.get();
            List<Episodio> topEpisodios = serieRepository.top5Episodios(serie);
            topEpisodios.forEach(e -> System.out.printf("Serie: %s Temporada: %s Episodio: %s Evaluación: %s \n",
                    e.getSerie().getTitulo(), e.getTemporada(), e.getTitulo(), e.getEvaluacion()));
        }
    }
}