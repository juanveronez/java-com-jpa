package br.com.alura.screenmatch.model;

import java.util.*;

public class Serie {
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    private List<Genero> genero;
    private String poster;
    private String sinopse;
    private List<String> atores;

    public Serie(DadosSerie dadosSerie) {
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.poster = dadosSerie.poster();
        this.sinopse = dadosSerie.sinopse();

        this.avaliacao = Optional.of(Double.valueOf(dadosSerie.avaliacao())).orElse(null);
        this.genero = Arrays.stream(dadosSerie.genero().split(", "))
                .map(Genero::fromString)
                .filter(Objects::nonNull)
                .toList();
        this.atores = Arrays.stream(dadosSerie.atores().split(",")).toList();
    }

    @Override
    public String toString() {
        return "Serie{" +
                "titulo='" + titulo + '\'' +
                ", totalTemporadas=" + totalTemporadas +
                ", avaliacao=" + avaliacao +
                ", genero=" + genero +
                ", poster='" + poster + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ", atores=" + atores +
                '}';
    }
}
