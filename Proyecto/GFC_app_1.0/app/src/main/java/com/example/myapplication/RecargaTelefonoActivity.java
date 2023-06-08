package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RecargaTelefonoActivity extends AppCompatActivity {

    EditText telefono, recarga;
    Button btnRecargar, inicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga_telefono);

        // Se reciben el id, la password y el saldo
        Bundle datosRec = getIntent().getExtras();
        String[] datosUsuarioRec = datosRec.getStringArray("keyDatos");
        // Le quitamos el último elemento para Principal
        String[] datosRegreso = {datosUsuarioRec[0], datosUsuarioRec[1]};
        Bundle datos = new Bundle();
        datos.putStringArray("keyDatos", datosRegreso);

        btnRecargar = findViewById(R.id.buttonRecargar);
        btnRecargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                telefono = findViewById(R.id.editTextNumTel);
                recarga = findViewById(R.id.editTextMonto);


                if(telefono.getText().toString().equals("") || recarga.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Complete el registro", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RecargaTelefonoActivity.this, RecargaTelefonoActivity.class);
                    intent.putExtras(datosRec);
                    startActivity(intent);
                    finish();
                }
                else{
                    double monto = Double.parseDouble(recarga.getText().toString());
                    double saldo = Double.parseDouble((datosUsuarioRec[2]));

                    if(monto > saldo){
                        Toast.makeText(getApplicationContext(), "Saldo disponible no suficiente", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), RecargaTelefonoActivity.class);
                        intent.putExtras(datosRec);
                        startActivity(intent);
                        finish();
                    } else if (monto <= 0) {
                        Toast.makeText(getApplicationContext(), "Cantidad inválida", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), RecargaTelefonoActivity.class);
                        intent.putExtras(datosRec);
                        startActivity(intent);
                        finish();
                    } else{
                        saldo -= monto;
                        actualizarDatos(datosUsuarioRec[0], datosUsuarioRec[1], saldo);
                        Intent intent = new Intent(getApplicationContext(), OperacionExitosaActivity.class);
                        intent.putExtras(datosRec);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        inicio = findViewById(R.id.buttonHomeRecarga);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Principal.class);
                intent.putExtras(datos);
                startActivity(intent);
            }
        });
    }



    public void actualizarDatos(String user, String pass, double saldo){
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
            //Intent intent = new Intent(MainActivity.this, MainActivity.class);
            //startActivity(intent);
        }

        // Obtenemos los datos del usuario a modificar
        String [] usuario = verificarUsuario(user, pass);
        // Los nuevos datos del usuario
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        String usuarioActualizado = usuario[0] + "," + usuario[1] + "," + usuario[2] + "," +
                usuario[3] + "," + usuario[4] + "," + usuario[5] + "," + usuario[6] + "," +
                usuario[7] + "," + decimalFormat.format(saldo);

        // Se borra el archivo, no es lo mejor, pero funciona.
        file.delete();
        // Se crea el nuevo archivo
        file = new File(getExternalFilesDir(null), "usuarios.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String[] x: listAcceso) {
                if (x[2].equals(user) && x[3].equals(pass)) {
                    bufferedWriter.write(usuarioActualizado);
                    bufferedWriter.newLine();
                } else {
                    String linea = x[0] + "," + x[1] + "," + x[2] + "," +
                            x[3] + "," + x[4] + "," + x[5] + "," + x[6] + "," +
                            x[7] + "," + x[8];

                    bufferedWriter.write(linea);
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.close();

        } catch (IOException e3){
            e3.printStackTrace();
            /*Toast.makeText(getApplicationContext(), "Error al guardar el usuario", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Registro2.this, Registro.class);
            startActivity(intent);*/
        }
    }

    public String[] verificarUsuario(String e, String p){
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
        boolean registrado = false;
        String[] usuario = new String[9];
        for (String[] x: listAcceso) {
            // Se encontró al usuario
            if(x[2].equals(e) && x[3].equals(p)){
                registrado = true;
                usuario = x;
                break;
            }
        }
        return usuario;
    }
}
