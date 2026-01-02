package com.gordilloacostapoo.ecommerce;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.gordilloacostapoo.ecommerce.model.DataManager;
import com.gordilloacostapoo.ecommerce.session.SessionManager;

public class PerfilFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SessionManager session = new SessionManager(this);
        String username = session.getUsername();
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        // Referencias
        TextView tvNombre = view.findViewById(R.id.tvNombrePerfil);
        TextView tvCorreo = view.findViewById(R.id.tvCorreoPerfil);
        Button btnMisCompras = view.findViewById(R.id.btnVerHistorial);

        // Mostrar datos del cliente logueado
        if (DataManager.clienteLogueado != null) {
            tvNombre.setText(DataManager.clienteLogueado.getNombre());
            tvCorreo.setText(DataManager.clienteLogueado.getCorreo());
        }

        // Configurar clic para ir al Historial
        btnMisCompras.setOnClickListener(v -> {
            PedidosFragment fragment = new PedidosFragment();

            // Enviamos un "Bundle" con la señal para filtrar
            Bundle args = new Bundle();
            args.putBoolean("esHistorialPersonal", true);
            fragment.setArguments(args);

            // Transición
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null) // Para poder regresar con el botón atrás
                    .commit();
        });

        return view;
    }
}