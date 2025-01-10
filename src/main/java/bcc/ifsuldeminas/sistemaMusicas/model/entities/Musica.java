package bcc.ifsuldeminas.sistemaMusicas.model.entities;

import org.springframework.data.neo4j.core.schema.*;

import java.util.ArrayList;
import java.util.List;


@Node
public class Musica {
    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String titulo;

    @Property
    private String spotifyId;

    @Relationship(type = "PERTENCE_A", direction = Relationship.Direction.OUTGOING)
    private List<Genero> generos = new ArrayList<>();

    @Relationship(type = "CRIADO_POR", direction = Relationship.Direction.OUTGOING)
    private List<Artista> artistas = new ArrayList<>();

    public Musica() {}

    public Musica(String titulo, String spotifyId, List<Genero> generos, List<Artista> artistas) {
        this.titulo = titulo;
        this.spotifyId = spotifyId;
        this.generos = generos;
        this.artistas = artistas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSpotifyId() {
        return spotifyId;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    public List<Artista> getArtistas() {
        return artistas;
    }

    public void setArtistas(List<Artista> artistas) {
        this.artistas = artistas;
    }

}