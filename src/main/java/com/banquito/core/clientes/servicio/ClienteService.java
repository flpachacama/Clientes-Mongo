package com.banquito.core.clientes.servicio;

import com.banquito.core.clientes.controlador.dto.*;
import com.banquito.core.clientes.controlador.mapper.*;
import com.banquito.core.clientes.excepcion.*;
import com.banquito.core.clientes.modelo.*;
import com.banquito.core.clientes.repositorio.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClienteService {

    private final PersonaRepositorio personaRepo;
    private final EmpresasRepositorio empresaRepo;
    private final ClientesRepositorio clienteRepo;
    private final PersonaMapper personaMapper;
    private final EmpresaMapper empresaMapper;
    private final ClienteMapper clienteMapper;

    public ClienteService(PersonaRepositorio personaRepo,
                         EmpresasRepositorio empresaRepo,
                         ClientesRepositorio clienteRepo,
                         PersonaMapper personaMapper,
                         EmpresaMapper empresaMapper,
                         ClienteMapper clienteMapper) {
        this.personaRepo = personaRepo;
        this.empresaRepo = empresaRepo;
        this.clienteRepo = clienteRepo;
        this.personaMapper = personaMapper;
        this.empresaMapper = empresaMapper;
        this.clienteMapper = clienteMapper;
    }

    // ========== MÉTODOS PARA PERSONAS ==========

    @Transactional
    public PersonaDTO crearPersona(PersonaDTO personaDTO) {
        try {
            log.info("Creando persona: {} {}", personaDTO.getTipoIdentificacion(), personaDTO.getNumeroIdentificacion());

            if (personaRepo.existsByTipoIdentificacionAndNumeroIdentificacion(
                    personaDTO.getTipoIdentificacion(),
                    personaDTO.getNumeroIdentificacion())) {
                throw new CreacionException("Persona ya existe", 1101);
            }

            Persona persona = personaMapper.toPersona(personaDTO);
            persona.setFechaRegistro(Date.from(Instant.now()));
            persona.setFechaActualizacion(Date.from(Instant.now()));
            persona.setEstado("ACTIVO");
            persona = personaRepo.save(persona);
            return personaMapper.toDto(persona);
        } catch (CreacionException e) {
            log.error("Error al crear persona: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al crear persona", e);
            throw new CreacionException("Error al crear persona", 1199);
        }
    }

    public PersonaDTO obtenerPersona(String tipo, String numero) {
        log.info("Obteniendo persona: {} {}", tipo, numero);
        Persona persona = personaRepo.findByTipoIdentificacionAndNumeroIdentificacion(tipo, numero)
                .orElseThrow(() -> new NotFoundException("Persona no encontrada", 3101));
        return personaMapper.toDto(persona);
    }

    public List<PersonaDTO> buscarPersonas(String nombre) {
        log.info("Buscando personas: {}", nombre);
        List<Persona> personas = personaRepo.findByNombreLikeOrderByNombreAsc("%" + nombre + "%");

        if (personas.isEmpty()) {
            throw new NotFoundException("No se encontraron personas", 3102);
        }

        return personas.stream()
                .limit(100)
                .map(personaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PersonaDTO actualizarPersona(String id, PersonaDTO personaDTO) {
        try {
            log.info("Actualizando persona ID: {}", id);
            Persona persona = personaRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Persona no encontrada", 3103));

            persona.setNombre(personaDTO.getNombre());
            persona.setGenero(personaDTO.getGenero());
            persona.setFechaNacimiento(personaDTO.getFechaNacimiento());
            persona.setEstadoCivil(personaDTO.getEstadoCivil());
            persona.setNivelEstudio(personaDTO.getNivelEstudio());
            persona.setCorreoElectronico(personaDTO.getCorreoElectronico());
            persona.setFechaActualizacion(Date.from(Instant.now()));

            persona = personaRepo.save(persona);
            return personaMapper.toDto(persona);
        } catch (NotFoundException e) {
            log.error("Error al actualizar persona: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al actualizar persona", e);
            throw new ActualizacionException("Error al actualizar persona", 2199);
        }
    }

    // ========== MÉTODOS PARA EMPRESAS ==========

    @Transactional
    public EmpresasDTO crearEmpresa(EmpresasDTO empresasDTO) {
        try {
            log.info("Creando empresa: {} {}", empresasDTO.getTipoIdentificacion(), empresasDTO.getNumeroIdentificacion());

            if (empresaRepo.existsByTipoIdentificacionAndNumeroIdentificacion(
                    empresasDTO.getTipoIdentificacion(),
                    empresasDTO.getNumeroIdentificacion())) {
                throw new CreacionException("Empresa ya existe", 1201);
            }

            Empresas empresa = empresaMapper.toEmpresas(empresasDTO);
            empresa.setFechaRegistro(Date.from(Instant.now()));
            empresa.setFechaActualizacion(Date.from(Instant.now()));
            empresa.setEstado("ACTIVO");
            empresa = empresaRepo.save(empresa);
            return empresaMapper.toDto(empresa);
        } catch (CreacionException e) {
            log.error("Error al crear empresa: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al crear empresa", e);
            throw new CreacionException("Error al crear empresa", 1299);
        }
    }

    public EmpresasDTO obtenerEmpresa(String tipo, String numero) {
        log.info("Obteniendo empresa: {} {}", tipo, numero);
        Empresas empresa = empresaRepo.findByTipoIdentificacionAndNumeroIdentificacion(tipo, numero)
                .orElseThrow(() -> new NotFoundException("Empresa no encontrada", 3201));
        return empresaMapper.toDto(empresa);
    }

    public List<EmpresasDTO> buscarEmpresasPorRazon(String razonSocial) {
        log.info("Buscando empresas por razón social: {}", razonSocial);
        List<Empresas> empresas = empresaRepo.findByRazonSocialLikeOrderByRazonSocialAsc("%" + razonSocial + "%");

        if (empresas.isEmpty()) {
            throw new NotFoundException("No se encontraron empresas", 3202);
        }

        return empresas.stream()
                .limit(100)
                .map(empresaMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EmpresasDTO> buscarEmpresasPorNombre(String nombreComercial) {
        log.info("Buscando empresas por nombre comercial: {}", nombreComercial);
        List<Empresas> empresas = empresaRepo.findByNombreComercialLikeOrderByNombreComercialAsc("%" + nombreComercial + "%");

        if (empresas.isEmpty()) {
            throw new NotFoundException("No se encontraron empresas", 3203);
        }

        return empresas.stream()
                .limit(100)
                .map(empresaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmpresasDTO actualizarEmpresa(String id, EmpresasDTO empresasDTO) {
        try {
            log.info("Actualizando empresa ID: {}", id);
            Empresas empresa = empresaRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Empresa no encontrada", 3204));

            empresa.setNombreComercial(empresasDTO.getNombreComercial());
            empresa.setRazonSocial(empresasDTO.getRazonSocial());
            empresa.setTipo(empresasDTO.getTipo());
            empresa.setCorreoElectronico(empresasDTO.getCorreoElectronico());
            empresa.setSectorEconomico(empresasDTO.getSectorEconomico());
            empresa.setFechaActualizacion(Date.from(Instant.now()));

            empresa = empresaRepo.save(empresa);
            return empresaMapper.toDto(empresa);
        } catch (NotFoundException e) {
            log.error("Error al actualizar empresa: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al actualizar empresa", e);
            throw new ActualizacionException("Error al actualizar empresa", 2299);
        }
    }

    // ========== MÉTODOS PARA CLIENTES ==========

    @Transactional
    public ClienteDTO crearClientePersona(String idPersona, ClienteDTO clienteDTO) {
        try {
            log.info("Creando cliente desde persona ID: {}", idPersona);
            Persona persona = personaRepo.findById(idPersona)
                    .orElseThrow(() -> new NotFoundException("Persona no encontrada", 3104));

            if (clienteRepo.existsByIdEntidadAndTipoEntidad(persona.getId(), "PERSONA")) {
                throw new CreacionException("Persona ya es cliente", 1301);
            }

            clienteDTO.setTipoEntidad("PERSONA");
            clienteDTO.setNombre(persona.getNombre());
            clienteDTO.setTipoIdentificacion(persona.getTipoIdentificacion());
            clienteDTO.setNumeroIdentificacion(persona.getNumeroIdentificacion());

            Clientes cliente = clienteMapper.toClientes(clienteDTO);
            cliente.setFechaRegistro(Date.from(Instant.now()));
            cliente.setFechaActualizacion(Date.from(Instant.now()));
            cliente.setEstado("ACTIVO");
            cliente = clienteRepo.save(cliente);
            return clienteMapper.toDto(cliente);
        } catch (NotFoundException | CreacionException e) {
            log.error("Error al crear cliente persona: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al crear cliente persona", e);
            throw new CreacionException("Error al crear cliente persona", 1399);
        }
    }

    @Transactional
    public ClienteDTO crearClienteEmpresa(String idEmpresa, ClienteDTO clienteDTO) {
        try {
            log.info("Creando cliente desde empresa ID: {}", idEmpresa);
            Empresas empresa = empresaRepo.findById(idEmpresa)
                    .orElseThrow(() -> new NotFoundException("Empresa no encontrada", 3205));

            if (clienteRepo.existsByIdEntidadAndTipoEntidad(empresa.getId(), "EMPRESA")) {
                throw new CreacionException("Empresa ya es cliente", 1302);
            }

            clienteDTO.setTipoEntidad("EMPRESA");
            clienteDTO.setNombre(empresa.getRazonSocial());
            clienteDTO.setTipoIdentificacion(empresa.getTipoIdentificacion());
            clienteDTO.setNumeroIdentificacion(empresa.getNumeroIdentificacion());

            Clientes cliente = clienteMapper.toClientes(clienteDTO);
            cliente.setFechaRegistro(Date.from(Instant.now()));
            cliente.setFechaActualizacion(Date.from(Instant.now()));
            cliente.setEstado("ACTIVO");
            cliente = clienteRepo.save(cliente);
            return clienteMapper.toDto(cliente);
        } catch (NotFoundException | CreacionException e) {
            log.error("Error al crear cliente empresa: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al crear cliente empresa", e);
            throw new CreacionException("Error al crear cliente empresa", 1398);
        }
    }

    public ClienteDTO obtenerCliente(String id) {
        log.info("Obteniendo cliente ID: {}", id);
        Clientes cliente = clienteRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado", 3301));
        return clienteMapper.toDto(cliente);
    }

    public ClienteDTO obtenerCliente(String tipo, String numero) {
        log.info("Obteniendo cliente: {} {}", tipo, numero);
        Clientes cliente = clienteRepo.findByTipoIdentificacionAndNumeroIdentificacion(tipo, numero)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado", 3302));
        return clienteMapper.toDto(cliente);
    }

    public List<ClienteDTO> buscarClientes(String nombre) {
        log.info("Buscando clientes: {}", nombre);
        List<Clientes> clientes = clienteRepo.findByNombreLikeOrderByNombreAsc("%" + nombre + "%");

        if (clientes.isEmpty()) {
            throw new NotFoundException("No se encontraron clientes", 3303);
        }

        return clientes.stream()
                .limit(100)
                .map(clienteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ClienteDTO actualizarCliente(String id, ClienteDTO clienteDTO) {
        try {
            log.info("Actualizando cliente ID: {}", id);
            Clientes cliente = clienteRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Cliente no encontrado", 3304));

            cliente.setSegmento(clienteDTO.getSegmento());
            cliente.setCanalAfiliacion(clienteDTO.getCanalAfiliacion());
            cliente.setEstado(clienteDTO.getEstado());
            cliente.setFechaActualizacion(Date.from(Instant.now()));

            cliente = clienteRepo.save(cliente);
            return clienteMapper.toDto(cliente);
        } catch (NotFoundException e) {
            log.error("Error al actualizar cliente: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al actualizar cliente", e);
            throw new ActualizacionException("Error al actualizar cliente", 2399);
        }
    }
}