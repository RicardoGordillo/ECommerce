package com.gordilloacostapoo.ecommerce;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gordilloacostapoo.ecommerce.data.DBHelper;
import com.gordilloacostapoo.ecommerce.model.Cliente;
import com.gordilloacostapoo.ecommerce.model.DataManager;
import com.gordilloacostapoo.ecommerce.session.SessionManager;

public class PerfilFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SessionManager session = new SessionManager(getContext());
        DBHelper db = new DBHelper(getContext());

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        TextView tvNombre = view.findViewById(R.id.tvNombrePerfil);
        TextView tvCorreo = view.findViewById(R.id.tvCorreoPerfil);
        Button btnMisCompras = view.findViewById(R.id.btnVerHistorial);
        TextView tvRolPerfil = view.findViewById(R.id.tvRolPerfil);

        Cliente clienteLogueado = db.getClienteByUsername(session.getUsername());
        if (clienteLogueado != null) {
            tvNombre.setText(clienteLogueado.getNombre());
            tvCorreo.setText(clienteLogueado.getCorreo());
        }

        if(clienteLogueado.getAdmin()){
            tvRolPerfil.setText("Administrador");
        } else {
            tvRolPerfil.setText("Cliente estándar");
        }

        btnMisCompras.setOnClickListener(v -> {
            if(clienteLogueado == null) return;
            PedidosFragment fragment = new PedidosFragment();

            Bundle args = new Bundle();
            args.putBoolean("esHistorialPersonal", true);
            args.putString("correoCliente", clienteLogueado.getCorreo());
            fragment.setArguments(args);

            Log.d("DEBUG_PERFIL", "Abriendo compras para el usuario: " + session.getUsername());

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

}