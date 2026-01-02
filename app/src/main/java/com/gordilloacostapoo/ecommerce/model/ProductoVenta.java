package com.gordilloacostapoo.ecommerce.model;

import java.io.Serializable;

public class ProductoVenta implements Serializable {
    private Producto producto;
    private int cantidad;

    public ProductoVenta(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }
    
    public Producto getProducto(){
        return producto;
    }
    
    public int getCantidad(){
        return cantidad;
    }
    
    public void sumarCantidad(int cant){
        cantidad += cant;
    }
}