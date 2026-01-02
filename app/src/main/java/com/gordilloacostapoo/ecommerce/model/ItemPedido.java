package com.gordilloacostapoo.ecommerce.model;

import java.io.Serializable;

public class ItemPedido implements Serializable{
    private Producto producto;
    private int cantidad;
    private double precioUnitario;
    private static long serialVersionUID = 1L;

    public ItemPedido(Producto producto, int cantidad, double precioUnitario){
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public Producto getProducto(){ 
        return producto; 
    }
    public void setProducto(Producto producto) { 
        this.producto = producto; 
    }

    public int getCantidad(){
        return cantidad; 
    }
    public void setCantidad(int cantidad){ 
        this.cantidad = cantidad; 
    }

    public double getPrecioUnitario(){ 
        return precioUnitario; 
    }
    public void setPrecioUnitario(double precioUnitario){ 
        this.precioUnitario = precioUnitario; 
    }

    public double getSubtotal() {
        return cantidad * precioUnitario;
    }

    @Override
    public String toString() {
        return "Producto: " + producto.getnombre() +
               "\nCantidad: " + cantidad +
               "\nPrecio Unitario: " + precioUnitario +
               "\nSubtotal: " + getSubtotal();
    }
}