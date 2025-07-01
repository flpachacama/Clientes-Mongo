package com.banquito.core.clientes.controlador.mapper;

import com.banquito.core.clientes.controlador.dto.ContactoTransactionalClienteDTO;
import com.banquito.core.clientes.enums.EstadoRegistro;
import com.banquito.core.clientes.modelo.ContactosTransaccionalesClientes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactoTransaccionalMapper {
    ContactoTransaccionalMapper INSTANCE = Mappers.getMapper(ContactoTransaccionalMapper.class);

    @Mapping(source = "estado", target = "estado", qualifiedByName = "stringToEstadoRegistro")
    ContactoTransactionalClienteDTO toDto(ContactosTransaccionalesClientes entidad);

    @Mapping(source = "estado", target = "estado", qualifiedByName = "estadoRegistroToString")
    ContactosTransaccionalesClientes toEntity(ContactoTransactionalClienteDTO dto);

    @Named("stringToEstadoRegistro")
    default EstadoRegistro stringToEstadoRegistro(String estado) {
        return EstadoRegistro.valueOf(estado);
    }

    @Named("estadoRegistroToString")
    default String estadoRegistroToString(EstadoRegistro estado) {
        return estado.name();
    }
}