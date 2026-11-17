
package com.biblioteca.modelo;

import java.util.ArrayList;
import com.biblioteca.excepciones.RecursoNoEncontradoException;
import com.biblioteca.excepciones.PersonalNoEncontradoException;

public class Biblioteca {

    private String nombre;
    private ArrayList<Seccion> secciones;
    private ArrayList<Personal> personal;

    public Biblioteca(String nombre) {

        this.nombre = nombre;
        secciones = new ArrayList<Seccion>();
        personal = new ArrayList<Personal>();

        secciones.add(new Seccion("Referencia", 10));
        secciones.add(new Seccion("Literatura", 8));
        secciones.add(new Seccion("Ciencias", 6));
    }

    public void agregarSeccion(String nombre, int capacidad) {

        secciones.add(new Seccion(nombre, capacidad));

        System.out.println("Sección '" + nombre + "' creada con capacidad " + capacidad);
    }

    public void agregarPersonal(Personal p) {

        personal.add(p);

        System.out.println(p.getNombre() + " registrado en la biblioteca.");
    }

    public Seccion buscarSeccion(String nombre) {

        for (int i = 0; i < secciones.size(); i++) {

            if (secciones.get(i).getNombre().equals(nombre)) {
                return secciones.get(i);
            }
        }

        return null;
    }

    public Recurso buscarRecurso(String codigo) throws RecursoNoEncontradoException {

        for (int i = 0; i < secciones.size(); i++) {

            try {
                return secciones.get(i).buscarRecurso(codigo);
            } catch (RecursoNoEncontradoException e) {
                // Continúa buscando
            }
        }

        throw new RecursoNoEncontradoException(codigo);
    }

    public Bibliotecario buscarBibliotecario(String id) throws PersonalNoEncontradoException {

        for (int i = 0; i < personal.size(); i++) {

            if (personal.get(i) instanceof Bibliotecario) {

                Bibliotecario b = (Bibliotecario) personal.get(i);

                if (b.getId().equals(id)) {
                    return b;
                }
            }
        }

        throw new PersonalNoEncontradoException(id);
    }

    public void listarBibliotecarios() {

        for (int i = 0; i < personal.size(); i++) {

            if (personal.get(i) instanceof Bibliotecario) {

                Bibliotecario b = (Bibliotecario) personal.get(i);

                System.out.println(b.getId() + " - " + b.getNombre()
                        + " (" + b.getArea() + ")");
            }
        }
    }

    public void generarReporteGeneral() {

        for (int i = 0; i < secciones.size(); i++) {

            Seccion s = secciones.get(i);

            System.out.println("Sección: " + s.getNombre()
                    + " | Ocupación: " + s.getRecursos().size()
                    + "/" + s.getCapacidad());
        }

        System.out.println("Total de personal: " + personal.size());

        for (int i = 0; i < personal.size(); i++) {
            System.out.println(personal.get(i).generarReporte());
        }
    }

    public ArrayList<Seccion> getSecciones() {
        return secciones;
    }

    public ArrayList<Personal> getPersonal() {
        return personal;
    }
}
