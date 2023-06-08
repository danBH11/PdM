package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ReportActivity extends AppCompatActivity {

    Button inicio, enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Se reciben los datos
        Bundle datosRec = getIntent().getExtras();
        String[] datosUsuarioRec = datosRec.getStringArray("keyDatos");
        String[] datosRegreso = {datosUsuarioRec[0], datosUsuarioRec[1]};
        Bundle datos = new Bundle();
        datos.putStringArray("keyDatos", datosRegreso);

        inicio = findViewById(R.id.buttonHomeReport);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Principal.class);
                intent.putExtras(datos);
                startActivity(intent);
                finish();
            }
        });

        enviar = findViewById(R.id.buttonReport);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Reporte enviado", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), Principal.class);
                intent.putExtras(datos);
                startActivity(intent);
                finish();
            }
        });

    }
}