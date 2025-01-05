package bcc.ifsuldeminas.sistemaMusicas.model.entities;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;
import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@Node
public class Usuario {

    @Id
    @GeneratedValue
    private long id;

    @Property
    private String nome;  // Nome do usuário

    @Property
    private LocalDate dataNascimento;  // Data de nascimento do usuário

    @Property
    private String genero;  // Gênero do usuário

    // Construtor vazio necessário para o Neo4j
    public Usuario() {}

    // Construtor para facilitar a criação de instâncias
    public Usuario(String nome, LocalDate dataNascimento, String genero) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
    }

    // Getters e setters
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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
}
