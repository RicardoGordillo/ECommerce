package com.gordilloacostapoo.ecommerce.model;

import java.util.HashSet;
import java.io.Serializable;

abstract public class Producto implements Descontable, Serializable{

    private String codigoSKU;
    private String nombre;
    private String marca;
    private double precio;
    private int stock;
    private String tipo;
    private static long serialVersionUID = 1L;

    public Producto(String codigoSKU,
                    String nombre,
                    String marca,
                    double precio,
                    int stock){
        this.marca = marca;
        this.codigoSKU = codigoSKU;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    //Encapsulamiento
    public String getmarca(){
        return marca;
    }
    public String getcodigoSKU(){
        return codigoSKU;
    }
    public String getnombre(){
        return nombre;
    }
    public double getprecio(){
        return precio;
    }
    public int getstock(){
        return stock;
    }
    public String gettipo(){
        return tipo;
    }

    public void setmarca(String marca){
        this.marca = marca;
    }
    public void setcodigoSKU(String codigoSKU){
        this.codigoSKU = codigoSKU;
    }
    public void setnombre(String nombre){
        this.nombre = nombre;
    }
    public void setprecio(double precio){
        this.precio = precio;
    }
    public void setstock(int stock){
        this.stock = stock;
    }
    public void settipo(String tipo){
        this.tipo = tipo;
    }

    //Métodos
    public static double calcularPrecioFinal(double valor){
        return valor*1.15; //IVA del 15%
    }
    public void reducirStock(int cantidad){
        if (cantidad > 0 && stock >= cantidad){
            stock -= cantidad;
        }
    }
    public void aumentarStock(int cantidad){
        if (cantidad > 0){
            stock += cantidad;
        }
    }

    public String toString(){
        return "CódigoSKU: " + codigoSKU + "\n" +
                "Nombre: " + nombre + "\n" +
                "Marca: " + marca + "\n" +
                "Precio sin IVA ni descuento: " + precio + "\n" +
                "Stock: " + stock;
    }
    
    @Override
    public abstract double aplicarDescuento();
        
}