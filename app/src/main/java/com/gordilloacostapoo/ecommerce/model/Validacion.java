package com.gordilloacostapoo.ecommerce.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import java.util.ArrayList;

public class Validacion implements Serializable {

    public static double validarValorDouble(String entrada) throws NumberFormatException {
        double valor = Double.parseDouble(entrada);
        if (valor < 0) {
            throw new IllegalArgumentException("El valor no puede ser menor que 0");
        }
        return valor;
    }

    public static LocalDate validarFecha(String entrada) throws DateTimeParseException {
        // Simplemente intentamos parsear. Si falla, la Activity capturará el error.
        return LocalDate.parse(entrada);
    }

    public static int validarValorInt(String texto) throws Exception {
        // 1. Validar si está vacío
        if (texto.isEmpty()) {
            throw new Exception("El campo no puede estar vacío");
        }

        try {
            int valor = Integer.parseInt(texto);

            // 2. Validar rango (como hacías en tu código original)
            if (valor < 0) {
                throw new Exception("El valor no puede ser menor que 0");
            }

            return valor;
        } catch (NumberFormatException e) {
            throw new Exception("Debe ingresar un número entero válido");
        }
    }
        
    /*public static Cliente validarCorreo(ArrayList<Cliente> clientes, Scanner sc) throws TiendaException{
        System.out.println("Ingrese su correo de cliente: ");
        String correo = sc.nextLine();
        for(Cliente clientComparacion : clientes){
            if(clientComparacion.getCorreo().equalsIgnoreCase(correo)){
                return clientComparacion;
            }
        }
        throw new TiendaException("Error: El correo introducido no esta registrado");
    }*/
}