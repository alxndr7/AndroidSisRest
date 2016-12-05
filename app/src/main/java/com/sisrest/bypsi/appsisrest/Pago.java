package com.sisrest.bypsi.appsisrest;

/**
 * Created by Alexander on 4/12/2016.
 */

public class Pago {

    private String nombre;
    private String dni;
    private String pago;
    private String fecha;
    private String cantidadPago;
    private String cantidadDisponible;

    public Pago(String nombre, String dni, String pago, String fecha, String cantidadPago, String cantidadDisponible) {
        this.nombre = nombre;
        this.dni = dni;
        this.pago = pago;
        this.fecha = fecha;
        this.cantidadPago = cantidadPago;
        this.cantidadDisponible = cantidadDisponible;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCantidadPago() {
        return cantidadPago;
    }

    public void setCantidadPago(String cantidadPago) {
        this.cantidadPago = cantidadPago;
    }

    public String getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(String cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
}
