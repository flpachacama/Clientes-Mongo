package com.banquito.core.clientes.controlador.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

import com.banquito.core.clientes.modelo.Clientes;

@Data
@Builder
public class ContactoTransactionalClienteDTO {
    
    private Clientes idCliente;
    private String telefono;
    private String correoElectronico;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private String estado;
}