package com.gordilloacostapoo.ecommerce.model;

import java.io.*;
import java.util.ArrayList;

public class TiendaVirtual implements Serializable{

    private ArrayList<Producto> catalogo;
    private ArrayList<Pedido> pedidosConfirmados;

    public TiendaVirtual() {
        catalogo = new ArrayList<>();
        pedidosConfirmados = new ArrayList<>();
    }
    
    public TiendaVirtual(ArrayList<Producto> catalogo, ArrayList<Cliente> clientes, ArrayList<Pedido> pedidosConfirmados){
        this.catalogo = catalogo;
        this.pedidosConfirmados = pedidosConfirmados;
    }

    public void registrarProducto(Producto nuevoProducto) {
        this.catalogo.add(nuevoProducto);
    }

    public Producto buscarProductoPorNombre(String nombre) throws TiendaException {
        for (Producto p : this.catalogo) {
            if (p.getnombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        throw new TiendaException("Error: Producto no encontrado: " + nombre); 
    }
    
    public void buscarProductoPorTipo(String tipo) throws TiendaException{
        ArrayList<Producto> prods = new ArrayList<>();
        for (Producto p : this.catalogo) {
            if (p.gettipo().equalsIgnoreCase(tipo)) {
                prods.add(p);
            }
        }
        if(prods.isEmpty()){
            throw new TiendaException("Error: no se encontraron productos para mostrar.");
        }
        for(Producto prod : prods){
                System.out.println("Nombre: " + prod.getnombre().toLowerCase() + " | Precio: " + prod.getprecio() + " | Stock: " + prod.getstock());
        }
    }
    
    public void listarProds() throws TiendaException{
        System.out.println("-------Lista de Productos-------");
        if(getCatalogo().isEmpty()){
            throw new TiendaException("Error: no se encontraron productos para mostrar.");
        }
        for(Producto prod : getCatalogo()){
                System.out.println("Nombre: " + prod.getnombre().toLowerCase() + " | Precio: " + prod.getprecio() + " | Stock: " + prod.getstock());
        }
    }

    public void confirmarPedido(Pedido pedido) throws TiendaException {

        for (ItemPedido item : pedido.getItems()) {
            Producto productoEnCarrito = item.getProducto();
            int cantidadDeseada = item.getCantidad();
            
            if (productoEnCarrito.getstock() < cantidadDeseada) {
                throw new TiendaException("Error: Stock insuficiente para '" + productoEnCarrito.getnombre() + 
                        "'.\nSolicitados: " + cantidadDeseada + ", Disponible: " 
                        + productoEnCarrito.getstock()); 
            }
        }

        for (ItemPedido item : pedido.getItems()) {
            int nuevoStock = item.getProducto().getstock() - item.getCantidad();
            item.getProducto().setstock(nuevoStock);
        }
        
        Pedido pedidoConfirmado = pedido;
        
        pedidoConfirmado.setFecha(java.time.LocalDate.now());
        this.pedidosConfirmados.add(pedidoConfirmado);
    }

    public void mostrarReporteVentas() {
        System.out.println("REPORTE DE VENTAS TOTALES");
        if (pedidosConfirmados.isEmpty()) {
            System.out.println("No se han realizado ventas.");
        }else{
            double totalAcumulado = 0;
            for (Pedido p : this.pedidosConfirmados) {
                System.out.println("Cliente: " + p.getCliente().getNombre() + 
                        " | Total: $" + p.calcularTotal());
                totalAcumulado += p.calcularTotal();
            }
            System.out.println("###################################");
            System.out.println("VENTA TOTAL ACUMULADA: $" + Math.round(totalAcumulado));
        }
    }
    
    /*public void listarPedidos(){
        if(pedidosConfirmados.isEmpty()){
            System.out.println("No hay pedidos para listar.");
        } else{
            System.out.println("-------Lista de Pedidos Confirmados-------");
            for(Pedido pedido : pedidosConfirmados){
                System.out.println(pedido);
            }
        }
    }

    public void listarPedidosPorCliente(Cliente client){
        if(pedidosConfirmados.isEmpty()){
            System.out.println("No hay pedidos para listar.");
        } else{
            ArrayList<Pedido> pedidosEncontrados = new ArrayList<>();
            for(Pedido pedido : pedidosConfirmados){
                if(pedido.getCliente().getCorreo().equalsIgnoreCase(client.getCorreo())){
                    pedidosEncontrados.add(pedido);
                }
            }
            if(pedidosEncontrados.isEmpty()){
                System.out.println("El cliente no tiene ningun pedido confirmado registrado.");
            } else{
                System.out.println("Pedidos confirmados de: " + client.getNombre());
                for(Pedido pE : pedidosEncontrados){
                    System.out.println(pE);
                }
            }
        }
    }*/
    
    public void listarProdsMasVendidos(){
        if(pedidosConfirmados.isEmpty()){
            System.out.println("No hay productos vendidos aun.");
        }else{
            ArrayList<ProductoVenta> listaVentas = new ArrayList<>();
            
            for(Pedido pedidoConfirmado : pedidosConfirmados){
                for (ItemPedido item : pedidoConfirmado.getItems()) {

                    Producto producto = item.getProducto();
                    int cantidad = item.getCantidad();

                    boolean encontrado = false;

                    for (ProductoVenta pv : listaVentas) {
                        if (pv.getProducto().getcodigoSKU().equals(producto.getcodigoSKU())) {
                            pv.sumarCantidad(cantidad);
                            encontrado = true;
                            break;
                        }
                    }

                    if (!encontrado) {
                        listaVentas.add(new ProductoVenta(producto, cantidad));
                    }
                }
            }
            
            listaVentas.sort((p1, p2) -> Integer.compare(p2.getCantidad(), p1.getCantidad()));
            
            System.out.println("Lista de productos mas vendidos: ");
            for(ProductoVenta pv : listaVentas){
                System.out.println("Producto: " + pv.getProducto().getnombre() + ", cantidad vendida: " + pv.getCantidad());
            }
        }
    }

    public boolean validarCodigoSKU(String codigoSKU){
        if(codigoSKU.length() == 8){
            for(Producto p : catalogo){
                if(p.getcodigoSKU().equalsIgnoreCase(codigoSKU)){
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean validarNombreProd(String nombreProd){
        for(Producto p : catalogo){
            if(p.getnombre().equalsIgnoreCase(nombreProd)){
                return false;
            }
        }
        return true;
    }
    
    /*public void guardarTiendaVirtual(){
        String ruta = "Tienda.dat";
        
        try(FileOutputStream fos = new FileOutputStream(ruta) ; ObjectOutputStream oos = new ObjectOutputStream(fos)){
            
            oos.writeObject(catalogo);
            oos.writeObject(clientes);
            oos.writeObject(pedidosConfirmados);
            
            System.out.println("Se guardaron los datos de la tienda.");
            
        } catch(IOException e){
            e.printStackTrace();
        }
    }*/
    
    /*public static TiendaVirtual cargarTiendaVirtual(){
        String ruta = "Tienda.dat";
        File archivo = new File(ruta);
        
        if(!archivo.exists()){
            System.out.println("Archivo no encontrado. Se inicia una tienda nueva.");
            return new TiendaVirtual();
        }
        
        try(FileInputStream fis = new FileInputStream(ruta) ; ObjectInputStream ois = new ObjectInputStream(fis)){
            
            ArrayList<Producto> catalogo = (ArrayList<Producto>) ois.readObject();
            ArrayList<Cliente> clientes = (ArrayList<Cliente>) ois.readObject();
            ArrayList<Pedido> pedidosConfirmados = (ArrayList<Pedido>) ois.readObject();
            
            return new TiendaVirtual(catalogo, clientes, pedidosConfirmados);
            
        } catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        System.out.println("Ocurrio un error en la lectura del archivo de datos, se inicializa una tienda nueva.");
        return new TiendaVirtual();
    }*/
    
    public ArrayList<Producto> getCatalogo() {
        return this.catalogo;
    }
    
    public ArrayList<Pedido> getPedidosConfirmados() {
        return this.pedidosConfirmados;
    }
}