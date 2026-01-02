package com.gordilloacostapoo.ecommerce;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

public class InventoryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventario, container, false);
        // Referencias a los componentes
        RadioGroup rgTipo = view.findViewById(R.id.rgTipoProducto);
        TextView tvDetallesLabel = view.findViewById(R.id.tvDetallesLabel);
        TextInputLayout til1 = view.findViewById(R.id.tilExtra1);
        TextInputLayout til2 = view.findViewById(R.id.tilExtra2);
        TextInputLayout til3 = view.findViewById(R.id.tilExtra3);
        TextInputLayout til4 = view.findViewById(R.id.tilExtra4);
        Space space4 = view.findViewById(R.id.spacerExtra4);

        // Listener para el cambio de categoría
        rgTipo.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbComida) {
                til1.setHint("Fecha de caducidad");
                til2.setHint("Fecha de elaboración");
                til3.setHint("Calorías");

                // Ocultar el 4to campo (Comida solo usa 3 extras)
                til4.setVisibility(View.GONE);
                space4.setVisibility(View.GONE);

            } else if (checkedId == R.id.rbRopa) {
                til1.setHint("Talla");
                til2.setHint("Color");
                til3.setHint("Diseño");

                // Ocultar el 4to campo (Ropa solo usa 3 extras)
                til4.setVisibility(View.GONE);
                space4.setVisibility(View.GONE);

            } else if (checkedId == R.id.rbElectronico) {
                til1.setHint("Modelo");
                til2.setHint("Especificaciones");
                til3.setHint("Capacidad batería (mAh)");

                // Mostrar el 4to campo (Electrónico usa 4 extras)
                til4.setVisibility(View.VISIBLE);
                til4.setHint("Sistema operativo");
                space4.setVisibility(View.VISIBLE);
            }
        });

        // Forzar el estado inicial (Comida por defecto)
        rgTipo.check(R.id.rbComida);

        return view;
    }
}
