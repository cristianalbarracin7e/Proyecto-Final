package com.biblioteca.modelo;
import com.biblioteca.excepciones.PrestamoInvalidoException;
import java.util.ArrayList;
public class Bibliotecario extends Personal {
    private ArrayList <Prestamo> prestamos;

    public Bibliotecario (String nombre, String id, String area){
        super(nombre, id, area);
        this.prestamos = new ArrayList<>();
    }
    public void registrarPrestamo (String fecha, String lector, Recurso recurso) throws PrestamoInvalidoException{
        validarDatosPrestamo (fecha, lector, recurso);
        Prestamo p = new Prestamo(fecha, "Prestamo estandar",lector, recurso, this);
        prestamos.add(p);
        System.out.println("Prestamo Registrado "+recurso.getTitulo()+ "a" + lector);
    }
    public void registrarPrestamo (String fecha, String lector, String observacion, Recurso recurso) throws PrestamoInvalidoException{
        validarDatosPrestamo (fecha, lector, recurso);
        if (observacion == null || observacion.trim().isEmpty()){
        throw new PrestamoInvalidoException("la observacion no puede estar vacia. ");
        }
        Prestamo p = new Prestamo(fecha, observacion, lector, recurso, this);
        prestamos.add(p);
        System.out.println("Prestamo Registrado"+ recurso.getTitulo()+ "a" + lector + "("+ observacion + ")");
    }
    private void validarDatosPrestamo (String fecha, String lector, Recurso recurso) throws PrestamoInvalidoException{
        if (fecha == null || fecha.trim().isEmpty()) throw new PrestamoInvalidoException (" la fecha no puede estar vacia");
        if (lector == null || lector.trim().isEmpty()) throw new PrestamoInvalidoException("Debe indicarse el nombre del lector. ");
        if (recurso == null) throw new PrestamoInvalidoException("Debe indicarse un recurso valido. ");
    }
    @Override 
    public String generarReporte(){
    return "=== Bibliotecario==='\nNombre: " + getNombre() + "\nID: " + getArea() + "\nPrestamos registrados: " + prestamos.size();
    }
    public ArrayList<Prestamo> getPrestamos(){
        return prestamos;
    }
    
}
