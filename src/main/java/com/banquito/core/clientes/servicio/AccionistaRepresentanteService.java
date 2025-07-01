package com.banquito.core.clientes.servicio;

import com.banquito.core.clientes.controlador.dto.*;
import com.banquito.core.clientes.controlador.mapper.*;
import com.banquito.core.clientes.enums.EstadoRegistro;
import com.banquito.core.clientes.enums.TipoEntidadParticipe;
import com.banquito.core.clientes.excepcion.*;
import com.banquito.core.clientes.modelo.*;
import com.banquito.core.clientes.repositorio.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccionistaRepresentanteService {

    private final AccionistasEmpresasRepositorio accionistaRepo;
    private final RepresentantesEmpresasRepositorio representanteRepo;
    private final EmpresasRepositorio empresaRepo;
    private final ClientesRepositorio clienteRepo;
    private final AccionistasEmpresasMapper accionistaMapper;
    private final RepresentanteEmpresaMapper representanteMapper;

    public AccionistaRepresentanteService(AccionistasEmpresasRepositorio accionistaRepo,
                                        RepresentantesEmpresasRepositorio representanteRepo,
                                        EmpresasRepositorio empresaRepo,
                                        ClientesRepositorio clienteRepo,
                                        AccionistasEmpresasMapper accionistaMapper,
                                        RepresentanteEmpresaMapper representanteMapper) {
        this.accionistaRepo = accionistaRepo;
        this.representanteRepo = representanteRepo;
        this.empresaRepo = empresaRepo;
        this.clienteRepo = clienteRepo;
        this.accionistaMapper = accionistaMapper;
        this.representanteMapper = representanteMapper;
    }

    // ========== MÉTODOS PARA ACCIONISTAS ==========

    @Transactional
    public AccionistasEmpresasDTO agregarAccionista(AccionistasEmpresasDTO accionistaDTO) {
        try {
            log.info("Agregando accionista a empresa: {}", accionistaDTO.getIdEmpresa());
            
            if (!empresaRepo.existsById(accionistaDTO.getIdEmpresa())) {
                throw new NotFoundException("Empresa no encontrada", 3206);
            }
            
            if (TipoEntidadParticipe.PERSONA.name().equals(accionistaDTO.getTipoEntidadParticipe())) {
                if (!clienteRepo.existsByIdAndTipoEntidad(accionistaDTO.getIdParticipe(), "PERSONA")) {
                    throw new CreacionException("Persona debe ser cliente", 1401);
                }
            } else if (TipoEntidadParticipe.EMPRESA.name().equals(accionistaDTO.getTipoEntidadParticipe())) {
                if (!clienteRepo.existsByIdAndTipoEntidad(accionistaDTO.getIdParticipe(), "EMPRESA")) {
                    throw new CreacionException("Empresa debe ser cliente", 1402);
                }
            }
            
            if (accionistaRepo.existsByEmpresaIdAndParticipeId(accionistaDTO.getIdEmpresa(), accionistaDTO.getIdParticipe())) {
                throw new CreacionException("Accionista ya existe", 1404);
            }
            
            AccionistasEmpresas accionista = accionistaMapper.toEntity(accionistaDTO);
            accionista.setEstado(EstadoRegistro.ACTIVO.name());
            
            return accionistaMapper.toDto(accionistaRepo.save(accionista));
            
        } catch (CreacionException | NotFoundException e) {
            log.error("Error al agregar accionista: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al agregar accionista", e);
            throw new CreacionException("Error al agregar accionista", 1499);
        }
    }

    @Transactional
    public AccionistasEmpresasDTO actualizarAccionista(String id, AccionistasEmpresasDTO accionistaDTO) {
        try {
            log.info("Actualizando accionista ID: {}", id);
            
            AccionistasEmpresas accionista = accionistaRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Accionista no encontrado", 3401));
            
            accionista.setParticipacion(accionistaDTO.getParticipacion());
            
            return accionistaMapper.toDto(accionistaRepo.save(accionista));
            
        } catch (NotFoundException e) {
            log.error("Error al actualizar accionista: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al actualizar accionista", e);
            throw new ActualizacionException("Error al actualizar accionista", 2499);
        }
    }

    @Transactional
    public void cambiarEstadoAccionista(String id, EstadoRegistro estado) {
        try {
            log.info("Cambiando estado del accionista ID: {} a {}", id, estado);
            
            AccionistasEmpresas accionista = accionistaRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Accionista no encontrado", 3402));
            
            accionista.setEstado(estado.name());
            accionistaRepo.save(accionista);
            
        } catch (NotFoundException e) {
            log.error("Error al cambiar estado accionista: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al cambiar estado accionista", e);
            throw new ActualizacionException("Error al cambiar estado accionista", 2498);
        }
    }

    public List<AccionistasEmpresasDTO> listarAccionistasActivos(String idEmpresa) {
        try {
            log.info("Listando accionistas activos de empresa: {}", idEmpresa);
            
            if (!empresaRepo.existsById(idEmpresa)) {
                throw new NotFoundException("Empresa no encontrada", 3208);
            }
            
            return accionistaRepo.findByEmpresaIdAndEstado(idEmpresa, EstadoRegistro.ACTIVO.name())
                    .stream()
                    .map(accionistaMapper::toDto)
                    .collect(Collectors.toList());
            
        } catch (NotFoundException e) {
            log.error("Error al listar accionistas: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al listar accionistas", e);
            throw new ConsultaException("Error al listar accionistas", 3497);
        }
    }

    // ========== MÉTODOS PARA REPRESENTANTES ==========

    @Transactional
    public RepresentanteEmpresaDTO agregarRepresentante(RepresentanteEmpresaDTO representanteDTO) {
        try {
            log.info("Agregando representante a empresa: {}", representanteDTO.getIdEmpresa());
            
            if (!empresaRepo.existsById(representanteDTO.getIdEmpresa())) {
                throw new NotFoundException("Empresa no encontrada", 3209);
            }
            
            Clientes cliente = clienteRepo.findById(representanteDTO.getIdCliente())
                    .orElseThrow(() -> new NotFoundException("Cliente no encontrado", 3305));

            if (!"PERSONA".equals(cliente.getTipo())) {
                throw new CreacionException("Representante debe ser persona", 1501);
            }
            
            if (representanteRepo.existsByEmpresaIdAndClienteId(representanteDTO.getIdEmpresa(), representanteDTO.getIdCliente())) {
                throw new CreacionException("Representante ya existe", 1502);
            }
            
            RepresentantesEmpresas representante = representanteMapper.toEntity(representanteDTO);
            representante.setEstado(EstadoRegistro.ACTIVO.name());
            
            return representanteMapper.toDto(representanteRepo.save(representante));
            
        } catch (CreacionException | NotFoundException e) {
            log.error("Error al agregar representante: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al agregar representante", e);
            throw new CreacionException("Error al agregar representante", 1599);
        }
    }

    @Transactional
    public RepresentanteEmpresaDTO actualizarRepresentante(String id, RepresentanteEmpresaDTO representanteDTO) {
        try {
            log.info("Actualizando representante ID: {}", id);
            
            RepresentantesEmpresas representante = representanteRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Representante no encontrado", 3501));
            
            representante.setRol(representanteDTO.getRol());
            
            return representanteMapper.toDto(representanteRepo.save(representante));
            
        } catch (NotFoundException e) {
            log.error("Error al actualizar representante: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al actualizar representante", e);
            throw new ActualizacionException("Error al actualizar representante", 2599);
        }
    }

    @Transactional
    public void cambiarEstadoRepresentante(String id, EstadoRegistro estado) {
        try {
            log.info("Cambiando estado del representante ID: {} a {}", id, estado);
            
            RepresentantesEmpresas representante = representanteRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Representante no encontrado", 3502));
            
            representante.setEstado(estado.name());
            representanteRepo.save(representante);
            
        } catch (NotFoundException e) {
            log.error("Error al cambiar estado representante: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al cambiar estado representante", e);
            throw new ActualizacionException("Error al cambiar estado representante", 2598);
        }
    }

    public List<RepresentanteEmpresaDTO> listarRepresentantesActivos(String idEmpresa) {
        try {
            log.info("Listando representantes activos de empresa: {}", idEmpresa);
            
            if (!empresaRepo.existsById(idEmpresa)) {
                throw new NotFoundException("Empresa no encontrada", 3210);
            }
            
            return representanteRepo.findByEmpresaIdAndEstado(idEmpresa, EstadoRegistro.ACTIVO.name())
                    .stream()
                    .map(representanteMapper::toDto)
                    .collect(Collectors.toList());
            
        } catch (NotFoundException e) {
            log.error("Error al listar representantes: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al listar representantes", e);
            throw new ConsultaException("Error al listar representantes", 3597);
        }
    }
}