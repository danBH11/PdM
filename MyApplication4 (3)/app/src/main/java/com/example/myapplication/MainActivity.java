package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Llamada al registro de un usuario*/
    public void irRegistro(View view){
        Intent intent = new Intent(getApplicationContext(), Registro.class);
        startActivity(intent);
    }

    /** Se inicia sesión*/
    public void irPrincipal(View view){
        Intent intent = new Intent(getApplicationContext(), Principal.class);
        startActivity(intent);
    }

}