package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuarios {
    public Usuarios(String nombreUsuario, String apellidos, String email, String password) {
        this.nombreUsuario = nombreUsuario;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
    }

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "nombreUsuario")
    String nombreUsuario;

    @ColumnInfo(name = "apellidos")
    String apellidos;

    @ColumnInfo(name = "email")
    String email;

    @ColumnInfo(name = "password")
    String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
