package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UsuariosDao {

    @Query("SELECT * FROM usuarios")
    List<Usuarios> getAll();

    @Query("SELECT * FROM usuarios WHERE nombreUsuario LIKE :usuario LIMIT 1")
    Usuarios findByName(String usuario);

    @Query("SELECT * FROM usuarios WHERE email LIKE :email LIMIT 1")
    Usuarios findByEMail(String email);

    @Insert
    Long insert(Usuarios usuarios);
}
