package com.example.myapplication;

public class Usuario {
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String nombreTarja;
    private String numeroTarjeta;
    private String fecha;
    private String cvv;
    private String saldo;

    public Usuario(String nombre, String apellido, String email, String password, String nombreTarja, String numeroTarjeta, String fecha, String cvv, String saldo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.nombreTarja = nombreTarja;
        this.numeroTarjeta = numeroTarjeta;
        this.fecha = fecha;
        this.cvv = cvv;
        this.saldo = saldo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public String getNombreTarja() {
        return nombreTarja;
    }

    public void setNombreTarja(String nombreTarja) {
        this.nombreTarja = nombreTarja;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
}
