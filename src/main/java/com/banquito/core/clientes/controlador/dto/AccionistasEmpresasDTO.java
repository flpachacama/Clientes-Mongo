package com.banquito.core.clientes.controlador.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccionistasEmpresasDTO {
    private String idEmpresa;
    private String idParticipe;
    private String personaId;
    private Double participacion;
    private String tipoEntidadParticipe;
    private String estado;
}