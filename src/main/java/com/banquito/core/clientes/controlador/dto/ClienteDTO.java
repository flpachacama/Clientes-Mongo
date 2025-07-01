package com.banquito.core.clientes.controlador.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ClienteDTO {
    private String id;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombre;
    private String tipoEntidad;
    private String segmento;
    private String canalAfiliacion;
    private String estado;
    private Date fechaRegistro;
    private Date fechaActualizacion;
    private String correoElectronico;
    private List<TelefonoClienteDTO> telefonos;
    private List<DireccionClienteDTO> direcciones;
    private ContactoTransactionalClienteDTO contactoTransaccional;
    private List<ClienteSucursalDTO> sucursales;
}