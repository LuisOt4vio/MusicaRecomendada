package bcc.ifsuldeminas.sistemaMusicas.service;

import bcc.ifsuldeminas.sistemaMusicas.model.entities.Musica;
import bcc.ifsuldeminas.sistemaMusicas.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MusicaService {

    private final MusicaRepository musicaRepository;

    @Autowired
    public MusicaService(MusicaRepository musicaRepository) {
        this.musicaRepository = musicaRepository;
    }

    public Musica salvarMusica(Musica musica) {
        return musicaRepository.save(musica);
    }

    public Optional<Musica> buscarMusicaPorId(String id) {
        return musicaRepository.findById(id);
    }

    public Iterable<Musica> listarMusicas() {
        return musicaRepository.findAll();
    }

    public void deletarMusica(String id) {
        musicaRepository.deleteById(id);
    }
}
