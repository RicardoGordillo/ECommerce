package com.gordilloacostapoo.ecommerce;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gordilloacostapoo.ecommerce.adapter.ProductoAdapter;
import com.gordilloacostapoo.ecommerce.model.DataManager;

public class HomeFragment extends Fragment {

    private RecyclerView rvProductos;
    private ProductoAdapter adapter;
    private EditText etBuscar;
    private ImageButton btnLupa;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Referencias
        etBuscar = view.findViewById(R.id.etBuscar);
        btnLupa = view.findViewById(R.id.btnLupa);
        rvProductos = view.findViewById(R.id.rvProductos);
        Button btnPersonaFile = view.findViewById(R.id.btnPersonaFile);

        // Configurar RecyclerView
        rvProductos.setLayoutManager(new LinearLayoutManager(getContext()));

        DataManager.cargarDatosIniciales();
        // Obtenemos los productos
        adapter = new ProductoAdapter(DataManager.tienda.getCatalogo());
        rvProductos.setAdapter(adapter);

        // Lógica de búsqueda al dar click en la lupa
        btnLupa.setOnClickListener(v -> {
            String query = etBuscar.getText().toString().trim();
            adapter.filtrar(query);
            // Cerrar el teclado después de buscar
            ocultarTeclado();
        });

        btnPersonaFile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PersonaFileActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void ocultarTeclado() {
        View view = this.getView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}