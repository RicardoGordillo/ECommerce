package com.gordilloacostapoo.ecommerce.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.material.navigation.NavigationView;
import com.gordilloacostapoo.ecommerce.model.Cliente;
import com.gordilloacostapoo.ecommerce.session.SessionManager;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ECommerce.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_USERS = "users";
    public static final String COL_ID = "id";
    public static final String COL_USERNAME = "username";
    public static final String COL_NAME = "name";
    public static final String COL_ADMIN = "admin";
    public static final String COL_PASSWORD = "password";
    private SessionManager sessionManager;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsers = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT UNIQUE NOT NULL, " +
                COL_NAME + "TEXT NOT NULL," +
                COL_PASSWORD + " TEXT NOT NULL" +
                ");";
        db.execSQL(createUsers);
        // Usuario de prueba (siempre existe después de instalar)
        insertUserIfNotExists(db, "admin", "name", "1234");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Para este ejercicio, estrategia simple
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    private void insertUserIfNotExists(SQLiteDatabase db, String username, String name, String password) {
        Cursor c = db.rawQuery(
                "SELECT 1 FROM " + TABLE_USERS + " WHERE " + COL_USERNAME + "=? LIMIT 1",
                new String[]{username}
        );
        boolean exists = c.moveToFirst();
        c.close();
        if (!exists) {
            ContentValues values = new ContentValues();
            values.put(COL_USERNAME, username);
            values.put(COL_NAME, name);
            values.put(COL_PASSWORD, password);
            db.insert(TABLE_USERS, null, values);
        }
    }

    public boolean userExists(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT 1 FROM " + TABLE_USERS + " WHERE " + COL_USERNAME + "=? LIMIT 1",
                new String[]{username}
        );
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }
    public boolean registerUser(String username, String name, String password) {
        if (username == null || username.trim().isEmpty()) return false;
        if (password == null || password.trim().isEmpty()) return false;
        if (name == null || name.trim().isEmpty()) return false;
        if (userExists(username.trim())) return false;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username.trim());
        values.put(COL_NAME, name.trim());
        values.put(COL_PASSWORD, password.trim());
        long id = db.insert(TABLE_USERS, null, values);
        return id != -1;
    }
    public boolean validateLogin(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT 1 FROM " + TABLE_USERS + " WHERE " + COL_USERNAME + "=? AND " +
                        COL_PASSWORD + "=? LIMIT 1",
                new String[]{username, password}
        );
        boolean ok = c.moveToFirst();
        c.close();
        return ok;
    }

    public Cliente getClienteByUsername() {
        String username = sessionManager.getUsername();
        SQLiteDatabase db = this.getReadableDatabase();
        // Buscamos al usuario por su correo/username
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COL_USERNAME + " = ?", new String[]{username});

        if (cursor.moveToFirst()) {
            // Suponiendo que tu clase Cliente tiene un constructor: Cliente(nombre, correo, password)
            // O ajusta según los índices de tus columnas (0: id, 1: username, 2: password)
            Cliente cliente = new Cliente();
            cliente.setCorreo(cursor.getString(1));
            cliente.setNombre(cursor.getString(2)); // Por ahora usamos el correo como nombre

            cursor.close();
            return cliente;
        }
        cursor.close();
        return null;
    }

    public boolean isUserAdmin(){

    }
}
