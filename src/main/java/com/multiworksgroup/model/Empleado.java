package com.multiworksgroup.model;
import java.util.Date;

public class Empleado extends Persona {
    private String tipoContratacion;
    public Empleado() {}

    public Empleado(int idPersona, String nombreCompleto, String documentoIdentidad,
                    String tipoPersona, String telefono, String correoElectronico,
                    String direccion, Date fechaCreacion, Date fechaActualizacion,
                    Date fechaInactivacion, String estado, String creadoPor,
                    String tipoContratacion) {
        super(idPersona, nombreCompleto, documentoIdentidad, tipoPersona, telefono,
                correoElectronico, direccion, fechaCreacion, fechaActualizacion,
                fechaInactivacion, estado, creadoPor);
        this.tipoContratacion = tipoContratacion;
    }

    public String getTipoContratacion() { return tipoContratacion; }
    public void setTipoContratacion(String tipoContratacion) { this.tipoContratacion = tipoContratacion; }

    @Override
    public String toString() {
        return "Empleado{" +
                "idEmpleado=" + getIdPersona() +
                ", tipoContratacion='" + tipoContratacion + '\'' +
                '}';
    }
}