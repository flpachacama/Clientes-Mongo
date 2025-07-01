package com.banquito.core.clientes.controlador.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TelefonoClienteDTO {
    private String tipo;
    private String numero;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private String estado;
}