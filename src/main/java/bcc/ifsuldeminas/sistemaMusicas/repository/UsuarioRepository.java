package bcc.ifsuldeminas.sistemaMusicas.repository;

import bcc.ifsuldeminas.sistemaMusicas.model.entities.Usuario;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends Neo4jRepository<Usuario, String> {
}
