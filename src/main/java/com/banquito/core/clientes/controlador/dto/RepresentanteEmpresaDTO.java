package com.banquito.core.clientes.controlador.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RepresentanteEmpresaDTO {
    private String idCliente;
    private String idEmpresa;
    private String rol;
    private Date fechaAsignacion;
    private String estado;
}