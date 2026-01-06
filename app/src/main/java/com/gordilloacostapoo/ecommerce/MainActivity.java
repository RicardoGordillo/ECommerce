package com.gordilloacostapoo.ecommerce;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.gordilloacostapoo.ecommerce.auth.LoginActivity;
import com.gordilloacostapoo.ecommerce.data.DBHelper;
import com.gordilloacostapoo.ecommerce.model.DataManager;
import com.gordilloacostapoo.ecommerce.model.Pedido;
import com.gordilloacostapoo.ecommerce.session.SessionManager;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SessionManager session = new SessionManager(this);
        DBHelper db = new DBHelper(this);
        if (!session.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        } else {
            String username = session.getUsername();
            DataManager.clienteLogueado = db.getClienteByUsername(username);

            if(DataManager.carrito == null) {
                DataManager.carrito = new Pedido(DataManager.clienteLogueado);
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        Menu menu = navigationView.getMenu();
        bottomNav = findViewById(R.id.bottom_nav);
        ImageButton btnMenu = findViewById(R.id.btn_menu);
        MenuItem itemRegistrarProducto = menu.findItem(R.id.drawer_inventario);
        MenuItem itemListarTodosLosPedidos = menu.findItem(R.id.drawer_pedidos);
        MenuItem itemMasVendidos = menu.findItem(R.id.drawer_masVendidos);

        // Capa inicial: por defecto HOME
        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
        }

        if(DataManager.clienteLogueado.getAdmin()){
            itemRegistrarProducto.setVisible(true);
            itemListarTodosLosPedidos.setVisible(true);
            itemMasVendidos.setVisible(true);
        } else {
            itemRegistrarProducto.setVisible(false);
            itemListarTodosLosPedidos.setVisible(false);
            itemMasVendidos.setVisible(false);
        }

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                replaceFragment(new HomeFragment());
                return true;
            } else if (id == R.id.nav_carrito) {
                replaceFragment(new CarritoFragment());
                return true;
            } else if (id == R.id.nav_perfil) {
                replaceFragment(new PerfilFragment());
                return true;
            }
            return false;
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.drawer_home) {
                replaceFragment(new HomeFragment());
                bottomNav.setSelectedItemId(R.id.nav_home);
            } else if (id == R.id.drawer_carrito) {
                replaceFragment(new CarritoFragment());
                bottomNav.setSelectedItemId(R.id.nav_carrito);
            } else if (id == R.id.drawer_perfil) {
                replaceFragment(new PerfilFragment());
                bottomNav.setSelectedItemId(R.id.nav_perfil);
            } else if (id == R.id.drawer_inventario && DataManager.clienteLogueado.getAdmin() == true) {
                replaceFragment(new InventoryFragment());
            } else if (id == R.id.drawer_pedidos && DataManager.clienteLogueado.getAdmin() == true) {
                replaceFragment(new PedidosFragment());
            } else if (id == R.id.drawer_masVendidos && DataManager.clienteLogueado.getAdmin() == true){
                replaceFragment(new MasVendidosFragment());
            } else if (id == R.id.drawer_persona_file) {
                Intent i = new Intent(this, PersonaFileActivity.class);
                startActivity(i);
            }
            else if (id == R.id.drawer_logout) {
                session.logout();

                DataManager.clienteLogueado = null;
                DataManager.carrito = null;

                Intent i = new Intent(this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
            drawerLayout.closeDrawers();
            return true;
        });

        btnMenu.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

    }
    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}