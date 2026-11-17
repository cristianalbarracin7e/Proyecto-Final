package com.biblioteca.modelo;
public class Validador {
    private Validador (){ }// no se instancia
    public static void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        for (char c : nombre.toCharArray()) {
            if (Character.isDigit(c)) {
                throw new IllegalArgumentException("El nombre no puede contener números: " + nombre);
            }
        }
    }
    public static void ValidarCodigo(String codigo){
        if (codigo == null || codigo.trim().isEmpty() || codigo.contains("")){
            throw new IllegalArgumentException("Codigo Invalido: no puede ser vacio ni tener espacios"+codigo);
        }
    }
    
}
