package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }
    /** Llamada al registro2 de un usuario*/
    public void irRegistro2(View view){
        Intent intent = new Intent(getApplicationContext(), Registro2.class);
        startActivity(intent);
    }
}