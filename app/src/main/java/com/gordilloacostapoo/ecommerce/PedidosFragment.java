package com.gordilloacostapoo.ecommerce;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gordilloacostapoo.ecommerce.adapter.PedidoAdapter;
import com.gordilloacostapoo.ecommerce.model.DataManager;
import com.gordilloacostapoo.ecommerce.model.Pedido;
import com.gordilloacostapoo.ecommerce.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class PedidosFragment extends Fragment {

    TextView tvTotalGeneralReporte, tvCantidadPedidosReporte;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listar_pedidos, container, false);



        RecyclerView rv = view.findViewById(R.id.rvPedidos);
        View layoutVacio = view.findViewById(R.id.layoutVacio);

        tvTotalGeneralReporte = view.findViewById(R.id.tvTotalGeneralReporte);
        tvCantidadPedidosReporte = view.findViewById(R.id.tvCantidadPedidosReporte);

        // Obtener la lista de pedidos de tu TiendaVirtual
        List<Pedido> pedidos = DataManager.tienda.getPedidosConfirmados();
        List<Pedido> pedidosAMostrar = new ArrayList<>();

        String correoFiltro;

        if(getArguments() != null){
            Bundle args = getArguments();
            correoFiltro = args.getString("correoCliente");
        } else {
            correoFiltro = null;
        }

        if(correoFiltro != null && !correoFiltro.isEmpty()) {

            Log.d("DEBUG_PEDIDOS", "Filtrando por: " + correoFiltro);

            for (Pedido p : pedidos) {
                if (p.getCliente() != null && p.getCliente().getCorreo().equalsIgnoreCase(correoFiltro.trim())) {
                    Log.d("DEBUG_PEDIDOS", "Comparando: " + correoFiltro + " con " + correoFiltro);
                    pedidosAMostrar.add(p);
                }
            }
        } else {
            Log.d("DEBUG_PEDIDOS", "No hay filtro de correo, cargando lista completa.");
            pedidosAMostrar.addAll(pedidos);
        }
        if (pedidosAMostrar.isEmpty()) {
            // Si no hay pedidos, mostramos el mensaje de error visual
            rv.setVisibility(View.GONE);
            layoutVacio.setVisibility(View.VISIBLE);
            tvTotalGeneralReporte.setText("$ 0.00");
            tvCantidadPedidosReporte.setText("0");

        } else {
            // Si hay pedidos, configuramos el RecyclerView
            rv.setVisibility(View.VISIBLE);
            layoutVacio.setVisibility(View.GONE);

            double totalAcumulado = 0;
            int numeroPedidos = 0;
            for (Pedido p : pedidosAMostrar) {
                totalAcumulado += p.calcularTotal();
                numeroPedidos += 1;
            }
            tvTotalGeneralReporte.setText("$ " + Math.round(totalAcumulado));
            tvCantidadPedidosReporte.setText(String.valueOf(numeroPedidos));

            rv.setLayoutManager(new LinearLayoutManager(getContext()));
            rv.setAdapter(new PedidoAdapter(pedidosAMostrar));
        }

        return view;
    }
}
