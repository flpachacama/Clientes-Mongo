package com.banquito.core.clientes.modelo;

import java.util.Date;

import lombok.Data;

@Data
public class ContactosTransaccionalesClientes {
    private Clientes idCliente;
    private String telefono;
    private String correoElectronico;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private String estado;
    private String version;
}