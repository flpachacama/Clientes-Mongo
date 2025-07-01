package com.banquito.core.clientes.controlador.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ClienteSucursalDTO {
    private String codigoSucursal;
    private String estado;
    private String canalAfiliacion;
    private Date fechaActualizacion;
    private String comentarios;
}