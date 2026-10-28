package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.beans.factory.annotation.Value;

import java.beans.BeanProperty;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "http://www.omdbapi.com/?t=%s&apikey=%s";

    @Value("${OMDB_APIKEY}")
    private String API_KEY;

    private final List<Serie> series = new ArrayList<>();

    public void exibeMenu() {
        var menu = """
                1 - Buscar séries
                2 - Buscar episódios
                3 - Listar séries
                4 - Listar séries por gênero
                
                0 - Sair
                """;

        var opcao = -1;
        while (opcao != 0) {
            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeries();
                    break;
                case 4:
                    listarSeriesPorGenero();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void listarSeries() {
        series.forEach(System.out::println);
    }

    private void listarSeriesPorGenero() {
        List<Genero> generos = series.stream()
                .flatMap(s -> s.getGenero().stream())
                .distinct()
                .toList();
        System.out.println("Generos disponíveis:");
        for (int i = 0; i < generos.size(); i++) {
            System.out.println(i + ": " + generos.get(i));
        }

        var indiceGenero = leitura.nextInt();
        leitura.nextLine();

        var seriesGenero = series.stream()
                .filter(s -> s.getGenero().contains(generos.get(indiceGenero)))
                .toList();
        seriesGenero.forEach(System.out::println);
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();

        series.add(new Serie(dados));
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO.formatted(nomeSerie.replace(" ", "+"), API_KEY));
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){
        DadosSerie dadosSerie = getDadosSerie();
        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            var json = consumo.obterDados(ENDERECO + dadosSerie.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);
    }
}