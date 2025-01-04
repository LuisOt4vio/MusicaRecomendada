package bcc.ifsuldeminas.sistemaMusicas.repository;

import bcc.ifsuldeminas.sistemaMusicas.model.entities.Playlist;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends Neo4jRepository<Playlist, String> {
    // Podemos adicionar métodos customizados aqui, caso necessário
}
