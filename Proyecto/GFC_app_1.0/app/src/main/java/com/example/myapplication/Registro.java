package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
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

public class Registro extends AppCompatActivity {

    EditText nombre, apellidos, email, password, againPass, genero;
    Button btnSiguiente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnSiguiente = findViewById(R.id.button3);
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Atrapamos los datos que se escriben en el registro
                nombre = findViewById(R.id.editTextTextPersonName);
                apellidos = findViewById(R.id.editTextTextPersonName2);
                email = findViewById(R.id.editTextTextEmailAddress3);
                password = findViewById(R.id.editTextTextPassword2);
                againPass = findViewById(R.id.editTextTextPassword3);
                //genero = findViewById(R.id.opciones_sexo.);


                if(nombre.getText().toString().equals("") || apellidos.getText().toString().equals("") ||
                        email.getText().toString().equals("") || password.getText().toString().equals("") ||
                        againPass.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Complete el registro", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Registro.this, Registro.class);
                    startActivity(intent);
                }
                else if(password.getText().toString().equals(againPass.getText().toString())){
                    // Comprobar que no haya otra persona con el mismo email
                    boolean emailRegistrado = false;
                    File file = new File(getExternalFilesDir(null), "usuarios.txt");
                    List<String[]> listUsuarios = new ArrayList<>();
                    try{
                        FileReader fileReader = new FileReader(file);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        String datos;
                        while ((datos = bufferedReader.readLine()) != null){
                            String[] acceso = datos.split(",");
                            listUsuarios.add(acceso);
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    for (String[] x: listUsuarios) {
                        if(x[2].equals(email.getText().toString())){
                        emailRegistrado = true;
                        break;
                        }
                    }
                    if(emailRegistrado){
                        Toast.makeText(getApplicationContext(), "Email ya registrado", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Registro.this, Registro.class);
                        startActivity(intent);
                        finish();
                    } else{
                        String datosUsuario = nombre.getText().toString() + "," +
                                apellidos.getText().toString() + "," +
                                email.getText().toString() + "," +
                                password.getText().toString();


                        String [] datosUsuarioArray = new String[]{nombre.getText().toString(),
                                apellidos.getText().toString(), email.getText().toString(),
                                password.getText().toString()};

                        Bundle datos = new Bundle();
                        datos.putString("keyDatos", datosUsuario);
                        //datos.putString("keyDatos", nombre.getText().toString());

                        Intent intent = new Intent(Registro.this, Registro2.class);
                        intent.putExtras(datos);
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Registro.this, Registro.class);
                    startActivity(intent);
                }
            }
        });
    }
}