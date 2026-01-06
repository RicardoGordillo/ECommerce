package com.gordilloacostapoo.ecommerce.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gordilloacostapoo.ecommerce.R;
import com.gordilloacostapoo.ecommerce.model.Comida;
import com.gordilloacostapoo.ecommerce.model.ProductoVenta;
import com.gordilloacostapoo.ecommerce.model.Ropa;

import java.util.List;

public class ProductoTopAdapter extends RecyclerView.Adapter<ProductoTopAdapter.ProductoTopViewHolder> {

    private List<ProductoVenta> listaVentas;

    public ProductoTopAdapter (List<ProductoVenta> listaVentas){
        this.listaVentas = listaVentas;
    }

    @NonNull
    @Override
    public ProductoTopAdapter.ProductoTopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto_top, parent, false);
        return new ProductoTopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoTopAdapter.ProductoTopViewHolder holder, int position) {
        ProductoVenta pv = listaVentas.get(position);
        holder.bind(pv);
    }

    @Override
    public int getItemCount() {
        return listaVentas != null ? listaVentas.size() : 0;
    }

    public static class ProductoTopViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreTop, tvSkuTop, tvCantidadVendidaTop, tvTotalRecaudadoTop;
        ImageView imgCategoriaTop;

        public ProductoTopViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreTop = itemView.findViewById(R.id.tvNombreTop);
            tvCantidadVendidaTop = itemView.findViewById(R.id.tvCantidadVendidaTop);
            tvTotalRecaudadoTop = itemView.findViewById(R.id.tvTotalRecaudadoTop);
            tvSkuTop = itemView.findViewById(R.id.tvSkuTop);
            imgCategoriaTop = itemView.findViewById(R.id.imgCategoriaTop);
        }

        public void bind(ProductoVenta pv){
            tvNombreTop.setText(pv.getProducto().getnombre());
            tvSkuTop.setText("SKU: " + pv.getProducto().getcodigoSKU());
            tvCantidadVendidaTop.setText(pv.getCantidad() + " unidades");
            tvTotalRecaudadoTop.setText("$ " + String.format("%.2f", pv.getTotalRecaudado()));


            if (pv.getProducto() instanceof Comida) imgCategoriaTop.setImageResource(R.drawable.ic_food);
            else if (pv.getProducto() instanceof Ropa) imgCategoriaTop.setImageResource(R.drawable.ic_checkroom);
            else imgCategoriaTop.setImageResource(R.drawable.ic_bolt);
        }
    }
}
