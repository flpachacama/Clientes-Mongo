package com.banquito.core.clientes.modelo;

import java.util.Date;
import lombok.Data;

@Data
public  class RepresentantesEmpresas {
    private String personaId; 
    private String rol;
    private Date fechaAsignacion;
    private String estado;
    private String version;
}