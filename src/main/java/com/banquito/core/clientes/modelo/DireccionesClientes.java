package com.banquito.core.clientes.modelo;

import java.util.Date;

import lombok.Data;

@Data
public class DireccionesClientes {
    private String tipo; 
    private String linea1;
    private String linea2;
    private String codigoPostal;
    private String codigoGeografico;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private String estado;
    private String version;
}