package com.banquito.core.clientes.modelo;

import lombok.Data;

@Data
public class AccionistasEmpresas {
    private String idAccionista;
    private String idEmpresa;
    private String idParticipe;
    private Double participacion;
    private String tipoEntidadParticipe;
    private String estado;
    private Long version;
}