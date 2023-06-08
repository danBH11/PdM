package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OperacionExitosaActivity extends AppCompatActivity {

    Button inicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operacion_exitosa);
        // Se reciben los datos
        Bundle datosRec = getIntent().getExtras();
        String[] datosUsuarioRec = datosRec.getStringArray("keyDatos");
        String[] datosRegreso = {datosUsuarioRec[0], datosUsuarioRec[1]};
        Bundle datos = new Bundle();
        datos.putStringArray("keyDatos", datosRegreso);

        inicio = findViewById(R.id.button6Inicio);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Principal.class);
                intent.putExtras(datos);
                startActivity(intent);
                finish();
            }
        });

    }
    /** Registro exitoso*/
/*    public void paginaPrincipal(View view){
        Intent intent = new Intent(getApplicationContext(), Principal.class);
        startActivity(intent);
        finish();
    }*/
}