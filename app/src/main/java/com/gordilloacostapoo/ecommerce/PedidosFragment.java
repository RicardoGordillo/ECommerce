package com.gordilloacostapoo.ecommerce;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gordilloacostapoo.ecommerce.adapter.PedidoAdapter;
import com.gordilloacostapoo.ecommerce.model.DataManager;
import com.gordilloacostapoo.ecommerce.model.Pedido;

import java.util.List;

public class PedidosFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listar_pedidos, container, false);

        RecyclerView rv = view.findViewById(R.id.rvPedidos);
        View layoutVacio = view.findViewById(R.id.layoutVacio);

        // Obtener la lista de pedidos de tu TiendaVirtual
        List<Pedido> pedidos = DataManager.tienda.getPedidosConfirmados();

        if (pedidos.isEmpty()) {
            // Si no hay pedidos, mostramos el mensaje de error visual
            rv.setVisibility(View.GONE);
            layoutVacio.setVisibility(View.VISIBLE);
        } else {
            // Si hay pedidos, configuramos el RecyclerView
            rv.setVisibility(View.VISIBLE);
            layoutVacio.setVisibility(View.GONE);

            rv.setLayoutManager(new LinearLayoutManager(getContext()));
            rv.setAdapter(new PedidoAdapter(pedidos));
        }

        return view;
    }
}
