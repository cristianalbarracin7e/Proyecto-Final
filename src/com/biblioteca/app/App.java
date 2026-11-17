
package com.biblioteca.app;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

import com.biblioteca.modelo.Biblioteca;
import com.biblioteca.modelo.Bibliotecario;
import com.biblioteca.modelo.Auxiliar;
import com.biblioteca.modelo.Recurso;
import com.biblioteca.modelo.Libro;
import com.biblioteca.modelo.RecursoDigital;
import com.biblioteca.modelo.Seccion;
import com.biblioteca.modelo.Prestamo;

import com.biblioteca.excepciones.SeccionLlenaException;
import com.biblioteca.excepciones.RecursoNoEncontradoException;
import com.biblioteca.excepciones.PersonalNoEncontradoException;
import com.biblioteca.excepciones.PrestamoInvalidoException;

public class App {

    public static int leerEntero(Scanner sc, String mensaje) {
        int numero = 0;
        boolean valido = false;

        while (!valido) {
            try {
                System.out.print(mensaje);
                numero = sc.nextInt();
                sc.nextLine();
                valido = true;
            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un número entero.");
                sc.nextLine();
            }
        }

        return numero;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Biblioteca biblioteca = new Biblioteca("Biblioteca Central Los Libertadores");

        ArrayList<Recurso> recursosRegistrados = new ArrayList<Recurso>();

        Bibliotecario b1 = new Bibliotecario("Marta Rojas", "B001", "Circulación");
        Bibliotecario b2 = new Bibliotecario("Julián Pardo", "B002", "Referencia");

        Auxiliar a1 = new Auxiliar("Diana Cruz", "A001", "Catalogación", "Tarde");

        biblioteca.agregarPersonal(b1);
        biblioteca.agregarPersonal(b2);
        biblioteca.agregarPersonal(a1);

        int opcion = -1;

        while (opcion != 0) {

            System.out.println("\n===== SISTEMA DE BIBLIOTECA =====");
            System.out.println("1. Registrar recurso");
            System.out.println("2. Listar recursos registrados");
            System.out.println("3. Ubicar recurso en una sección");
            System.out.println("4. Registrar préstamo");
            System.out.println("5. Ver recursos de una sección");
            System.out.println("6. Ver préstamos de un bibliotecario");
            System.out.println("7. Dar de baja un recurso de una sección");
            System.out.println("8. Buscar recurso por código");
            System.out.println("9. Reporte general de la biblioteca");
            System.out.println("10. Crear nueva sección");
            System.out.println("0. Salir");

            opcion = leerEntero(sc, "Seleccione una opción: ");

            switch (opcion) {

                case 1: {
                    System.out.println("\n--- REGISTRAR RECURSO ---");
                    System.out.println("1. Libro");
                    System.out.println("2. Recurso digital");

                    int tipo = leerEntero(sc, "Seleccione el tipo de recurso: ");

                    if (tipo != 1 && tipo != 2) {
                        System.out.println("Tipo inválido.");
                        break;
                    }

                    try {
                        System.out.print("Ingrese el título: ");
                        String titulo = sc.nextLine();

                        System.out.print("Ingrese el código: ");
                        String codigo = sc.nextLine();

                        int anio = leerEntero(sc, "Ingrese el año: ");

                        if (tipo == 1) {
                            System.out.print("Ingrese el autor: ");
                            String autor = sc.nextLine();

                            int paginas = leerEntero(sc, "Ingrese el número de páginas: ");

                            Libro libro = new Libro(titulo, codigo, anio, autor, paginas);

                            recursosRegistrados.add(libro);

                            System.out.println("Libro registrado correctamente.");

                        } else {
                            System.out.print("Ingrese el formato: ");
                            String formato = sc.nextLine();

                            int tamano = leerEntero(sc, "Ingrese el tamaño en MB: ");

                            RecursoDigital digital = new RecursoDigital(titulo, codigo, anio, formato, tamano);

                            recursosRegistrados.add(digital);

                            System.out.println("Recurso digital registrado correctamente.");
                        }

                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;
                }

                case 2: {
                    System.out.println("\n--- RECURSOS REGISTRADOS ---");

                    if (recursosRegistrados.isEmpty()) {
                        System.out.println("No hay recursos registrados.");
                    } else {
                        for (int i = 0; i < recursosRegistrados.size(); i++) {
                            System.out.println(recursosRegistrados.get(i).obtenerInfo());
                        }
                    }

                    break;
                }

                case 3: {
                    System.out.println("\n--- UBICAR RECURSO ---");

                    if (recursosRegistrados.isEmpty()) {
                        System.out.println("No hay recursos registrados.");
                        break;
                    }

                    System.out.print("Ingrese el nombre de la sección: ");
                    String nombreSeccion = sc.nextLine();

                    Seccion seccion = biblioteca.buscarSeccion(nombreSeccion);

                    if (seccion == null) {
                        System.out.println("La sección no existe.");
                        break;
                    }

                    System.out.print("Ingrese el código del recurso: ");
                    String codigo = sc.nextLine();

                    Recurso recursoEncontrado = null;

                    for (int i = 0; i < recursosRegistrados.size(); i++) {
                        if (recursosRegistrados.get(i).getCodigo().equals(codigo)) {
                            recursoEncontrado = recursosRegistrados.get(i);
                            break;
                        }
                    }

                    if (recursoEncontrado == null) {
                        System.out.println("El recurso no está registrado.");
                        break;
                    }

                    try {
                        seccion.agregarRecurso(recursoEncontrado);
                    } catch (SeccionLlenaException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;
                }

                case 4: {
                    System.out.println("\n--- REGISTRAR PRÉSTAMO ---");

                    biblioteca.listarBibliotecarios();

                    try {
                        System.out.print("Ingrese el ID del bibliotecario: ");
                        String id = sc.nextLine();

                        Bibliotecario bibliotecario = biblioteca.buscarBibliotecario(id);

                        System.out.print("Ingrese el código del recurso: ");
                        String codigo = sc.nextLine();

                        Recurso recurso = biblioteca.buscarRecurso(codigo);

                        System.out.print("Ingrese la fecha del préstamo: ");
                        String fecha = sc.nextLine();

                        System.out.print("Ingrese el nombre del lector: ");
                        String lector = sc.nextLine();

                        System.out.print("¿Desea agregar una observación? (s/n): ");
                        String respuesta = sc.nextLine();

                        while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
                            System.out.print("Respuesta inválida. Ingrese s o n: ");
                            respuesta = sc.nextLine();
                        }

                        if (respuesta.equalsIgnoreCase("s")) {
                            System.out.print("Ingrese la observación: ");
                            String observacion = sc.nextLine();

                            bibliotecario.registrarPrestamo(fecha, lector, observacion, recurso);

                        } else {
                            bibliotecario.registrarPrestamo(fecha, lector, recurso);
                        }

                    } catch (PersonalNoEncontradoException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (RecursoNoEncontradoException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (PrestamoInvalidoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;
                }

                case 5: {
                    System.out.println("\n--- RECURSOS DE UNA SECCIÓN ---");

                    System.out.print("Ingrese el nombre de la sección: ");
                    String nombre = sc.nextLine();

                    Seccion seccion = biblioteca.buscarSeccion(nombre);

                    if (seccion == null) {
                        System.out.println("La sección no existe.");
                    } else {
                        seccion.listarRecursos();
                    }

                    break;
                }

                case 6: {
                    System.out.println("\n--- PRÉSTAMOS DE UN BIBLIOTECARIO ---");

                    biblioteca.listarBibliotecarios();

                    try {
                        System.out.print("Ingrese el ID del bibliotecario: ");
                        String id = sc.nextLine();

                        Bibliotecario bibliotecario = biblioteca.buscarBibliotecario(id);

                        ArrayList<Prestamo> prestamos = bibliotecario.getPrestamos();

                        if (prestamos.isEmpty()) {
                            System.out.println("Este bibliotecario no tiene préstamos registrados.");
                        } else {
                            for (int i = 0; i < prestamos.size(); i++) {
                                System.out.println(prestamos.get(i).getInfo());
                            }
                        }

                    } catch (PersonalNoEncontradoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;
                }

                case 7: {
                    System.out.println("\n--- DAR DE BAJA UN RECURSO ---");

                    System.out.print("Ingrese el nombre de la sección: ");
                    String nombre = sc.nextLine();

                    Seccion seccion = biblioteca.buscarSeccion(nombre);

                    if (seccion == null) {
                        System.out.println("La sección no existe.");
                        break;
                    }

                    System.out.print("Ingrese el código del recurso: ");
                    String codigo = sc.nextLine();

                    try {
                        seccion.retirarRecurso(codigo);
                    } catch (RecursoNoEncontradoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;
                }

                case 8: {
                    System.out.println("\n--- BUSCAR RECURSO ---");

                    System.out.print("Ingrese el código del recurso: ");
                    String codigo = sc.nextLine();

                    try {
                        Recurso recurso = biblioteca.buscarRecurso(codigo);
                        System.out.println(recurso.obtenerInfo());
                    } catch (RecursoNoEncontradoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;
                }

                case 9: {
                    System.out.println("\n--- REPORTE GENERAL ---");
                    biblioteca.generarReporteGeneral();
                    break;
                }

                case 10: {
                    System.out.println("\n--- CREAR NUEVA SECCIÓN ---");

                    System.out.print("Ingrese el nombre de la sección: ");
                    String nombre = sc.nextLine();

                    int capacidad = leerEntero(sc, "Ingrese la capacidad de la sección: ");

                    try {
                        biblioteca.agregarSeccion(nombre, capacidad);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;
                }

                case 0: {
                    System.out.println("Gracias por utilizar el sistema de biblioteca.");
                    break;
                }

                default: {
                    System.out.println("Opción inválida. Intente nuevamente.");
                }
            }
        }

        sc.close();
    }
}

