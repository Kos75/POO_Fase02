package com.multiworksgroup.model;

import java.util.Date;

public class Cotizacion {
    private int idCotizacion;
    private int idCliente;
    private String numeroCotizacion;
    private double horasProyecto;
    private Date fechaInicio;
    private Date fechaFin;
    private double costosAsignados;
    private double costosAdicionales;
    private double total;
    private String estado; // "en proceso", "finalizada", "cancelada"

    public Cotizacion() {}

    public Cotizacion(int idCotizacion, int idCliente, String numeroCotizacion,
                      double horasProyecto, Date fechaInicio, Date fechaFin,
                      double costosAsignados, double costosAdicionales, double total,
                      String estado) {
        this.idCotizacion = idCotizacion;
        this.idCliente = idCliente;
        this.numeroCotizacion = numeroCotizacion;
        this.horasProyecto = horasProyecto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.costosAsignados = costosAsignados;
        this.costosAdicionales = costosAdicionales;
        this.total = total;
        this.estado = estado;
    }

    // Métodos
    public void calcularTotal() {
        this.total = this.costosAsignados + this.costosAdicionales;
    }

    // Getters y Setters
    public int getIdCotizacion() { return idCotizacion; }
    public void setIdCotizacion(int idCotizacion) { this.idCotizacion = idCotizacion; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getNumeroCotizacion() { return numeroCotizacion; }
    public void setNumeroCotizacion(String numeroCotizacion) { this.numeroCotizacion = numeroCotizacion; }

    public double getHorasProyecto() { return horasProyecto; }
    public void setHorasProyecto(double horasProyecto) { this.horasProyecto = horasProyecto; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public double getCostosAsignados() { return costosAsignados; }
    public void setCostosAsignados(double costosAsignados) { this.costosAsignados = costosAsignados; }

    public double getCostosAdicionales() { return costosAdicionales; }
    public void setCostosAdicionales(double costosAdicionales) { this.costosAdicionales = costosAdicionales; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Cotizacion{" +
                "idCotizacion=" + idCotizacion +
                ", numeroCotizacion='" + numeroCotizacion + '\'' +
                ", total=" + total +
                '}';
    }
}