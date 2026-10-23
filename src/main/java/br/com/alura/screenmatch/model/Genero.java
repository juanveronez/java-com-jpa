package br.com.alura.screenmatch.model;

public enum Genero {
    ACAO("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    CRIME("Crime"),
    FANTASIA("Fantasy");

    private final String categoriaOmdb;


    Genero(String categoriaOmdb) {
        this.categoriaOmdb = categoriaOmdb;
    }

    public static Genero fromString(String categoriaOmdb) {
        for (Genero genero : Genero.values()) {
            if (genero.categoriaOmdb.equals(categoriaOmdb)) return genero;
        }

        return null;
    }
}
