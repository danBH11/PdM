package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Registro2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2);
    }
    /** Registro exitoso*/
    public void irRegistroExitoso(View view){
        Intent in = new Intent(getApplicationContext(), RegistroExitoso.class);
        startActivity(in);
    }
}