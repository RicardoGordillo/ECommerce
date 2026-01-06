package com.gordilloacostapoo.ecommerce.model;

import static java.security.AccessController.getContext;

import android.widget.Toast;

import com.gordilloacostapoo.ecommerce.PerfilFragment;
import com.gordilloacostapoo.ecommerce.data.DBHelper;
import com.gordilloacostapoo.ecommerce.session.SessionManager;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class DataManager {
    public static Cliente clienteLogueado;
    public static TiendaVirtual tienda = new TiendaVirtual();

    public static void cargarDatosIniciales() {
        if(tienda.getCatalogo().isEmpty()){
            tienda.registrarProducto(new Electronico("DJWNDNWD", "Nintendo Switch", "Nintendo",
                    299.99, 10, "Switch Lite", "Consola de videojuegos",
                    10000, "FreeDSB"));
            tienda.registrarProducto(new Ropa("HJBSJHWG", "Camiseta", "Nike",
                    29.99, 50, "M", "Azul", "Manga corta"));
            LocalDate fechaCaducidad = LocalDate.parse("2026-02-19");
            LocalDate fechaElaboracion = LocalDate.parse("2025-12-25");
            tienda.registrarProducto(new Comida("NDUWNDWN", "Yogur Light", "Toni",
                    4.00, 30, fechaCaducidad, fechaElaboracion, 100));
        }
    }

    public static Pedido carrito;
}
