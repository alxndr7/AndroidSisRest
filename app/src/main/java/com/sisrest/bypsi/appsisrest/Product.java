package com.sisrest.bypsi.appsisrest;

/**
 * POJO de un producto
 */
public class Product {


    /**
     * Nombre del producto
     */
    private String nombre;
    /**
     * Especificaciones del producto
     */
    private String dni;
    /**
     * Precio del producto
     */
    private String fecha;
    /**
     * Valoraciones del producto
     */
    private String cantidad;
    private float rating;
    /**
     * Identificador de la imagen para miniatura
     */
    private int idThumbnail;

    public Product(String nombre, String dni, String fecha, String cantidad) {
        this.nombre = nombre;
        this.dni = dni;
        this.fecha = fecha;
        this.cantidad = cantidad;
    }

    public Product() {
    }

    public String getNombre() {
        return nombre;
    }

    public String getDni() {
        return dni;
    }

    public String getFecha() {
        return fecha;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

}
