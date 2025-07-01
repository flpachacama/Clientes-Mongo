package com.banquito.core.clientes.controlador;

import com.banquito.core.clientes.controlador.dto.ContactoTransactionalClienteDTO;
import com.banquito.core.clientes.enums.EstadoRegistro;
import com.banquito.core.clientes.servicio.ContactosTransaccionalesClientesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/clientes/{idCliente}/contactos-transaccionales")
public class ContactosTransaccionalesClientesControlador {

    private final ContactosTransaccionalesClientesService contactoService;

    public ContactosTransaccionalesClientesControlador(ContactosTransaccionalesClientesService contactoService) {
        this.contactoService = contactoService;
    }

    @PostMapping
    public ResponseEntity<ContactoTransactionalClienteDTO> crearContacto(
            @PathVariable String idCliente,
            @RequestBody ContactoTransactionalClienteDTO dto) {
        log.info("Creando contacto transaccional para cliente ID: {}", idCliente);
        ContactoTransactionalClienteDTO contactoCreado = contactoService.crearContacto(idCliente, dto);
        return ResponseEntity.ok(contactoCreado);
    }

    @GetMapping("/{idContacto}")
    public ResponseEntity<ContactoTransactionalClienteDTO> obtenerContacto(
            @PathVariable String idCliente,
            @PathVariable String idContacto) {
        log.info("Obteniendo contacto ID: {} para cliente ID: {}", idContacto, idCliente);
        ContactoTransactionalClienteDTO contacto = contactoService.obtenerContacto(idContacto);
        return ResponseEntity.ok(contacto);
    }

    @GetMapping
    public ResponseEntity<List<ContactoTransactionalClienteDTO>> listarContactosPorCliente(
            @PathVariable String idCliente) {
        log.info("Listando contactos transaccionales para cliente ID: {}", idCliente);
        List<ContactoTransactionalClienteDTO> contactos = contactoService.listarContactosPorCliente(idCliente);
        return ResponseEntity.ok(contactos);
    }

    @PutMapping("/{idContacto}")
    public ResponseEntity<ContactoTransactionalClienteDTO> actualizarContacto(
            @PathVariable String idCliente,
            @PathVariable String idContacto,
            @RequestBody ContactoTransactionalClienteDTO dto) {
        log.info("Actualizando contacto ID: {} para cliente ID: {}", idContacto, idCliente);
        ContactoTransactionalClienteDTO actualizado = contactoService.actualizarContacto(idContacto, dto);
        return ResponseEntity.ok(actualizado);
    }

    @PatchMapping("/{idContacto}/estado")
    public ResponseEntity<Void> cambiarEstadoContacto(
            @PathVariable String idCliente,
            @PathVariable String idContacto,
            @RequestParam EstadoRegistro estado) {
        log.info("Cambiando estado de contacto ID: {} a {} para cliente ID: {}", idContacto, estado, idCliente);
        contactoService.cambiarEstadoContacto(idContacto, estado);
        return ResponseEntity.noContent().build();
    }
}