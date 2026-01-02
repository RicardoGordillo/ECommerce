package com.gordilloacostapoo.ecommerce.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.gordilloacostapoo.ecommerce.MainActivity;
import com.gordilloacostapoo.ecommerce.R;
import com.gordilloacostapoo.ecommerce.data.DBHelper;
import com.gordilloacostapoo.ecommerce.session.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUser, edtPass;
    private DBHelper db;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(this);
        if (session.isLoggedIn()) {
            goMain();
            return;
        }
        setContentView(R.layout.activity_login);
        db = new DBHelper(this);
        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnGoRegister = findViewById(R.id.btnGoRegister);
        btnLogin.setOnClickListener(v -> doLogin());
        btnGoRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
    private void doLogin() {
        String user = edtUser.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Complete usuario y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean ok = db.validateLogin(user, pass);
        if (!ok) {
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            return;
        }
        session.createSession(user);
        goMain();
    }
    private void goMain() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }
}