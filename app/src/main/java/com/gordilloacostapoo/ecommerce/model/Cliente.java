package com.gordilloacostapoo.ecommerce.model;

import java.util.Objects;
import java.io.Serializable;

public class Cliente implements Serializable{
    private String nombre;
    private String correo;
    private static long serialVersionUID = 1L;

    private boolean admin;
    private String password;
    
    public Cliente(String nombre, String correo, boolean admin, String password){
        this.nombre=nombre;
        this.correo=correo;
        this.admin=admin;
        this.password=password;
    }

    public Cliente(){
        nombre = null;
        correo = null;
        admin = false;
        password = null;
    }

    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    public String getCorreo(){
        return correo;
    }
    public void setCorreo(String correo){
        this.correo=correo;
    }
    public boolean getAdmin() {return admin; }
    public void setAdmin(boolean admin) { this.admin = admin; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
    return "Cliente:\n"+
     "Nombre: " + nombre + "\n"+
     "Correo: " + correo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        
        Cliente otroCliente = (Cliente) obj;
        
        return this.correo != null && this.correo.equalsIgnoreCase(otroCliente.correo);
    }

    @Override
    public int hashCode() {
        return Objects.hash((correo != null ? correo.toLowerCase() : null));
    }
}