package com.gordilloacostapoo.ecommerce.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.io.Serializable;

public class Pedido implements Serializable{
    private Cliente cliente;
    private LocalDate fecha;
    private ArrayList<ItemPedido> items;
    private static long serialVersionUID = 1L;

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.fecha = LocalDate.now();
        this.items = new ArrayList<>();
    }

    public Cliente getCliente(){ 
        return cliente; 
    }
    public void setCliente(Cliente cliente){ 
        this.cliente = cliente; 
    }
    public LocalDate getFecha() { 
        return fecha; 
    }
    public void setFecha(LocalDate fecha){ 
        this.fecha = fecha; 
    }
    public ArrayList<ItemPedido> getItems(){ 
        return items; 
    }

    public void agregarItem(Producto productodeseado, int cantidad) throws TiendaException {
        if(cantidad == 0){
            throw new TiendaException("Error: no se puede agregar un pedido con 0 cantidad de productos.");
        }
        double pu = productodeseado.aplicarDescuento();
        double pf = Math.round(Producto.calcularPrecioFinal(pu));
        items.add(new ItemPedido(productodeseado, cantidad, pf));
    }

    public double calcularTotal() {
        double total = 0.0;
        for (ItemPedido it : items){
            total += it.getSubtotal();
        } 
        return Math.round(total);
    }

    @Override
    public String toString() {
        return "Cliente: " + cliente.getNombre() +
               "\nFecha: " + fecha +
               "\nProductos comprados: \n" + items +
               "\nTotal: " + calcularTotal();
    }
}