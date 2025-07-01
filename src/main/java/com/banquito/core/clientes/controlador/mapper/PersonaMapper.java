package com.banquito.core.clientes.controlador.mapper;

import com.banquito.core.clientes.controlador.dto.PersonaDTO;
import com.banquito.core.clientes.modelo.Persona;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PersonaMapper {
    PersonaMapper INSTANCE = Mappers.getMapper(PersonaMapper.class);

    PersonaDTO toDto(Persona persona);
    Persona toPersona(PersonaDTO dto);

    List<PersonaDTO> toDtoList(List<Persona> personas);
}