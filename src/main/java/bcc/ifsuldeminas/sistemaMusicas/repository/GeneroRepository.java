package bcc.ifsuldeminas.sistemaMusicas.repository;

import bcc.ifsuldeminas.sistemaMusicas.model.entities.Genero;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneroRepository extends Neo4jRepository<Genero, String> {
}
