package com.multiworksgroup.model;

import java.util.Date;

public class Persona {
    private int idPersona;
    private String nombreCompleto;
    private String documentoIdentidad;
    private String tipoPersona; // "cliente" o "empleado"
    private String telefono;
    private String correoElectronico;
    private String direccion;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private Date fechaInactivacion;
    private String estado; // "activo", "inactivo"
    private String creadoPor;

    public Persona() {}

    public Persona(int idPersona, String nombreCompleto, String documentoIdentidad,
                   String tipoPersona, String telefono, String correoElectronico,
                   String direccion, Date fechaCreacion, Date fechaActualizacion,
                   Date fechaInactivacion, String estado, String creadoPor) {
        this.idPersona = idPersona;
        this.nombreCompleto = nombreCompleto;
        this.documentoIdentidad = documentoIdentidad;
        this.tipoPersona = tipoPersona;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.direccion = direccion;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.fechaInactivacion = fechaInactivacion;
        this.estado = estado;
        this.creadoPor = creadoPor;
    }

    // Getters y Setters
    public int getIdPersona() { return idPersona; }
    public void setIdPersona(int idPersona) { this.idPersona = idPersona; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getDocumentoIdentidad() { return documentoIdentidad; }
    public void setDocumentoIdentidad(String documentoIdentidad) { this.documentoIdentidad = documentoIdentidad; }

    public String getTipoPersona() { return tipoPersona; }
    public void setTipoPersona(String tipoPersona) { this.tipoPersona = tipoPersona; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Date getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(Date fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    public Date getFechaInactivacion() { return fechaInactivacion; }
    public void setFechaInactivacion(Date fechaInactivacion) { this.fechaInactivacion = fechaInactivacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCreadoPor() { return creadoPor; }
    public void setCreadoPor(String creadoPor) { this.creadoPor = creadoPor; }

    @Override
    public String toString() {
        return "Persona{" +
                "idPersona=" + idPersona +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", documentoIdentidad='" + documentoIdentidad + '\'' +
                ", tipopersona='" + tipoPersona + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", fechaActualizacion='" + fechaActualizacion + '\'' +
                ", fechaInactivacion='" + fechaInactivacion + '\'' +
                ", estado='" + estado + '\'' +
                ", creadoPor='" + creadoPor + '\'' +
                '}';
    }
}