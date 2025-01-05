package bcc.ifsuldeminas.sistemaMusicas.service;

import bcc.ifsuldeminas.sistemaMusicas.model.entities.Musica;
import bcc.ifsuldeminas.sistemaMusicas.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
/*
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
*/

import bcc.ifsuldeminas.sistemaMusicas.model.entities.Musica;
import bcc.ifsuldeminas.sistemaMusicas.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Service
public class MusicaService {

    private final MusicaRepository musicaRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public MusicaService(MusicaRepository musicaRepository) {
        this.musicaRepository = musicaRepository;
        this.restTemplate = new RestTemplate();
    }

    public void buscarMusicasPorArtista(String artistId) {
        String url = "https://api.deezer.com/artist/" + artistId + "/top";


        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response != null && response.containsKey("data")) {
            List<Map<String, Object>> tracks = (List<Map<String, Object>>) response.get("data");


            for (Map<String, Object> track : tracks) {
                String titulo = (String) track.get("title");
                String artista = (String) ((Map<String, Object>) track.get("artist")).get("name");
                String deezerId = String.valueOf(track.get("id"));

                Musica musica = new Musica(titulo, artista, deezerId);
                musicaRepository.save(musica);
            }
        }
    }
}
