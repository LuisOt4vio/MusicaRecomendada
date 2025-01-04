package bcc.ifsuldeminas.sistemaMusicas.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import bcc.ifsuldeminas.sistemaMusicas.model.entities.Usuario;

public interface UsuarioRepository extends Neo4jRepository<Usuario, String> {
    // Podemos adicionar métodos personalizados se necessário
}
