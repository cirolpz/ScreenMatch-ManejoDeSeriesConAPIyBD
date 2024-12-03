package com.aluracursos.screenmatch.model;

public enum Categoria {
    ACCION("Action","Acción"),
    AVENTURA("Adventure","Aventura"),
    COMEDIA("Comedy","Comedia"),
    DRAMA("Drama","Drama"),
    FANTASIA("Fantasy","Fantasia"),
    HORROR("Horror","Terror"),
    MISTERIO("Mystery","Misterio"),
    ROMANCE("Romance","Romance"),
    SUSPENSO("Thriller","Suspenso"),
    TERROR("Terror","Terror");

    private  String categoriaOmdb;
    private String categoriaEspanol;

    Categoria(String categoriaOmdb, String categoriaEspanol) {
        this.categoriaOmdb=categoriaOmdb;
        this.categoriaEspanol=categoriaEspanol;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("No se encontró la categoria " + text);
    }

    public static Categoria fromEspanol(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaEspanol.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("No se encontró la categoria " + text);
    }
}
