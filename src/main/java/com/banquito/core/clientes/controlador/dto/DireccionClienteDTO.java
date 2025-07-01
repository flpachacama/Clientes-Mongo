package com.banquito.core.clientes.controlador.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class DireccionClienteDTO {
    private String tipo;
    private String linea1;
    private String linea2;
    private String codigoPostal;
    private String codigoGeografico;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private String estado;
}