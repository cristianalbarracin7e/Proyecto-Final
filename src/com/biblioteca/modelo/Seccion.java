
package com.biblioteca.modelo;

import java.util.ArrayList;
import com.biblioteca.excepciones.SeccionLlenaException;
import com.biblioteca.excepciones.RecursoNoEncontradoException;

public class Seccion {

    private String nombre;
    private int capacidad;
    private ArrayList<Recurso> recursos;

    public Seccion(String nombre, int capacidad) {

        if (capacidad <= 0) {
            throw new IllegalArgumentException("Capacidad debe ser mayor a 0.");
        }

        this.nombre = nombre;
        this.capacidad = capacidad;
        recursos = new ArrayList<Recurso>();
    }

    public void agregarRecurso(Recurso r) throws SeccionLlenaException {

        if (recursos.size() >= capacidad) {
            throw new SeccionLlenaException("Sección " + nombre + " sin espacio disponible.");
        }

        recursos.add(r);

        System.out.println("Recurso " + r.getTitulo() + " ubicado en sección " + nombre);
    }

    public void retirarRecurso(String codigo) throws RecursoNoEncontradoException {

        for (int i = 0; i < recursos.size(); i++) {

            if (recursos.get(i).getCodigo().equals(codigo)) {
                recursos.remove(i);
                System.out.println("Recurso dado de baja.");
                return;
            }
        }

        throw new RecursoNoEncontradoException(codigo);
    }

    public Recurso buscarRecurso(String codigo) throws RecursoNoEncontradoException {

        for (int i = 0; i < recursos.size(); i++) {

            if (recursos.get(i).getCodigo().equals(codigo)) {
                return recursos.get(i);
            }
        }

        throw new RecursoNoEncontradoException(codigo);
    }

    public void listarRecursos() {

        if (recursos.isEmpty()) {
            System.out.println("No hay recursos en esta sección.");
        } else {

            for (int i = 0; i < recursos.size(); i++) {
                System.out.println(recursos.get(i).obtenerInfo());
            }
        }
    }

    public String getNombre() {
        return nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public ArrayList<Recurso> getRecursos() {
        return recursos;
    }
}
