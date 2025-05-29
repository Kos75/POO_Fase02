package com.multiworksgroup.model;

import java.util.ArrayList;
import java.util.List;

public class Sistema {
    private List<Cliente> clientes;
    private List<Empleado> empleados;
    private List<Actividad> actividades;
    private List<AsignacionTarea> asignaciones;
    private List<Cotizacion> cotizaciones;

    public Sistema() {
        this.clientes = new ArrayList<>();
        this.empleados = new ArrayList<>();
        this.actividades = new ArrayList<>();
        this.asignaciones = new ArrayList<>();
        this.cotizaciones = new ArrayList<>();
    }

    // Métodos para agregar entidades
    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void agregarEmpleado(Empleado empleado) {
        empleados.add(empleado);
    }

    public void agregarActividad(Actividad actividad) {
        actividades.add(actividad);
    }

    public void agregarAsignacion(AsignacionTarea asignacion) {
        asignaciones.add(asignacion);
    }

    public void agregarCotizacion(Cotizacion cotizacion) {
        cotizaciones.add(cotizacion);
    }

    // Métodos para obtener listas
    public List<Cliente> getClientes() { return clientes; }
    public List<Empleado> getEmpleados() { return empleados; }
    public List<Actividad> getActividades() { return actividades; }
    public List<AsignacionTarea> getAsignaciones() { return asignaciones; }
    public List<Cotizacion> getCotizaciones() { return cotizaciones; }

    @Override
    public String toString() {
        return "Sistema{" +
                "clientes=" + clientes.size() +
                ", empleados=" + empleados.size() +
                ", actividades=" + actividades.size() +
                ", asignaciones=" + asignaciones.size() +
                ", cotizaciones=" + cotizaciones.size() +
                '}';
    }
}