package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {

    TextView nombre, email,cuenta, saldo;
    Button inicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Se recibe todo el perfil del usuario
        Bundle datosRec = getIntent().getExtras();
        String[] datosUsuarioRec = datosRec.getStringArray("keyDatos");
        // Obtenermos el email y la contraseña
        String[] datosRegreso = {datosUsuarioRec[2], datosUsuarioRec[3]};
        Bundle datos = new Bundle();
        datos.putStringArray("keyDatos", datosRegreso);

        // Mostrar los datos del usuario en la pantalla principal
        nombre = findViewById(R.id.textViewNombre);
        nombre.setText("Nombre: " + datosUsuarioRec[0] + " " + datosUsuarioRec[1]);

        email = findViewById(R.id.textViewCorreo);
        email.setText("Correo electrónico: "+ datosUsuarioRec[2]);

        cuenta = findViewById(R.id.textViewCuenta);
        cuenta.setText("Cuenta: " + datosUsuarioRec[5]);

        saldo = findViewById(R.id.textViewSaldo);
        saldo.setText("Saldo disponible: " + datosUsuarioRec[8]);

        // Boton de inicio
        inicio = findViewById(R.id.buttonHomePerfil);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Principal.class);
                intent.putExtras(datos);
                startActivity(intent);

            }
        });
    }
}