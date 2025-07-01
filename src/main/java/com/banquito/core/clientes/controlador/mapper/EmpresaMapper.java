package com.banquito.core.clientes.controlador.mapper;

import com.banquito.core.clientes.controlador.dto.*;
import com.banquito.core.clientes.modelo.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EmpresaMapper {
    EmpresaMapper INSTANCE = Mappers.getMapper(EmpresaMapper.class);

    EmpresasDTO toDto(Empresas empresa);
    Empresas toEmpresas(EmpresasDTO dto);

    List<EmpresasDTO> toDtoList(List<Empresas> empresas);

    // Mappings para componentes
    AccionistasEmpresasDTO toAccionistaDto(AccionistasEmpresas accionista);
    AccionistasEmpresas toAccionistaEmpresa(AccionistasEmpresasDTO dto);

    RepresentanteEmpresaDTO toRepresentanteDto(RepresentantesEmpresas representante);
    RepresentantesEmpresas toRepresentanteEmpresa(RepresentanteEmpresaDTO dto);
}