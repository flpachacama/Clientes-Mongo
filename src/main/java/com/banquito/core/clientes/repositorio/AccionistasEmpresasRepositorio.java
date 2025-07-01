package com.banquito.core.clientes.repositorio;

import com.banquito.core.clientes.modelo.AccionistasEmpresas;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccionistasEmpresasRepositorio extends MongoRepository<AccionistasEmpresas, String> {
    Optional<AccionistasEmpresas> findById(String id);
    List<AccionistasEmpresas> findByEmpresaIdAndEstado(String idEmpresa, String estado);
    boolean existsByEmpresaIdAndParticipeId(String idEmpresa, String idParticipe);
    boolean existsById(String id);
}