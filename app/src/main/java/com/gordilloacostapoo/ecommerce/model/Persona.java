package com.gordilloacostapoo.ecommerce.model;

public class Persona {
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private String telefono;
    private int edad;

    public Persona(String nombre, String apellido, String correoElectronico, String telefono, int edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.edad = edad;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public int getEdad() {
        return edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setApellido(String apellido){
        this.apellido = apellido;
    }

    public void setCorreoElectronico(String correoElectronico){
        this.correoElectronico = correoElectronico;
    }

    public void setEdad(int edad){
        this.edad = edad;
    }

    public void setTelefono(String telefono){
        this.telefono = telefono;
    }

    public String toText() {
        return "Nombre: " + nombre +
                "\nApellido: " + apellido +
                "\nCorreo: " + correoElectronico +
                "\nTeléfono: " + telefono +
                "\nEdad: " + edad;
    }
}
