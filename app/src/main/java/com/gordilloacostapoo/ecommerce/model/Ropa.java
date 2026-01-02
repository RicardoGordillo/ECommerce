package com.gordilloacostapoo.ecommerce.model;

import java.util.HashSet;
import java.util.Scanner;

public class Ropa extends Producto implements Descontable{

    private String talla;
    private String color;
    private String diseno;

    public Ropa(String codigoSKU,
                String nombre,
                String marca,
                double precio,
                int stock,
                String talla,
                String color,
                String diseno) {
        super(codigoSKU, nombre, marca, precio, stock);
        this.talla = talla;
        this.color = color;
        this.diseno = diseno;
        settipo("Ropa");
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getTalla() {
        return talla;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setDiseno(String diseno) {
        this.diseno = diseno;
    }

    public String getDiseno() {
        return diseno;
    }

    @Override
    public String toString(){
        return super.toString() +
                "\nTalla: " + talla +
                "\nColor: " + color +
                "\nDiseno: " + diseno;
    }

    @Override
    public double aplicarDescuento() {
        return getprecio()*0.90;
    }
    
    /*public static Ropa agregarRopa(HashSet codigosSKU, HashSet nombres, Scanner sc) throws TiendaException{
        String[] tallas = {"XS", "S", "M", "L", "XL"};
        
        System.out.println("Ingrese el codigoSKU del producto: ");
        String codigoSKU = sc.nextLine();
        boolean valido = Producto.validarCodigoSKU(codigoSKU, codigosSKU);
        
        if(valido){
            System.out.println("Ingrese el nombre del producto: ");
            String nombre = sc.nextLine();
            boolean valido2 = Producto.validarNombreProd(nombre, nombres);
            
            if(valido2){
                System.out.println("Ingrese la marca del producto: ");
                String marca = sc.nextLine();

                System.out.println("Ingrese el precio del producto: ");
                double precio = Validacion.validarValorDouble(sc);

                sc.nextLine();
                System.out.println("Ingrese el stock disponible: ");
                int stock = Validacion.validarValorInt(sc);

                boolean disponible;

                if(stock == 0){
                    disponible = false;
                } else{
                    disponible = true;
                }

                sc.nextLine();
                System.out.println("Ingrese una descripcion del producto: ");
                String descripcion = sc.nextLine();

                System.out.println("Ingrese la talla del producto: ");
                String talla = sc.nextLine();

                boolean valido1 = false;

                while(!valido1){
                    for(String talla1 : tallas){
                        if(talla.equalsIgnoreCase(talla1)){
                            valido1 = true;
                            break;
                        }
                    }
                    if(!valido1){
                        System.out.println("Talla invalida. Intente nuevamente (opciones validas: XS, S, M, L, XL): ");
                        talla = sc.nextLine(); 
                    }
                }

                System.out.println("Ingrese el color del producto: ");
                String color = sc.nextLine();

                System.out.println("Ingrese el diseno del producto: ");
                String diseno = sc.nextLine();

                Ropa r = new Ropa(codigoSKU, nombre, marca, precio, stock, descripcion, disponible, talla, color, diseno);

                return r;
            }
            throw new TiendaException("Error: ya existe un producto con ese nombre.");
        }
        throw new TiendaException("Error: el codigoSKU no es valido, debe tener 8 caracteres de largo.");
    }*/
}