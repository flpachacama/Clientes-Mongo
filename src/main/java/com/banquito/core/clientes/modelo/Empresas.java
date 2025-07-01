package com.banquito.core.clientes.modelo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@Document(collection = "empresas")
public class Empresas {
    @Id
    private String id;
    
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombreComercial;
    private String razonSocial;
    private String tipo;
    private Date fechaConstitucion;
    private String correoElectronico;
    private String sectorEconomico;
    
    // Relación con clientes 
    private String clienteId;
    
    // Auditoría
    private Date fechaRegistro;
    private Date fechaActualizacion;
    private String estado;
    private String version;
    
    // Accionistas 
    private List<AccionistasEmpresas> accionistas;
    
    // Representantes 
    private List<RepresentantesEmpresas> representantes;
    
}