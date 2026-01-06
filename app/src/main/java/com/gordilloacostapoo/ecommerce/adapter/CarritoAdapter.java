package com.gordilloacostapoo.ecommerce.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gordilloacostapoo.ecommerce.R;
import com.gordilloacostapoo.ecommerce.model.Comida;
import com.gordilloacostapoo.ecommerce.model.ItemPedido;
import com.gordilloacostapoo.ecommerce.model.Producto;
import com.gordilloacostapoo.ecommerce.model.Ropa;

import java.util.ArrayList;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>{

    private ArrayList<ItemPedido> items;
    private OnCarritoChangeListener listener;

    public interface OnCarritoChangeListener {
        void onUpdateTotal();
    }

    public CarritoAdapter(ArrayList<ItemPedido> items, OnCarritoChangeListener listener){
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrito, parent, false);
        return new CarritoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CarritoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvSku, tvSubtotal;
        EditText etCantidad;
        Button btnMas, btnMenos;
        ImageView imgCategoria;

        public CarritoViewHolder(@NonNull View itemView){
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreCart);
            tvSku = itemView.findViewById(R.id.tvSKUCart);
            tvSubtotal = itemView.findViewById(R.id.tvSubtotalItem);
            etCantidad = itemView.findViewById(R.id.etCantidadCart);
            btnMas = itemView.findViewById(R.id.btnMasCart);
            btnMenos = itemView.findViewById(R.id.btnMenosCart);
            imgCategoria = itemView.findViewById(R.id.imgCategoriaCart);
        }

        public void bind(ItemPedido item) {
            Producto p = item.getProducto();
            tvNombre.setText(p.getnombre());
            tvSku.setText("SKU: " + p.getcodigoSKU());
            etCantidad.setText(String.valueOf(item.getCantidad()));

            tvSubtotal.setText("$ " + String.format("%.2f", item.getSubtotal()));

            btnMas.setOnClickListener(v -> {
                if(item.getCantidad() < p.getstock()) {
                    item.setCantidad(item.getCantidad() + 1);
                    actualizarUI(item);
                }
            });

            btnMenos.setOnClickListener(v -> {
                if(item.getCantidad() > 0) {
                    item.setCantidad(item.getCantidad() - 1);
                    actualizarUI(item);
                } else {
                    items.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    listener.onUpdateTotal();
                }
            });

            if(p instanceof Comida) imgCategoria.setImageResource(R.drawable.ic_food);
            else if (p instanceof Ropa) imgCategoria.setImageResource(R.drawable.ic_checkroom);
            else imgCategoria.setImageResource(R.drawable.ic_bolt);
        }

        private void actualizarUI(ItemPedido item){
            etCantidad.setText(String.valueOf(item.getCantidad()));
            tvSubtotal.setText("$ " + String.format("%.2f", item.getSubtotal()));
            if (listener != null) listener.onUpdateTotal();
        }
    }
}
