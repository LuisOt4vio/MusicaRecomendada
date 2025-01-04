package bcc.ifsuldeminas.sistemaMusicas.service;

import bcc.ifsuldeminas.sistemaMusicas.model.entities.Genero;
import bcc.ifsuldeminas.sistemaMusicas.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GeneroService {

    private final GeneroRepository generoRepository;

    @Autowired
    public GeneroService(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    public Genero salvarGenero(Genero genero) {
        return generoRepository.save(genero);
    }

    public Optional<Genero> buscarGeneroPorId(String id) {
        return generoRepository.findById(id);
    }

    public Iterable<Genero> listarGeneros() {
        return generoRepository.findAll();
    }

    public void deletarGenero(String id) {
        generoRepository.deleteById(id);
    }
}
