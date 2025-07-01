package com.banquito.core.clientes.controlador;

import com.banquito.core.clientes.servicio.AccionistaRepresentanteService;
import com.banquito.core.clientes.controlador.dto.*;
import com.banquito.core.clientes.enums.EstadoRegistro;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/accionistas-representantes")
public class AccionistaRepresentanteController {

    private final AccionistaRepresentanteService servicio;

    public AccionistaRepresentanteController(AccionistaRepresentanteService servicio) {
        this.servicio = servicio;
    }

    // ========== ENDPOINTS PARA ACCIONISTAS ==========

    @PostMapping("/accionistas")
    @ResponseStatus(HttpStatus.CREATED)
    public AccionistasEmpresasDTO crearAccionista(@RequestBody AccionistasEmpresasDTO accionistaDTO) {
        log.info("Creando nuevo accionista para empresa ID: {}", accionistaDTO.getIdEmpresa());
        return servicio.agregarAccionista(accionistaDTO);
    }

    @PutMapping("/accionistas/{id}")
    public AccionistasEmpresasDTO actualizarAccionista(
            @PathVariable String id, 
            @RequestBody AccionistasEmpresasDTO accionistaDTO) {
        log.info("Actualizando accionista ID: {}", id);
        return servicio.actualizarAccionista(id, accionistaDTO);
    }

    @PatchMapping("/accionistas/{id}/estado")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cambiarEstadoAccionista(
            @PathVariable String id,
            @RequestParam EstadoRegistro estado) {
        log.info("Cambiando estado de accionista ID: {} a {}", id, estado);
        servicio.cambiarEstadoAccionista(id, estado);
    }

    @GetMapping("/empresas/{idEmpresa}/accionistas")
    public List<AccionistasEmpresasDTO> listarAccionistasActivos(
            @PathVariable String idEmpresa) {
        log.info("Listando accionistas activos para empresa ID: {}", idEmpresa);
        return servicio.listarAccionistasActivos(idEmpresa);
    }

    // ========== ENDPOINTS PARA REPRESENTANTES ==========

    @PostMapping("/representantes")
    @ResponseStatus(HttpStatus.CREATED)
    public RepresentanteEmpresaDTO crearRepresentante(@RequestBody RepresentanteEmpresaDTO representanteDTO) {
        log.info("Creando nuevo representante para empresa ID: {}", representanteDTO.getIdEmpresa());
        return servicio.agregarRepresentante(representanteDTO);
    }

    @PutMapping("/representantes/{id}")
    public RepresentanteEmpresaDTO actualizarRepresentante(
            @PathVariable String id,
            @RequestBody RepresentanteEmpresaDTO representanteDTO) {
        log.info("Actualizando representante ID: {}", id);
        return servicio.actualizarRepresentante(id, representanteDTO);
    }

    @PatchMapping("/representantes/{id}/estado")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cambiarEstadoRepresentante(
            @PathVariable String id,
            @RequestParam EstadoRegistro estado) {
        log.info("Cambiando estado de representante ID: {} a {}", id, estado);
        servicio.cambiarEstadoRepresentante(id, estado);
    }

    @GetMapping("/empresas/{idEmpresa}/representantes")
    public List<RepresentanteEmpresaDTO> listarRepresentantesActivos(
            @PathVariable String idEmpresa) {
        log.info("Listando representantes activos para empresa ID: {}", idEmpresa);
        return servicio.listarRepresentantesActivos(idEmpresa);
    }
}