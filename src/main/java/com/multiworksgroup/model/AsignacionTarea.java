package com.multiworksgroup.model;

public class AsignacionTarea {
    private int idAsignacion;
    private int idEmpleado;
    private int idActividad;
    private String titulo;
    private String descripcion;

    public AsignacionTarea() {}

    public AsignacionTarea(int idAsignacion, int idEmpleado, int idActividad,
                           String titulo, String descripcion) {
        this.idAsignacion = idAsignacion;
        this.idEmpleado = idEmpleado;
        this.idActividad = idActividad;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getIdAsignacion() { return idAsignacion; }
    public void setIdAsignacion(int idAsignacion) { this.idAsignacion = idAsignacion; }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public int getIdActividad() { return idActividad; }
    public void setIdActividad(int idActividad) { this.idActividad = idActividad; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() {
        return "AsignacionTarea{" +
                "titulo='" + titulo + '\'' +
                '}';
    }
}