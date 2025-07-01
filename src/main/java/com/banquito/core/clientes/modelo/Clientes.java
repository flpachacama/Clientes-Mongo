package com.banquito.core.clientes.modelo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@Document(collection = "clientes")
@CompoundIndexes({
    @CompoundIndex(
        name = "idxu_cliente_tipo_numero_identificacion", 
        def = "{'tipoIdentificacion': 1, 'numeroIdentificacion': 1}", 
        unique = true
    )
})
public class Clientes {
    @Id
    private String id;
    
    // Información básica
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombre;
    private String tipo; 
    private String segmento;
    private String canalAfiliacion;
    private String estado;
    private String version;

    private Date fechaRegistro;
    private Date fechaActualizacion;
    
    // Información de contacto
    private String correoElectronico;
    private List<TelefonosClientes> telefonos;
    private List<DireccionesClientes> direcciones;
    private ContactosTransaccionalesClientes contactoTransaccional;

}