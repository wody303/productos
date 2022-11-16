package com.emergente.modelo;

public class Producto {
    private int id;
    private String producto;
    private double precio;
    private int cantidad;
    
    public Producto(int id, String descripcion, String cantidad, int precio) {
        this.id = 0;
        this.producto = " ";
        this.cantidad = 0;
        this.precio = 0;
    }

    public Producto() {
     
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String descripcion) {
        this.producto = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    

    
}
