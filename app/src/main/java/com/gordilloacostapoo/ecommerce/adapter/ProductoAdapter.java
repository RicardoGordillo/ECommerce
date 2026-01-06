package com.gordilloacostapoo.ecommerce.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gordilloacostapoo.ecommerce.R;
import com.gordilloacostapoo.ecommerce.model.Comida;
import com.gordilloacostapoo.ecommerce.model.DataManager;
import com.gordilloacostapoo.ecommerce.model.ItemPedido;
import com.gordilloacostapoo.ecommerce.model.Producto;
import com.gordilloacostapoo.ecommerce.model.Ropa;
import com.gordilloacostapoo.ecommerce.model.TiendaException;

import java.util.ArrayList;
import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private List<Producto> listaOriginal;
    private List<Producto> listaFiltrada;

    public ProductoAdapter(List<Producto> listaProductos) {
        this.listaOriginal = listaProductos;
        this.listaFiltrada = new ArrayList<>(listaProductos);
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto p = listaFiltrada.get(position);
        holder.bind(p);
    }

    @Override
    public int getItemCount() {
        return listaFiltrada.size();
    }

    // Lógica para filtrar por nombre
    public void filtrar(String texto) {
        listaFiltrada.clear();
        if (texto.isEmpty()) {
            listaFiltrada.addAll(listaOriginal);
        } else {
            for (Producto p : listaOriginal) {
                if (p.getnombre().toLowerCase().contains(texto.toLowerCase())) {
                    listaFiltrada.add(p);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvSku, tvPrecio, tvStock;
        EditText etCantidad;
        Button btnMas, btnMenos, btnAgregar;
        ImageView imgCategoria;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreProducto);
            tvSku = itemView.findViewById(R.id.tvSKU);
            tvPrecio = itemView.findViewById(R.id.tvPrecioProducto);
            tvStock = itemView.findViewById(R.id.tvStock);
            etCantidad = itemView.findViewById(R.id.etCantidadItem);
            btnMas = itemView.findViewById(R.id.btnMas);
            btnMenos = itemView.findViewById(R.id.btnMenos);
            btnAgregar = itemView.findViewById(R.id.btnAgregarCarrito);
            imgCategoria = itemView.findViewById(R.id.imgCategoria);
        }

        public void bind(Producto p) {
            tvNombre.setText(p.getnombre());
            tvSku.setText("SKU: " + p.getcodigoSKU());
            tvPrecio.setText("$ " + String.format("%.2f", p.getprecio()));
            tvStock.setText("Stock: " + p.getstock());

            // Lógica de los botones + y -
            btnMas.setOnClickListener(v -> {
                int cant = Integer.parseInt(etCantidad.getText().toString());
                if (cant < p.getstock()) { // No pedir más de lo que hay
                    etCantidad.setText(String.valueOf(cant + 1));
                }
            });

            btnMenos.setOnClickListener(v -> {
                int cant = Integer.parseInt(etCantidad.getText().toString());
                if (cant > 1) {
                    etCantidad.setText(String.valueOf(cant - 1));
                }
            });

            // Acción de agregar al carrito
            btnAgregar.setOnClickListener(v -> {
                if(etCantidad.getText().toString().isEmpty()) return;
                int cantidadFinal = Integer.parseInt(etCantidad.getText().toString());
                try {
                    if(cantidadFinal > p.getstock()) {
                        Toast.makeText(itemView.getContext(), "No hay suficiente stock", Toast.LENGTH_SHORT).show();
                        etCantidad.setText(String.valueOf(p.getstock()));
                        return;
                    }
                    DataManager.carrito.agregarItem(p, cantidadFinal);
                    Toast.makeText(itemView.getContext(), "Agregado: " + p.getnombre(), Toast.LENGTH_SHORT).show();
                } catch (TiendaException e) {
                    Toast.makeText(itemView.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            // Cambiar icono según subclase (POO)
            if (p instanceof Comida) imgCategoria.setImageResource(R.drawable.ic_food);
            else if (p instanceof Ropa) imgCategoria.setImageResource(R.drawable.ic_checkroom);
            else imgCategoria.setImageResource(R.drawable.ic_bolt);
        }
    }
}