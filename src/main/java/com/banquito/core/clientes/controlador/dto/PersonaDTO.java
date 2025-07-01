package com.banquito.core.clientes.controlador.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class PersonaDTO {
    private String id;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombre;
    private String genero;
    private Date fechaNacimiento;
    private String estadoCivil;
    private String nivelEstudio;
    private String correoElectronico;
    private List<String> clienteIds;
    private Date fechaRegistro;
    private Date fechaActualizacion;
    private String estado;
}