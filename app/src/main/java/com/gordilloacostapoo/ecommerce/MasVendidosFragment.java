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

import com.gordilloacostapoo.ecommerce.adapter.ProductoTopAdapter;
import com.gordilloacostapoo.ecommerce.model.DataManager;
import com.gordilloacostapoo.ecommerce.model.ItemPedido;
import com.gordilloacostapoo.ecommerce.model.Pedido;
import com.gordilloacostapoo.ecommerce.model.ProductoVenta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MasVendidosFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                         @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_mas_vendidos, container, false);

        RecyclerView rv = view.findViewById(R.id.rvMasVendidos);
        View layoutVacio = view.findViewById(R.id.layoutVacioRanking);

        List<ProductoVenta> ranking = obtenerRanking();

        if(ranking.isEmpty()){
            rv.setVisibility(View.GONE);
            layoutVacio.setVisibility(View.VISIBLE);
        } else {
            rv.setVisibility(View.VISIBLE);
            layoutVacio.setVisibility(View.GONE);

            rv.setLayoutManager(new LinearLayoutManager(getContext()));
            rv.setAdapter(new ProductoTopAdapter(ranking));
        }

        return view;

    }

    private List<ProductoVenta> obtenerRanking() {
        HashMap<String, ProductoVenta> mapaVentas = new HashMap<>();
        for (Pedido pedido : DataManager.tienda.getPedidosConfirmados()) {
            for(ItemPedido item : pedido.getItems()){
                String sku = item.getProducto().getcodigoSKU();
                int cant = item.getCantidad();
                if(mapaVentas.containsKey(sku)) {
                    mapaVentas.get(sku).sumarCantidad(cant);
                } else {
                    mapaVentas.put(sku, new ProductoVenta(item.getProducto(), cant));
                }
            }
        }

        List<ProductoVenta> listaOrdenada = new ArrayList<>(mapaVentas.values());
        listaOrdenada.sort((p1, p2) -> Integer.compare(p2.getCantidad(), p1.getCantidad()));

        return listaOrdenada;
    }
}
