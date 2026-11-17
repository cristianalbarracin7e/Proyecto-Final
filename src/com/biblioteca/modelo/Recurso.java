package com.biblioteca.modelo;

public abstract class Recurso {
    private String titulo;
    private String codigo;
    private int anio;
    public Recurso (String titulo, String codigo, int anio){
        Validador.ValidarCodigo(codigo);
       if ( titulo == null || titulo.trim().isEmpty()){
        throw new IllegalArgumentException("El titulo no puede estar vacio"+ titulo);
       }
       if (anio < 1450 || anio > 2026){
        throw new IllegalArgumentException("año invalido"+ anio);
       }
        this.titulo = titulo;
        this.codigo = codigo;
        this.anio = anio;
    }
    public String getTitulo() { 
        return titulo;
    }
    public String getCodigo() { 
        return codigo; 
    }
    public int getAnio() { 
        return anio;
    }
    public abstract String obtenerInfo();
    
}
