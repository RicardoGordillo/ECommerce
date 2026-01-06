package com.gordilloacostapoo.ecommerce.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.gordilloacostapoo.ecommerce.R;
import com.gordilloacostapoo.ecommerce.model.ItemPedido;
import com.gordilloacostapoo.ecommerce.model.Pedido;
import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.ViewHolder> {
    private List<Pedido> listaPedidos;

    public PedidoAdapter(List<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pedido pedido = listaPedidos.get(position);

        // 1. Cliente y Fecha
        if (pedido.getCliente() == null) {
            holder.tvCliente.setText("Cliente desconocido");
        } else {
            holder.tvCliente.setText("Cliente: " + pedido.getCliente().getNombre());
        }
        holder.tvFecha.setText(pedido.getFecha().toString());

        // 2. Construir la lista de productos (Similar a tu toString)
        StringBuilder sb = new StringBuilder();
        for (ItemPedido item : pedido.getItems()) {
            sb.append("• ")
                    .append(item.getProducto().getnombre())
                    .append(" (x")
                    .append(item.getCantidad())
                    .append(") - $")
                    .append(item.getSubtotal())
                    .append("\n");
        }
        holder.tvItems.setText(sb.toString().trim());

        // 3. Total calculado
        holder.tvTotal.setText("Total: $ " + pedido.calcularTotal());
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCliente, tvFecha, tvItems, tvTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCliente = itemView.findViewById(R.id.tvClientePedido);
            tvFecha = itemView.findViewById(R.id.tvFechaPedido);
            tvItems = itemView.findViewById(R.id.tvItemsPedido);
            tvTotal = itemView.findViewById(R.id.tvTotalPedido);
        }
    }
}