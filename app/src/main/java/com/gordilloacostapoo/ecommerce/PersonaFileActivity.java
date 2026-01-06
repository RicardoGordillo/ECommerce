package com.gordilloacostapoo.ecommerce;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.gordilloacostapoo.ecommerce.model.Persona;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
public class PersonaFileActivity extends AppCompatActivity {
    private static final String FILE_NAME = "file.txt";
    private EditText edtNombre, edtApellido, edtCorreo, edtTelefono, edtEdad;
    private TextView txtResultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persona_file);
        edtNombre = findViewById(R.id.edtNombre);
        edtApellido = findViewById(R.id.edtApellido);
        edtCorreo = findViewById(R.id.edtCorreo);
        edtTelefono = findViewById(R.id.edtTelefono);
        edtEdad = findViewById(R.id.edtEdad);
        txtResultado = findViewById(R.id.txtResultado);
        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnLeer = findViewById(R.id.btnLeer);
        btnGuardar.setOnClickListener(v -> guardarPersona());
        btnLeer.setOnClickListener(v -> leerArchivo());
    }
    private void guardarPersona() {
        try {
            String nombre = edtNombre.getText().toString();
            String apellido = edtApellido.getText().toString();
            String correo = edtCorreo.getText().toString();
            String telefono = edtTelefono.getText().toString();
            int edad = Integer.parseInt(edtEdad.getText().toString());
            Persona persona = new Persona(nombre, apellido, correo, telefono, edad);
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(persona.toText().getBytes());
            fos.close();
            Toast.makeText(this, "Archivo creado y guardado en el teléfono",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al guardar datos", Toast.LENGTH_SHORT).show();
        }
    }
    private void leerArchivo() {
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            br.close();
            txtResultado.setText(sb.toString().trim());
        } catch (Exception e) {
            Toast.makeText(this, "El archivo no existe", Toast.LENGTH_SHORT).show();
        }
    }
}