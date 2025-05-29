package com.multiworksgroup.model;

import java.util.Date;

public class Actividad {
    private int idActividad;
    private String titulo;
    private String areaAsignada;
    private double costoPorHora;
    private Date fechaInicio;
    private Date fechaFin;
    private double cantidadHoras;
    private double costoBase;
    private double incrementoExtra;
    private double total;

    public Actividad() {}

    public Actividad(int idActividad, String titulo, String areaAsignada,
                     double costoPorHora, Date fechaInicio, Date fechaFin,
                     double cantidadHoras, double costoBase, double incrementoExtra,
                     double total) {
        this.idActividad = idActividad;
        this.titulo = titulo;
        this.areaAsignada = areaAsignada;
        this.costoPorHora = costoPorHora;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cantidadHoras = cantidadHoras;
        this.costoBase = costoBase;
        this.incrementoExtra = incrementoExtra;
        this.total = total;
    }

    public void calcularTotal() {
        this.total = this.costoBase + this.incrementoExtra;
    }

    // Getters y Setters
    public int getIdActividad() { return idActividad; }
    public void setIdActividad(int idActividad) { this.idActividad = idActividad; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAreaAsignada() { return areaAsignada; }
    public void setAreaAsignada(String areaAsignada) { this.areaAsignada = areaAsignada; }

    public double getCostoPorHora() { return costoPorHora; }
    public void setCostoPorHora(double costoPorHora) { this.costoPorHora = costoPorHora; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public double getCantidadHoras() { return cantidadHoras; }
    public void setCantidadHoras(double cantidadHoras) { this.cantidadHoras = cantidadHoras; }

    public double getCostoBase() { return costoBase; }
    public void setCostoBase(double costoBase) { this.costoBase = costoBase; }

    public double getIncrementoExtra() { return incrementoExtra; }
    public void setIncrementoExtra(double incrementoExtra) { this.incrementoExtra = incrementoExtra; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    @Override
    public String toString() {
        return "Actividad{" +
                "titulo='" + titulo + '\'' +
                ", total=" + total +
                '}';
    }
}