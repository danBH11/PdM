package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Registro2 extends AppCompatActivity {

    EditText nombreTarjeta, numeroTarjeta, fecha, cvv;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2);
        //Se reciben los datos del primer registro
        Bundle datosRec = getIntent().getExtras();
        String datosUsuarioRec = datosRec.getString("keyDatos");

        btnRegistrar = findViewById(R.id.button4);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Atrapamos los datos que se escriben en el registro
                nombreTarjeta = findViewById(R.id.editTextTextPersonName3);
                numeroTarjeta = findViewById(R.id.editTextNumber2);
                fecha = findViewById(R.id.editTextDate);
                cvv = findViewById(R.id.editTextNumber);

                if(nombreTarjeta.getText().toString().equals("") || numeroTarjeta.getText().toString().equals("") ||
                        fecha.getText().toString().equals("") || cvv.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Complete el registro", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Registro2.this, Registro2.class);
                    intent.putExtras(datosRec);
                    startActivity(intent);
                } else{
                    // Comprobar que no haya otra persona con ese mismo número de cuenta
                    boolean cuentaRegistrada = false;
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
                        if(x[5].equals(numeroTarjeta.getText().toString())){
                            cuentaRegistrada = true;
                            break;
                        }
                    }
                    if(cuentaRegistrada) {
                        Toast.makeText(getApplicationContext(), "Número de tarjeta ya registrado", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Registro2.this, Registro2.class);
                        intent.putExtras(datosRec);
                        startActivity(intent);
                        finish();
                    } else{
                        //Número aleatorio que representará el saldo disponible en la tarjeta.
                        DecimalFormat decimalFormat = new DecimalFormat("#.###");
                        Random random = new Random();
                        double saldo = random.nextDouble() * 10000;


                        String datosUsuario = nombreTarjeta.getText().toString() + "," +
                                numeroTarjeta.getText().toString() + "," +
                                fecha.getText().toString() + "," +
                                cvv.getText().toString() +"," + decimalFormat.format(saldo);

                        // Se obtienen los datos de acceso
                        String[] arreglo = datosUsuarioRec.split(",");
                        String acceso = arreglo[2] + "," + arreglo[3] + "," + numeroTarjeta.getText().toString();

                        datosUsuario = datosUsuarioRec + "," + datosUsuario;
                        System.out.println("Datos: " + datosUsuario);

                        Bundle datos = new Bundle();
                        datos.putString("keyDatos", datosUsuario);

                        // Guardar los datos del usuario
                        // La ruta del directorio de almacenamiento externo
                        file = new File(getExternalFilesDir(null), "usuarios.txt");
                        // Guardar datos de acceso
                        File file2 = new File(getExternalFilesDir(null), "acceso.txt");

                        try {
                            FileWriter fileWriter = new FileWriter(file, true);
                            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                            bufferedWriter.write(datosUsuario);
                            bufferedWriter.newLine();
                            bufferedWriter.close();

                            // Para el segundo archivo
                            FileWriter fileWriter2 = new FileWriter(file2, true);
                            BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
                            bufferedWriter2.write(acceso);
                            bufferedWriter2.newLine();
                            bufferedWriter2.close();


                            //Toast.makeText(getApplicationContext(), "Usuario Registrado correctamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Registro2.this, RegistroExitoso.class);
                            intent.putExtras(datos);
                            startActivity(intent);

                        } catch (IOException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error al guardar el usuario", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Registro2.this, Registro.class);
                            startActivity(intent);
                        }

                    }
                }
            }
        });
    }
    /** Registro exitoso*/
/*    public void irRegistroExitoso(View view){
        Intent in = new Intent(getApplicationContext(), RegistroExitoso.class);
        startActivity(in);
    }*/
}