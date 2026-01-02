package com.gordilloacostapoo.ecommerce.model;

import java.util.HashSet;
import java.util.Scanner;

public class Electronico extends Producto{

    private String modelo;
    private String especificaciones;
    private int capacidadBateria;
    private String sistemaOperativo;

    public Electronico(String codigoSKU,
                       String nombre,
                       String marca,
                       double precio,
                       int stock,
                       String modelo,
                       String especificaciones,
                       int capacidadBateria,
                       String sistemaOperativo){
        super(codigoSKU, nombre, marca, precio, stock);
        this.modelo = modelo;
        this.especificaciones = especificaciones;
        this.capacidadBateria = capacidadBateria;
        this.sistemaOperativo = sistemaOperativo;
        settipo("Electronico");
    }

    public void setModelo(String modelo){
        this.modelo = modelo;
    }

    public String getModelo(){
        return modelo;
    }

    public void setEspecificaciones(String especificaciones){
        this.especificaciones = especificaciones;
    }

    public String getEspecificaciones(){
        return especificaciones;
    }

    public void setCapacidadBateria(int capacidadBateria){
        this.capacidadBateria = capacidadBateria;
    }

    public int getCapacidadBateria(){
        return capacidadBateria;
    }

    public void setSistemaOperativo(String sistemaOperativo){
        this.sistemaOperativo = sistemaOperativo;
    }

    public String getSistemaOperativo(){
        return sistemaOperativo;
    }

    @Override
    public String toString(){
        return super.toString() + "\nModelo: " + modelo +
                "\nEspecificaciones: " + especificaciones +
                "\nCapacidad de bateria: " + capacidadBateria + "mAh" +
                "\nSistema operativo: " + sistemaOperativo;

    }

    @Override
    public double aplicarDescuento() {
        return getprecio()*0.95;
    }
    
    /*public static Electronico agregarElectronico(HashSet codigosSKU, HashSet nombres, Scanner sc) throws TiendaException{
        
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

                sc.nextLine();
                System.out.println("Ingrese el stock disponible: ");
                int stock = Validacion.validarValorInt(sc);

                boolean disponible = (stock>0);

                sc.nextLine();
                System.out.println("Ingrese una descripcion del producto: ");
                String descripcion = sc.nextLine();

                System.out.println("Ingrese el modelo del producto: ");
                String modelo = sc.nextLine();

                System.out.println("Ingrese las especificaciones del producto: ");
                String especificaciones = sc.nextLine();

                System.out.println("Ingrese la capacidad de la bateria (en mAh): ");
                int capacidadBateria = Validacion.validarValorInt(sc);

                sc.nextLine();
                System.out.println("Ingrese el nombre del sistema operativo: ");
                String sistemaOperativo = sc.nextLine();

                Electronico e = new Electronico(codigoSKU, nombre, marca, precio, stock, descripcion, disponible, modelo, especificaciones, capacidadBateria, sistemaOperativo);

                return e;
            }
            throw new TiendaException("Error: ya existe un producto con ese nombre.");
        }
        throw new TiendaException("Error: el codigoSKU no es valido, debe tener 8 caracteres de largo.");
    }*/
}