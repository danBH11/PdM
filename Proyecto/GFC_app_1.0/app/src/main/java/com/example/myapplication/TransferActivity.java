package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class TransferActivity extends AppCompatActivity {

    EditText cuenta, concepto, cantidad;
    Button btnTransferir, inicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        //Toolbar toolbar = findViewById(R.id.);
        //setSupportActionBar(toolbar);

        // Se reciben el id, la password, el número de cuenta y el saldo
        Bundle datosRec = getIntent().getExtras();
        String[] datosUsuarioRec = datosRec.getStringArray("keyDatos");

        // Le quitamos los últimos dos elementos para regresar a Principal
        String[] datosRegreso = {datosUsuarioRec[0], datosUsuarioRec[1]};
        Bundle datos = new Bundle();
        datos.putStringArray("keyDatos", datosRegreso);

        btnTransferir = findViewById(R.id.buttonTransfer);
        btnTransferir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cuenta = findViewById(R.id.editTextTransf1);
                concepto = findViewById(R.id.editTextTransConcepto);
                cantidad = findViewById(R.id.editTextCantidad);


                if(cuenta.getText().toString().equals("") || concepto.getText().toString().equals("") || cantidad.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Complete el formulario", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), TransferActivity.class);
                    intent.putExtras(datosRec);
                    startActivity(intent);
                    finish();
                }
                else{
                    double monto = Double.parseDouble(cantidad.getText().toString());
                    //String[] datos = datosUsuarioRec.split(",");
                    double saldo = Double.parseDouble((datosUsuarioRec[3]));

                    if (cuenta.getText().toString().equals(datosUsuarioRec[2])){
                        Toast.makeText(getApplicationContext(), "Operación no permitida", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), TransferActivity.class);
                        intent.putExtras(datosRec);
                        startActivity(intent);
                        finish();
                    } else if(monto > saldo){
                        Toast.makeText(getApplicationContext(), "Saldo disponible no suficiente", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), TransferActivity.class);
                        intent.putExtras(datosRec);
                        startActivity(intent);
                        finish();
                    } else if (monto <= 0) {
                        Toast.makeText(getApplicationContext(), "Cantidad inválida", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), TransferActivity.class);
                        intent.putExtras(datosRec);
                        startActivity(intent);
                        finish();
                    } else{
                        saldo -= monto;
                        actualizarDatos(datosUsuarioRec[0], datosUsuarioRec[1], saldo, monto, cuenta.getText().toString());
                        Intent intent = new Intent(getApplicationContext(), OperacionExitosaActivity.class);
                        intent.putExtras(datosRec);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        inicio = findViewById(R.id.buttonHomeTrans);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Principal.class);
                intent.putExtras(datos);
                startActivity(intent);
            }
        });

    }

    public void actualizarDatos(String user, String pass, double saldo, double monto, String cuenta){
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
                    String linea;
                    if(cuenta.equals(x[5])){
                        double valor = Double.parseDouble(x[8]);
                        valor += monto;
                        linea = x[0] + "," + x[1] + "," + x[2] + "," +
                                x[3] + "," + x[4] + "," + x[5] + "," + x[6] + "," +
                                x[7] + "," + decimalFormat.format(valor);
                    } else{
                        linea = x[0] + "," + x[1] + "," + x[2] + "," +
                                x[3] + "," + x[4] + "," + x[5] + "," + x[6] + "," +
                                x[7] + "," + x[8];
                    }
                    bufferedWriter.write(linea);
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.close();

        } catch (IOException e3){
            e3.printStackTrace();
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