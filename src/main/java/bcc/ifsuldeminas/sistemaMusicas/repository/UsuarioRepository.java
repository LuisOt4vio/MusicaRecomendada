package bcc.ifsuldeminas.sistemaMusicas.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import bcc.ifsuldeminas.sistemaMusicas.model.entities.Usuario;

import java.util.Optional;
import java.util.UUID;


public interface UsuarioRepository extends Neo4jRepository<Usuario, Long> {
    Optional<Usuario> findById(long id);

    void deleteById(long id);
}