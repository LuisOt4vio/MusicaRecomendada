package bcc.ifsuldeminas.sistemaMusicas.model.entities;

import org.springframework.data.neo4j.core.schema.*;

import java.util.ArrayList;
import java.util.List;

@Node
public class Playlist {

    @Id
    @GeneratedValue
    private long id;

    @Property
    private String nome;

    @Property
    private String descricao;

    @Relationship(type = "Possui")
    private List<Musica> musicas = new ArrayList<>();

    public Playlist() {}

    public Playlist(String nome, String descricao) {
        //this.id = id;
        this.nome = nome;
        this.descricao=descricao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }
}
