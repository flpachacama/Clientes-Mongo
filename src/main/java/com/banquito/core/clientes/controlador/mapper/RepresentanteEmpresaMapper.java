package com.banquito.core.clientes.controlador.mapper;

import com.banquito.core.clientes.controlador.dto.RepresentanteEmpresaDTO;
import com.banquito.core.clientes.modelo.RepresentantesEmpresas;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RepresentanteEmpresaMapper {
    RepresentanteEmpresaMapper INSTANCE = Mappers.getMapper(RepresentanteEmpresaMapper.class);

    RepresentanteEmpresaDTO toDto(RepresentantesEmpresas representante);
    RepresentantesEmpresas toEntity(RepresentanteEmpresaDTO dto);
}