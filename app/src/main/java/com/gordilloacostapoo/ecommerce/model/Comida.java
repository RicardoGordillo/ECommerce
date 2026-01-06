package com.gordilloacostapoo.ecommerce.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.ArrayList;

public class Comida extends Producto implements Descontable{

    private LocalDate fechaCaducidad;
    private LocalDate fechaElaboracion;
    private int calorias;

    public Comida(String codigoSKU,
                  String nombre,
                  String marca,
                  double precio,
                  int stock,
                  LocalDate fechaCaducidad,
                  LocalDate fechaElaboracion,
                  int calorias) {
        super(codigoSKU, nombre, marca, precio, stock);
        this.fechaCaducidad = fechaCaducidad;
        this.fechaElaboracion = fechaElaboracion;
        this.calorias = calorias;
        settipo("Comida");
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaElaboracion(LocalDate fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public LocalDate getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }

    public int getCalorias() {
        return calorias;
    }

    @Override
    public String toString(){
        return super.toString() +
                "\nCalorias: " + calorias + "kcal" +
                "\nFecha de Elaboracion: " + fechaElaboracion +
                "\nFecha de Vencimiento: " + fechaCaducidad;
    }

    @Override
    public double aplicarDescuento() {
        return getprecio()*0.85;
    }
    
    /*public static Comida agregarComida(HashSet codigosSKU, HashSet nombres, Scanner sc) throws TiendaException{
        
        System.out.println("Ingrese el codigoSKU del producto: ");
        String codigoSKU = sc.nextLine();
        boolean valido = Producto.validarCodigoSKU(codigoSKU, codigosSKU);
        
        if(valido){
            System.out.println("Ingrese el nombre del producto: ");
            String nombre = sc.nextLine();
            boolean valido1 = Producto.validarNombreProd(nombre, nombres);
            
            if(valido1){
                System.out.println("Ingrese la marca del producto: ");
                String marca = sc.nextLine();

                System.out.println("Ingrese el precio del producto: ");
                double precio = Validacion.validarValorDouble(sc);

                System.out.println("Ingrese el stock disponible: ");
                int stock = Validacion.validarValorInt(sc);

                boolean disponible = (stock > 0);

                sc.nextLine();            
                System.out.println("Ingrese una descripcion del producto: ");
                String descripcion = sc.nextLine();

                System.out.println("Ingrese la fecha de caducidad del producto (formato: yyyy-mm-dd): ");
                LocalDate fechaCaducidad = Validacion.validarFecha(sc);

                System.out.println("Ingrese la fecha de elaboracion del producto (formato: yyyy-mm-dd): ");
                LocalDate fechaElaboracion = Validacion.validarFecha(sc);

                System.out.println("Ingrese el tipo de comida: ");
                String tipoComida = sc.nextLine();

                System.out.println("Ingrese la cantidad de calorias que contiene (en kcal): ");
                int calorias = Validacion.validarValorInt(sc);
                sc.nextLine();

                Comida c = new Comida(codigoSKU, nombre, marca, precio, stock, descripcion, disponible, fechaCaducidad, tipoComida, fechaElaboracion, calorias);

                return c;
            }
            throw new TiendaException("Error: ya existe un producto con ese nombre.");
        }
        throw new TiendaException("Error: el codigoSKU no es valido, debe tener 8 caracteres de largo.");
    }*/
}