package com.banquito.core.clientes.controlador.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class EmpresasDTO {
    private String id;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombreComercial;
    private String razonSocial;
    private String tipo;
    private Date fechaConstitucion;
    private String correoElectronico;
    private String sectorEconomico;
    private String clienteId;
    private Date fechaRegistro;
    private Date fechaActualizacion;
    private String estado;
    private List<AccionistasEmpresasDTO> accionistas;
    private List<RepresentanteEmpresaDTO> representantes;
}