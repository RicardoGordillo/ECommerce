package com.gordilloacostapoo.ecommerce;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gordilloacostapoo.ecommerce.model.Comida;
import com.gordilloacostapoo.ecommerce.model.DataManager;
import com.gordilloacostapoo.ecommerce.model.Electronico;
import com.gordilloacostapoo.ecommerce.model.Producto;
import com.gordilloacostapoo.ecommerce.model.Ropa;

import java.time.LocalDate;
import java.util.Calendar;

public class InventoryFragment extends Fragment {

    private RadioGroup rgTipo;
    private TextInputEditText etNombre, etMarca, etSku, etPrecio, etStock;
    private TextInputEditText etExtra1, etExtra2, etExtra3, etExtra4;
    private TextInputLayout til1, til2, til3, til4;
    private Space space4;
    private Button btnGuardar;
    private static final String[] TALLAS_DISPONIBLES = {"XS", "S", "M", "L", "XL"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventario, container, false);

        etNombre = view.findViewById(R.id.etNombreProducto);
        etMarca = view.findViewById(R.id.etMarca);
        etSku = view.findViewById(R.id.etSku);
        etPrecio = view.findViewById(R.id.etPrecio);
        etStock = view.findViewById(R.id.etStock);

        rgTipo = view.findViewById(R.id.rgTipoProducto);

        til1 = view.findViewById(R.id.tilExtra1);
        til2 = view.findViewById(R.id.tilExtra2);
        til3 = view.findViewById(R.id.tilExtra3);
        til4 = view.findViewById(R.id.tilExtra4);

        etExtra1 = (TextInputEditText) til1.getEditText();
        etExtra2 = (TextInputEditText) til2.getEditText();
        etExtra3 = (TextInputEditText) til3.getEditText();
        etExtra4 = (TextInputEditText) til4.getEditText();

        space4 = view.findViewById(R.id.spacerExtra4);

        btnGuardar = view.findViewById(R.id.btnGuardarProducto);
        btnGuardar.setOnClickListener(v -> guardarProducto());

        rgTipo.setOnCheckedChangeListener((group, checkedId) -> {
            actualizarInterfaz(checkedId);
        });

        rgTipo.check(R.id.rbComida); // Estado inicial
        return view;
    }

    private void actualizarInterfaz(int checkedId) {
        resetearCampos(til1.getEditText());
        resetearCampos(til2.getEditText());
        resetearCampos(til3.getEditText());
        resetearCampos(til4.getEditText());

        if (checkedId == R.id.rbComida) {
            til1.setHint("Fecha caducidad");
            til2.setHint("Fecha elaboración");
            til3.setHint("Calorías");
            etExtra3.setInputType(InputType.TYPE_CLASS_NUMBER);
            configurarDatePicker((TextInputEditText) til1.getEditText());
            configurarDatePicker((TextInputEditText) til2.getEditText());
            til4.setVisibility(View.GONE);
            space4.setVisibility(View.GONE);

        } else if (checkedId == R.id.rbRopa) {
            til1.setHint("Talla");
            til2.setHint("Color");
            til3.setHint("Diseño");
            configurarSelectorTallas(etExtra1);
            til4.setVisibility(View.GONE);
            space4.setVisibility(View.GONE);

        } else if (checkedId == R.id.rbElectronico) {
            til1.setHint("Modelo");
            til2.setHint("Especificaciones");
            til3.setHint("Batería (mAh)");
            etExtra3.setInputType(InputType.TYPE_CLASS_NUMBER);
            til4.setVisibility(View.VISIBLE);
            til4.setHint("Sistema operativo");
            space4.setVisibility(View.VISIBLE);
        }
    }

    private void guardarProducto(){
        if(!validarCampos()) return;

        String sku = etSku.getText().toString().trim();
        String nombre = etNombre.getText().toString().trim();
        String marca = etMarca.getText().toString().trim();
        double precio = Double.parseDouble(etPrecio.getText().toString().trim());
        int stock = Integer.parseInt(etStock.getText().toString().trim());

        Producto nuevoProducto = null;

        // 3. Crear objeto específico según la categoría seleccionada
        int selectedId = rgTipo.getCheckedRadioButtonId();

        if (selectedId == R.id.rbComida) {
            nuevoProducto = new Comida(
                    sku, nombre, marca, precio, stock,
                    LocalDate.parse(etExtra1.getText().toString()), // Fecha caducidad
                    LocalDate.parse(etExtra2.getText().toString()), // Fecha elaboración
                    Integer.parseInt(etExtra3.getText().toString())  // Calorías
            );
        } else if (selectedId == R.id.rbRopa) {
            nuevoProducto = new Ropa(
                    sku, nombre, marca, precio, stock,
                    etExtra1.getText().toString(), // Talla
                    etExtra2.getText().toString(), // Color
                    etExtra3.getText().toString()  // Diseño
            );
        } else if (selectedId == R.id.rbElectronico) {
            nuevoProducto = new Electronico(
                    sku, nombre, marca, precio, stock,
                    etExtra1.getText().toString(), // Modelo
                    etExtra2.getText().toString(), // Specs
                    Integer.parseInt(etExtra3.getText().toString()), // Batería
                    etExtra4.getText().toString()  // SO
            );
        }

        if (nuevoProducto != null) {
            DataManager.tienda.registrarProducto(nuevoProducto);
            Toast.makeText(requireContext(), "Producto guardado correctamente", Toast.LENGTH_SHORT).show();
            limpiarFormulario();
        }
    }

    private void configurarSelectorTallas(TextInputEditText campo) {
        campo.setInputType(android.text.InputType.TYPE_NULL);
        campo.setFocusable(false);
        campo.setClickable(true);

        campo.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Seleccione una talla");
            builder.setItems(TALLAS_DISPONIBLES, (dialog, which) -> {
                // "which" es el índice de la opción tocada
                String tallaSeleccionada = TALLAS_DISPONIBLES[which];
                campo.setText(tallaSeleccionada);
            });
            builder.show();
        });
    }

    private boolean validarCampos() {
        if (etNombre.getText().toString().isEmpty()) {
            etNombre.setError("Requerido");
            return false;
        }
        if(!DataManager.tienda.validarNombreProd(etNombre.getText().toString())){
            etNombre.setError("Nombre ya existente");
            return false;
        }
        if (etPrecio.getText().toString().isEmpty()) {
            etPrecio.setError("Requerido");
            return false;
        }
        try {
            double p = Double.parseDouble(etPrecio.getText().toString());
            if(p <= 0){
                etPrecio.setError("Debe ser mayor a 0");
                return false;
            }
        } catch(NumberFormatException e){
            etPrecio.setError("Entrada invalida");
            return false;
        }
        if (etMarca.getText().toString().isEmpty()){
            etMarca.setError("Requerido");
            return false;
        }
        if (etSku.getText().toString().isEmpty()){
            etSku.setError("Requerido");
            return false;
        }
        if(!DataManager.tienda.validarCodigoSKU(etSku.getText().toString())){
            etSku.setError("CodigoSKU ya existente o largo incorrecto (8 caractares)");
            return false;
        }
        if (etStock.getText().toString().isEmpty()) {
            etStock.setError("Requerido");
            return false;
        }
        try {
            int s = Integer.parseInt(etStock.getText().toString());
            if(s < 0){
                etStock.setError("El stock no puede ser menor que 0");
                return false;
            }
        } catch (NumberFormatException e){
            etStock.setError("Entrada invalida");
            return false;
        }

        int selectedId = rgTipo.getCheckedRadioButtonId();
        if (selectedId == R.id.rbComida) {
            if (etExtra1.getText().toString().isEmpty()) {
                til1.setError("Seleccione fecha de caducidad");
                return false;
            }
            if (etExtra2.getText().toString().isEmpty()) {
                til2.setError("Seleccione fecha de elaboración");
                return false;
            }
            if (etExtra3.getText().toString().trim().isEmpty()) {
                til3.setError("Ingrese las calorías");
                return false;
            }

        } else if (selectedId == R.id.rbRopa) {
            if (etExtra1.getText().toString().trim().isEmpty()) {
                til1.setError("Ingrese la talla");
                return false;
            }
            if (etExtra2.getText().toString().trim().isEmpty()) {
                til2.setError("Ingrese el color");
                return false;
            }
            if (etExtra3.getText().toString().trim().isEmpty()) {
                til3.setError("Ingrese el diseño");
                return false;
            }

        } else if (selectedId == R.id.rbElectronico) {
            if (etExtra1.getText().toString().trim().isEmpty()) {
                til1.setError("Ingrese el modelo");
                return false;
            }
            if(etExtra2.getText().toString().trim().isEmpty()){
                til2.setError("Ingrese las especificaciones");
                return false;
            }
            if(etExtra3.getText().toString().trim().isEmpty()){
                til3.setError("Ingrese la capacidad de bateria");
                return false;
            }
            if (etExtra4.getText().toString().trim().isEmpty()) {
                til4.setError("Ingrese el sistema operativo");
                return false;
            }
        }
        return true;
    }

    private void configurarDatePicker(TextInputEditText campo) {
        campo.setInputType(android.text.InputType.TYPE_NULL);
        campo.setFocusable(false);
        campo.setClickable(true);
        campo.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            new DatePickerDialog(requireContext(), (view1, year, month, dayOfMonth) -> {
                LocalDate fecha = LocalDate.of(year, month + 1, dayOfMonth);
                campo.setText(fecha.toString());
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void resetearCampos(View view) {
        if (view instanceof TextInputEditText) {
            TextInputEditText et = (TextInputEditText) view;
            et.setOnClickListener(null);
            et.setFocusable(true);
            et.setFocusableInTouchMode(true);
            et.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
            et.setText("");
        }
    }

    private void limpiarFormulario() {
        etNombre.setText("");
        etMarca.setText("");
        etSku.setText("");
        etPrecio.setText("");
        etStock.setText("");
        etExtra1.setText("");
        etExtra2.setText("");
        etExtra3.setText("");
        etExtra4.setText("");
    }
}
