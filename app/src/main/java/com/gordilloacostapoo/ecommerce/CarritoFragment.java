package com.gordilloacostapoo.ecommerce;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gordilloacostapoo.ecommerce.adapter.CarritoAdapter;
import com.gordilloacostapoo.ecommerce.model.Cliente;
import com.gordilloacostapoo.ecommerce.model.DataManager;
import com.gordilloacostapoo.ecommerce.model.Pedido;
import com.gordilloacostapoo.ecommerce.model.TiendaException;

public class CarritoFragment extends Fragment implements CarritoAdapter.OnCarritoChangeListener{
    private RecyclerView rvCarrito;
    private TextView tvTotal;
    private Button btnConfirmar;
    private CarritoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);

        rvCarrito = view.findViewById(R.id.rvCarrito);
        tvTotal = view.findViewById(R.id.tvTotalCarrito);
        btnConfirmar = view.findViewById(R.id.btnConfirmarPedido);

        if(DataManager.carrito != null){
            rvCarrito.setLayoutManager(new LinearLayoutManager(requireContext()));

            adapter = new CarritoAdapter(DataManager.carrito.getItems(), this);
            rvCarrito.setAdapter(adapter);

            onUpdateTotal();
        }

        btnConfirmar.setOnClickListener(v -> finalizarCompra());
        return view;
    }

    @Override
    public void onUpdateTotal() {
        if(DataManager.carrito != null) {
            double total = DataManager.carrito.calcularTotal();
            tvTotal.setText("$ " + String.format("%.2f", total));
        }
    }

    private void finalizarCompra() {
        try {
            DataManager.tienda.confirmarPedido(DataManager.carrito);

            //Vaciar carrito (crear un carrito nuevo vacio)
            Cliente clienteActual = DataManager.carrito.getCliente();
            DataManager.carrito = new Pedido(clienteActual);

            Toast.makeText(requireContext(), "Compra confirmada. ¡Gracias!", Toast.LENGTH_LONG).show();

            //Refrescar UI
            adapter = new CarritoAdapter(DataManager.carrito.getItems(), this);
            rvCarrito.setAdapter(adapter);
            onUpdateTotal();

        } catch (TiendaException e) {
            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}