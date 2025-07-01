package com.banquito.core.clientes.modelo;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Entity
@Table(name = "clientes_sucursales", schema = "public")
public class ClientesSucursales {
    @Id
    private Integer id;
    private Clientes idCliente;
    private Integer codigoSucursal;
    private String estado;

    private Long version;

    public ClientesSucursales() {
    }

    public ClientesSucursales(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Clientes getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Clientes idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getCodigoSucursal() {
        return codigoSucursal;
    }

    public void setCodigoSucursal(Integer codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((idCliente == null) ? 0 : idCliente.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ClientesSucursales other = (ClientesSucursales) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (idCliente == null) {
            if (other.idCliente != null)
                return false;
        } else if (!idCliente.equals(other.idCliente))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ClientesSucursales [id=" + id + ", idCliente=" + idCliente + ", codigoSucursal=" + codigoSucursal
                + ", estado=" + estado + ", version=" + version + "]";
    }



}