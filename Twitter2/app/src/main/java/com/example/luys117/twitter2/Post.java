package com.example.luys117.twitter2;

public class Post {
    private String titulo,contenido,autor,imagen;

    public Post(){/*Constructor vacio que siempre pide FireBase(Debe llevar el mismo nombre de la clase*/

    }

    public Post(String titulo, String contenido, String autor, String imagen){
        this.titulo=titulo;
        this.contenido=contenido;
        this.autor=autor;
        this.imagen=imagen;
    }

    public String getTitulo(){
        return titulo;
    }

    public String getContenido(){
        return contenido;
    }

    public String getAutor(){
        return autor;
    }

    public String getImagen(){
        return imagen;
    }

    public void setTitulo(String title){
        this.titulo=title;
    }

    public void setContenido(String Cont){
        this.contenido=Cont;
    }

    public void setAutor(String aut){
        this.autor=aut;
    }

    public void setImagen(String image){
        this.imagen=image;
    }





}
