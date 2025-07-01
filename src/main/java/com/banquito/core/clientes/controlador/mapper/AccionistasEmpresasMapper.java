package com.banquito.core.clientes.controlador.mapper;

import com.banquito.core.clientes.controlador.dto.AccionistasEmpresasDTO;
import com.banquito.core.clientes.modelo.AccionistasEmpresas;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccionistasEmpresasMapper {
    AccionistasEmpresasMapper INSTANCE = Mappers.getMapper(AccionistasEmpresasMapper.class);

    AccionistasEmpresasDTO toDto(AccionistasEmpresas accionista);
    AccionistasEmpresas toEntity(AccionistasEmpresasDTO dto);
}