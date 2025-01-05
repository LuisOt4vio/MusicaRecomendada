package bcc.ifsuldeminas.sistemaMusicas.model.entities;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;

@Node
public class Musica {
    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String titulo;

    @Property
    private String artista;

    @Property
    private String deezerId;

    public Musica(){}

    public Musica(String titulo, String artista, String deezerId) {
        this.titulo = titulo;
        this.artista = artista;
        this.deezerId = deezerId;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getArtista() {
        return artista;
    }
    public void setArtista(String artista) {
        this.artista = artista;
    }
    public String getDeezerId() {
        return deezerId;
    }
    public void setDeezerId(String deezerId) {
        this.deezerId = deezerId;
    }

}
