package bcc.ifsuldeminas.sistemaMusicas.repository;
import bcc.ifsuldeminas.sistemaMusicas.model.entities.Artista;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistaRepository extends Neo4jRepository<Artista, Long> {
    Optional<Object> findBySpotifyId(String artistSpotifyId);
    @Query("""
    MATCH (a:Artista)-[:CRIADO_POR]->(m:Musica)-[:PERTENCE_A]->(g:Genero)
    WHERE a.spotifyId = $spotifyId
    RETURN a, collect(m) AS musicas, collect(g) AS generos
""")
    Artista findArtistaComMusicasEGeneros(String spotifyId);
}