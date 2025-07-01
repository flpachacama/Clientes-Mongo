package com.banquito.core.clientes.servicio;

import com.banquito.core.clientes.controlador.dto.ContactoTransactionalClienteDTO;
import com.banquito.core.clientes.controlador.mapper.ContactoTransaccionalMapper;
import com.banquito.core.clientes.enums.EstadoRegistro;
import com.banquito.core.clientes.excepcion.*;
import com.banquito.core.clientes.modelo.Clientes;
import com.banquito.core.clientes.modelo.ContactosTransaccionalesClientes;
import com.banquito.core.clientes.repositorio.ClientesRepositorio;
import com.banquito.core.clientes.repositorio.ContactosTransaccionalesClientesRepositorio;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ContactosTransaccionalesClientesService {

    private final ContactosTransaccionalesClientesRepositorio contactoRepo;
    private final ClientesRepositorio clienteRepo;
    private final ContactoTransaccionalMapper mapper;

    public ContactosTransaccionalesClientesService(
            ContactosTransaccionalesClientesRepositorio contactoRepo,
            ClientesRepositorio clienteRepo,
            ContactoTransaccionalMapper mapper) {
        this.contactoRepo = contactoRepo;
        this.clienteRepo = clienteRepo;
        this.mapper = mapper;
    }

    @Transactional
    public ContactoTransactionalClienteDTO crearContacto(String idCliente, ContactoTransactionalClienteDTO dto) {
        try {
            log.info("Creando contacto transaccional para cliente ID: {}", idCliente);
            
            Clientes cliente = clienteRepo.findById(idCliente)
                    .orElseThrow(() -> new NotFoundException("Cliente no encontrado", 4001));

            if (contactoRepo.existsByIdClienteIdAndTelefono(idCliente, dto.getTelefono())) {
                throw new CreacionException("Ya existe un contacto con este teléfono", 4101);
            }
            
            if (contactoRepo.existsByIdClienteIdAndCorreoElectronico(idCliente, dto.getCorreoElectronico())) {
                throw new CreacionException("Ya existe un contacto con este correo electrónico", 4102);
            }

            ContactosTransaccionalesClientes entidad = mapper.toEntity(dto);
            entidad.setIdCliente(cliente);
            entidad.setEstado(EstadoRegistro.ACTIVO.name());
            entidad.setFechaCreacion(Date.from(Instant.now()));
            entidad.setFechaActualizacion(Date.from(Instant.now()));

            ContactosTransaccionalesClientes guardado = contactoRepo.save(entidad);
            return mapper.toDto(guardado);
            
        } catch (NotFoundException | CreacionException e) {
            log.error("Error al crear contacto: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al crear contacto", e);
            throw new CreacionException("Error al crear contacto transaccional", 4199);
        }
    }

    public ContactoTransactionalClienteDTO obtenerContacto(String idContacto) {
        try {
            log.info("Obteniendo contacto transaccional ID: {}", idContacto);
            
            ContactosTransaccionalesClientes entidad = contactoRepo.findById(idContacto)
                    .orElseThrow(() -> new NotFoundException("Contacto no encontrado", 4002));
            
            return mapper.toDto(entidad);
            
        } catch (NotFoundException e) {
            log.error("Error al obtener contacto: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al obtener contacto", e);
            throw new ConsultaException("Error al obtener contacto transaccional", 4299);
        }
    }

    public List<ContactoTransactionalClienteDTO> listarContactosPorCliente(String idCliente) {
        try {
            log.info("Listando contactos transaccionales para cliente ID: {}", idCliente);
            
            if (!clienteRepo.existsById(idCliente)) {
                throw new NotFoundException("Cliente no encontrado", 4003);
            }

            List<ContactosTransaccionalesClientes> contactos = contactoRepo.findByIdClienteId(idCliente);
            
            if (contactos.isEmpty()) {
                throw new NotFoundException("No hay contactos para este cliente", 4004);
            }
            
            return contactos.stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toList());
            
        } catch (NotFoundException e) {
            log.error("Error al listar contactos: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al listar contactos", e);
            throw new ConsultaException("Error al listar contactos transaccionales", 4399);
        }
    }

    @Transactional
    public ContactoTransactionalClienteDTO actualizarContacto(String idContacto, ContactoTransactionalClienteDTO dto) {
        try {
            log.info("Actualizando contacto transaccional ID: {}", idContacto);
            
            final ContactosTransaccionalesClientes entidad = contactoRepo.findById(idContacto)
                    .orElseThrow(() -> new NotFoundException("Contacto no encontrado", 4005));

            if (!entidad.getTelefono().equals(dto.getTelefono())) {
                if (contactoRepo.existsByIdClienteIdAndTelefono(entidad.getIdCliente().getId(), dto.getTelefono())) {
                    throw new ActualizacionException("Ya existe un contacto con este teléfono", 4401);
                }
            }
            
            if (!entidad.getCorreoElectronico().equals(dto.getCorreoElectronico())) {
                if (contactoRepo.existsByIdClienteIdAndCorreoElectronico(entidad.getIdCliente().getId(), dto.getCorreoElectronico())) {
                    throw new ActualizacionException("Ya existe un contacto con este correo electrónico", 4402);
                }
            }

            entidad.setTelefono(dto.getTelefono());
            entidad.setCorreoElectronico(dto.getCorreoElectronico());
            entidad.setFechaActualizacion(Date.from(Instant.now()));

            ContactosTransaccionalesClientes actualizado = contactoRepo.save(entidad);
            return mapper.toDto(actualizado);
            
        } catch (NotFoundException | ActualizacionException e) {
            log.error("Error al actualizar contacto: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al actualizar contacto", e);
            throw new ActualizacionException("Error al actualizar contacto transaccional", 4499);
        }
    }

    @Transactional
    public void cambiarEstadoContacto(String idContacto, EstadoRegistro nuevoEstado) {
        try {
            log.info("Cambiando estado del contacto ID: {} a {}", idContacto, nuevoEstado);
            
            if (nuevoEstado == null) {
                throw new ValidacionException("Estado no puede ser nulo", 4401);
            }
            
            ContactosTransaccionalesClientes entidad = contactoRepo.findById(idContacto)
                    .orElseThrow(() -> new NotFoundException("Contacto no encontrado", 4006));

            entidad.setEstado(nuevoEstado.name());

            contactoRepo.save(entidad);
            
        } catch (NotFoundException | ValidacionException e) {
            log.error("Error al cambiar estado contacto: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al cambiar estado contacto", e);
            throw new ActualizacionException("Error al cambiar estado de contacto", 4599);
        }
    }
}