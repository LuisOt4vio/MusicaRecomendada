package bcc.ifsuldeminas.sistemaMusicas.model.entities;
import org.springframework.data.neo4j.core.schema.*;

import java.util.ArrayList;
import java.util.List;


@Node
public class Artista {
    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String nome;

    @Property
    private String spotifyId;

    @Property
    private String link;

    @Relationship(type = "CRIADO_POR", direction = Relationship.Direction.INCOMING)
    private List<Musica> musicas = new ArrayList<>();

    public Artista() {}

    public Artista(String nome, String spotifyId, String link) {
        this.nome = nome;
        this.spotifyId = spotifyId;
        this.link = link;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSpotifyId() {
        return spotifyId;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }

    public void setGeneros(List<Genero> generos) {

    }
}