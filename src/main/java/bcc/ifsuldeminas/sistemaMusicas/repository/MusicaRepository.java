package bcc.ifsuldeminas.sistemaMusicas.repository;

import bcc.ifsuldeminas.sistemaMusicas.model.entities.Musica;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicaRepository extends Neo4jRepository<Musica, String> {
}
