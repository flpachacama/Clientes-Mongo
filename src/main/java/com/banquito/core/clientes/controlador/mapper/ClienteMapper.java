package com.banquito.core.clientes.controlador.mapper;

import com.banquito.core.clientes.controlador.dto.*;
import com.banquito.core.clientes.modelo.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ClienteMapper {
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    ClienteDTO toDto(Clientes cliente);
    Clientes toClientes(ClienteDTO dto);

    List<ClienteDTO> toDtoList(List<Clientes> clientes);

    TelefonoClienteDTO toTelefonoDto(TelefonosClientes telefono);
    TelefonosClientes toTelefonoCliente(TelefonoClienteDTO dto);

    DireccionClienteDTO toDireccionDto(DireccionesClientes direccion);
    DireccionesClientes toDireccionCliente(DireccionClienteDTO dto);

    ContactoTransactionalClienteDTO toContactoDto(ContactosTransaccionalesClientes contacto);
    ContactosTransaccionalesClientes toContactoCliente(ContactoTransactionalClienteDTO dto);

    ClienteSucursalDTO toSucursalDto(ClientesSucursales sucursal);
    ClientesSucursales toSucursalCliente(ClienteSucursalDTO dto);
}