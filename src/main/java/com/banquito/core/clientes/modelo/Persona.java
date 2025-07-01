package com.banquito.core.clientes.modelo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@Document(collection = "personas")
public class Persona {
    @Id
    private String id;
    
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombre;
    private String genero;
    private Date fechaNacimiento;
    private String estadoCivil;
    private String nivelEstudio;
    private String correoElectronico;
    
    // Relación con clientes 
    private List<String> clienteIds;
    
    // Auditoría
    private Date fechaRegistro;
    private Date fechaActualizacion;
    private String estado;
    private String version;
    
}