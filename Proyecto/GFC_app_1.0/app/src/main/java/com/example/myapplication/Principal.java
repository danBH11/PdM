package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//import android.widget.Toolbar;

public class Principal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    TextView nombre, cuenta, saldo;

    Button btnTransfer, btnSalir, btnRecarTel, btnQr;

    private String email;
    private String pass;
    private String disponible;
    private String[] perfilUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //Para cambiar el nombre de la actividad
        this.setTitle(R.string.app_name_largo);

        //Se reciben el id y la contraseña
        Bundle datosRec = getIntent().getExtras();
        String[] datosRecArray = datosRec.getStringArray("keyDatos");
        String[] perfil = recuperaUsuario(datosRecArray[0], datosRecArray[1]);

        //Asignamos los valores globales
        email = datosRecArray[0];
        pass = datosRecArray[1];
        disponible = perfil[8];
        perfilUsuario = perfil;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(findViewById(R.id.toolbar));

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Mostrar los datos del usuario en la pantalla principal
        nombre = findViewById(R.id.textView9);
        nombre.setText(perfil[0] + " " + perfil[1]);

        cuenta = findViewById(R.id.textView7);
        cuenta.setText("Cuenta: " + perfil[5]);

        saldo = findViewById(R.id.textView8);
        saldo.setText("Saldo: $" + perfil[8]);

        String[] datos = {perfil[2], perfil[3], perfil[8]};
        Bundle credenciales = new Bundle();
        credenciales.putStringArray("keyDatos", datos);

        // Bundle especial para corregir el error de poder depositarte a ti mismo
        String[] datos1 = {perfil[2], perfil[3], perfil[5], perfil[8]};
        Bundle credenciales1 = new Bundle();
        credenciales1.putStringArray("keyDatos", datos1);


        // Transferencia
        btnTransfer = findViewById(R.id.button7);
        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Principal.this, TransferActivity.class);
                intent.putExtras(credenciales1);
                startActivity(intent);
                //finish();
            }
        });

        // Boton para salir
        btnSalir = findViewById(R.id.button9);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
                System.exit(0);
            }
        });

        // Recargar teléfono
        btnRecarTel = findViewById(R.id.button5);
        btnRecarTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Principal.this, RecargaTelefonoActivity.class);
                intent.putExtras(credenciales);
                startActivity(intent);
            }
        });

        // Pago con Qr
        btnQr = findViewById(R.id.button8);
        btnQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Principal.this, QrActivity.class);
                intent.putExtras(credenciales);
                startActivity(intent);
            }
        });


    // Para NavigationView
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String[] datos = {email, pass, disponible};
        Bundle credenciales = new Bundle();
        credenciales.putStringArray("keyDatos", datos);

        switch(item.getItemId()) {
            case R.id.perfil:
                Bundle perf = new Bundle();
                perf.putStringArray("keyDatos", perfilUsuario);
                Intent intent0 = new Intent(Principal.this, PerfilActivity.class);
                intent0.putExtras(perf);
                startActivity(intent0);
                return true;
                /*// Acción para el elemento "Perfil"
                Toast.makeText(this, "Seleccionaste Perfil", Toast.LENGTH_SHORT).show();
                return true;*/

            case R.id.transferencias:
                Intent intent = new Intent(Principal.this, TransferActivity.class);
                intent.putExtras(credenciales);
                startActivity(intent);
                return true;

            case R.id.recargaPhone:
                Intent intent1 = new Intent(Principal.this, RecargaTelefonoActivity.class);
                intent1.putExtras(credenciales);
                startActivity(intent1);
                return true;

            case R.id.operDigi:
                Intent intent2 = new Intent(Principal.this, QrActivity.class);
                intent2.putExtras(credenciales);
                startActivity(intent2);
                return true;

            case R.id.contacto:
                Intent intent3 = new Intent(Principal.this, ContactoActivity.class);
                intent3.putExtras(credenciales);
                startActivity(intent3);
                return true;

            case R.id.cerrar_sesion:
                finishAffinity();
                System.exit(0);
                return true;

            default:
                return false;

        }
    }


    /** Registro exitoso*/
    /*public void paginaInicio(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }*/

    /** Para que se pueda cerrar el menu con el botón de atrás*/
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Para ver las opciones del menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_secun_prin, menu);
        //mi.inflate(R.menu.menu_drawer_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String[] datos = {email, pass, disponible};
        Bundle credenciales = new Bundle();
        credenciales.putStringArray("keyDatos", datos);

        switch (item.getItemId()){
            case R.id.reportar:
                Intent intent3 = new Intent(Principal.this, ReportActivity.class);
                intent3.putExtras(credenciales);
                startActivity(intent3);
                //Toast.makeText(this,"Reportar", Toast.LENGTH_SHORT).show();
                //Log.i("Reportar", "Reportar");
                return true;
            case R.id.rateApp:
                Intent intent4 = new Intent(Principal.this, RatedActivity.class);
                intent4.putExtras(credenciales);
                startActivity(intent4);
                return true;
            case R.id.about:
                Intent intent5 = new Intent(Principal.this, AboutActivity.class);
                intent5.putExtras(credenciales);
                startActivity(intent5);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    // Para recuperar el usuario de la base de datos
    public String[] recuperaUsuario(String id, String pass){
        // Para leer los usuarios registrados:
        File file = new File(getExternalFilesDir(null), "usuarios.txt");
        List<String[]> listAcceso = new ArrayList<>();
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String datos;
            while ((datos = bufferedReader.readLine()) != null){
                String[] acceso = datos.split(",");
                listAcceso.add(acceso);
            }
        } catch (IOException e1){
            e1.printStackTrace();
            finish();
        }
        String[] usuario = new String[9];
        for (String[] x: listAcceso) {
            // Se encontró al usuario
            if(x[2].equals(id) && x[3].equals(pass)){
                usuario = x;
                break;
            }
        }
        return usuario;
    }
}