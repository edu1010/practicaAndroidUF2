package com.tiempociudades.edu1010.practica2;


public class cancion {
    private String urlImagen;
    private String titulo;
    private String autor;
    private  String urlCancion;

    public cancion() {
    }

    public cancion(String urlImagen, String titulo, String autor, String urlCancion) {
        this.urlImagen = urlImagen;
        this.titulo = titulo;
        this.autor = autor;
        this.urlCancion = urlCancion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getUrlCancion() {
        return urlCancion;
    }

    public void setUrlCancion(String urlCancion) {
        this.urlCancion = urlCancion;
    }
}
