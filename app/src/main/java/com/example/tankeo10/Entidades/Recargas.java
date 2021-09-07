package com.example.tankeo10.Entidades;

public class Recargas {
    private String fecha;
    private String tipo;

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    private String valor;



    public Recargas() {
    }


    public Recargas(String fecha, String tipo, String valor) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.valor = valor;

    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }



}
