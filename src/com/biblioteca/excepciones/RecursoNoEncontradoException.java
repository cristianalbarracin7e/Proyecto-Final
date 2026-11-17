package com.biblioteca.excepciones;
public class RecursoNoEncontradoException extends Exception {
    public RecursoNoEncontradoException (String codigo){
        super("Recurso con codigo"+ codigo + "no encontrado.");
    }
}
