package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RatedActivity extends AppCompatActivity {

    Button inicio, enviar;
    EditText rated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rated);

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

        enviar = findViewById(R.id.buttonRated);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rated = findViewById(R.id.editTextTransRated);

                rated.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // No se necesita implementar este método
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // No se necesita implementar este método
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        String text = s.toString();
                        if (!text.isEmpty()) {
                            int value = Integer.parseInt(text);
                            if (value > 10) {
                                rated.setText("10"); // Establece el valor máximo permitido
                                rated.setSelection(rated.getText().length()); // Coloca el cursor al final del texto
                            }
                        }
                    }
                });

                if(rated.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Complete el registro", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), RatedActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Reporte enviado", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), Principal.class);
                    intent.putExtras(datos);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}