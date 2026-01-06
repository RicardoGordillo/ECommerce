package com.gordilloacostapoo.ecommerce.auth;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.gordilloacostapoo.ecommerce.R;
import com.gordilloacostapoo.ecommerce.data.DBHelper;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtUser, edtName, edtPass1, edtPass2;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new DBHelper(this);
        edtUser = findViewById(R.id.edtNewUser);
        edtName = findViewById(R.id.edtNewName);
        edtPass1 = findViewById(R.id.edtNewPass);
        edtPass2 = findViewById(R.id.edtNewPass2);
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> doRegister());
    }
    private void doRegister() {
        String user = edtUser.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        String p1 = edtPass1.getText().toString().trim();
        String p2 = edtPass2.getText().toString().trim();
        if (user.isEmpty() || p1.isEmpty() || p2.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!p1.equals(p2)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!esCorreoValido(user)){
            Toast.makeText(this, "El correo debe contener @ y terminar en .com", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean created = db.registerUser(user, name, p1);
        if (!created) {
            Toast.makeText(this, "No se pudo registrar (usuario ya existe)",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Usuario registrado. Inicie sesión.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean esCorreoValido(String email){
        return email != null && email.contains("@") && email.endsWith(".com");
    }
}