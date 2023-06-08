package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button btnInicio, btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Para cambiar el nombre de la actividad
        this.setTitle(R.string.app_name);

        // Para el boton de Inicio de sesión
        btnInicio = findViewById(R.id.button);
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Atrapamos los datos para el inicio de sesión
                email = findViewById(R.id.editTextTextEmailAddress);
                password = findViewById(R.id.editTextTextPassword);

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
                } catch (IOException e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                boolean registrado = false;
                String[] usuario = new String[2];
                //salir:
                // Para buscar el usuario
                for (String[] x: listAcceso) {
                    // Se encontró al usuario
                    if(x[2].equals(email.getText().toString()) && x[3].equals(password.getText().toString())){
                        registrado = true;
                        usuario[0] = x[2];
                        usuario[1] = x[3];
                        break;
                    }
                }
                if (registrado == true){
                    // Se pasa el perfil del usuario
                    Bundle bundle = new Bundle();
                    bundle.putStringArray("keyDatos", usuario);

                    Intent intent = new Intent(MainActivity.this, Principal.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // Para el botón registro
        btnRegistro = findViewById(R.id.button2);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Registro.class);
                startActivity(intent);
            }
        });

    }


    /** Llamada al registro de un usuario*/
    /*public void irRegistro(View view){
        Intent intent = new Intent(getApplicationContext(), Registro.class);
        startActivity(intent);
    }*/

    /** Se inicia sesión*/
    /*public void irPrincipal(View view){
        Intent intent = new Intent(getApplicationContext(), Principal.class);
        startActivity(intent);
    }*/

}