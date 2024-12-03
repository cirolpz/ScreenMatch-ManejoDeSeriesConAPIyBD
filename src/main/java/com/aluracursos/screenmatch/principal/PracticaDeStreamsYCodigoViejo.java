package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.model.Episodio;

import java.util.*;
import java.util.stream.Collectors;

//En este codigo hay un ejemplo de como se usan los streams en java y codigo anterior que fue mejorado. Es de uso personal para recordar como se usan los streams
public class PracticaDeStreamsYCodigoViejo {
    public void muestraEjemplo(){
        List<String> nombres = Arrays.asList("Myshkin","Marco Aurelio","Nastasya Filippovna","MARCOS","Ciro");
        nombres.stream()
                .sorted()
                .limit(4)
                .filter(n -> n.startsWith("C"))
                .map(m -> m.toUpperCase())
                .forEach(System.out::println);
    }

    //Mostrar solo el titulo de los episodios para las temporadas
        /*
        Estamos recorriendo los capitulos que están dentro de las temporadas.
        for (int i=0; i<datos.totalDeTemporadas(); i++){
        List<DatosEpisodio>  episodiosTemporada = temporadas.get(i).episodios();
        for(int j=0; j< episodiosTemporada.size();j++){
            System.out.println(episodiosTemporada.get(j).titulo());
        }
        Haremos lo mismo pero con lambdas
    }*/
    //  temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    // t es el argumento, la flecha me indica que es lo que queremos hacer (seria como la representacion de temporadas), en este caso traer episodios
    //Luego en el episodio queremos recorrerlos y traer los titulos. (e sería la representacion de episodios)

    private void codigoViejo() {
        //Converitr todas las informaciones a una lista del tipo DatosEpisodio
        List<DatosTemporadas> temporadas = new ArrayList<>();
        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        // TOP 5 Episodes
        System.out.println("TOP 5 Episodios:");
        datosEpisodios.stream()
                .filter(e -> !e.evaluacion().equals("N/A"))
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                .limit(5)
                .forEach(System.out::println);

        //Convirtiendo los datos a una lista de tipo episiodio
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());
        episodios.forEach(System.out::println);
/*    //busqueda de episodios de apartir de x año
        System.out.println("Indica el año a partir del cual desea ver los episodios:");
        var fecha = teclado.nextInt();
        teclado.nextLine();

        LocalDate fechaBusqueda = LocalDate.of(fecha,1,1);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                .filter(e -> e.getFechaDeLanzamiento()!= null && e.getFechaDeLanzamiento().isAfter(fechaBusqueda))
                .forEach(e -> System.out.println("Temporada "+ e.getTemporada()
                +
                        "Episodio "+e.getTitulo()
                + "Fecha de Lanzamiento"+e.getFechaDeLanzamiento().format(dtf)
                ));*/
/*//Buscar episodios por pedazos de titulo
        System.out.println("Por favor esacribir el titulo del episodio que desea ver: ");
        var pedazoDeTitulo = teclado.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoDeTitulo.toUpperCase()))
                .findFirst();
        if(episodioBuscado.isPresent()){
            System.out.println("El episodio encontrado puede ser: ");
            System.out.println(episodioBuscado.get().getNumeroEpisodio()+" "+episodioBuscado.get().getTitulo()+" de la temporada: "+ episodioBuscado.get().getTemporada()+" calificado con:"+episodioBuscado.get().getEvaluacion());
        }
        else {
            System.out.println("Episodio no encontrado.");
        }*/
//Mapearlo
    /*    Map<Integer,Double>evaluacionesXTemporada= episodios.stream()
                .filter(e -> e.getEvaluacion()>0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,//Collectos es propio del paquete java.util.straems.colectors que proporciona metodos q recopila datos y los convierte en una collecion
                        Collectors.averagingDouble(Episodio::getEvaluacion)));
        System.out.println(evaluacionesXTemporada);
*/

        //Estadisticas con Streams sin iterar datos o filtrar elementos de forma rara
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
        //Estadisticas generales:
        System.out.println(est);
        System.out.println("Meedia de las evaluaciones: " + est.getAverage());
        System.out.println("Episodio mejor evaluado: " + est.getMax());
        System.out.println("Episodio peor evaluado: " + est.getMin());
    }
}
