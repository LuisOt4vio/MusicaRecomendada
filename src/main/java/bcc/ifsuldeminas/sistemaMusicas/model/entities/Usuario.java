package bcc.ifsuldeminas.sistemaMusicas.model.entities;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;
import java.time.LocalDate;
import java.time.Period;

@Node
public class Usuario {

    @Id
    private String id;  // ID do usuário, usando String para permitir UUID ou outro formato
    @Property
    private String nome;  // Nome do usuário
    @Property
    private LocalDate dataNascimento;  // Data de nascimento do usuário
    @Property
    private String genero;  // Gênero do usuário

    // Construtor vazio necessário para o Neo4j
    public Usuario() {}

    // Construtor para facilitar a criação de instâncias
    public Usuario(String id, String nome, LocalDate dataNascimento, String genero) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
    }

    // Getters e setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    // Método para calcular a idade do usuário com base na data de nascimento
    public int getIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
}
