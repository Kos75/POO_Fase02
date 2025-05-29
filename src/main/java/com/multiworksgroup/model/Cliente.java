package com.multiworksgroup.model;
import java.util.Date;


public class Cliente extends Persona {
    private String tipoCliente;

    public Cliente() {}

    public Cliente(int idPersona, String nombreCompleto, String documentoIdentidad,
                   String tipoPersona, String telefono, String correoElectronico,
                   String direccion, Date fechaCreacion, Date fechaActualizacion,
                   Date fechaInactivacion, String estado, String creadoPor, String tipoCliente) {
        super(idPersona, nombreCompleto, documentoIdentidad, tipoPersona, telefono,
                correoElectronico, direccion, fechaCreacion, fechaActualizacion,
                fechaInactivacion, estado, creadoPor);
        this.tipoCliente = tipoCliente;
    }

    public String getTipoCliente() { return tipoCliente; }
    public void setTipoCliente(String tipoCliente) { this.tipoCliente = tipoCliente; }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + getIdPersona() +
                ", tipoCliente='" + tipoCliente + '\'' +
                '}';
    }
}