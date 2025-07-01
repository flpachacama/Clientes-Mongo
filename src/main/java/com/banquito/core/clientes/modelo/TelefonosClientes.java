package com.banquito.core.clientes.modelo;

import java.util.Date;

import lombok.Data;
@Data
public class TelefonosClientes {
    private String tipo; // MOVIL, FIJO, TRABAJO
    private String numero;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private String estado;
    private String version;
}