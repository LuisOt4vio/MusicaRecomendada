package bcc.ifsuldeminas.sistemaMusicas.service;

import bcc.ifsuldeminas.sistemaMusicas.model.entities.Musica;
import bcc.ifsuldeminas.sistemaMusicas.repository.MusicaRepository;
//import net.minidev.json.JSONObject;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
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


    private static final String DEEZER_API_URL = "https://api.deezer.com/search?q=";

    public String buscarMusicaPorNome(String nome) {
        String url = DEEZER_API_URL + nome.replace(" ", "%20");
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

/*
    public Musica pesquisarPorTituloEAdicionarAoBanco(String titulo) {
        // URL da API do Deezer para buscar músicas
        String url = "https://api.deezer.com/search?q=" + titulo;

        // Chamada para a API do Deezer
        RestTemplate restTemplate = new RestTemplate();
        DeezerResponse response = restTemplate.getForObject(url, DeezerResponse.class);

        if (response != null && response.getData() != null && !response.getData().isEmpty()) {
            DeezerTrack track = response.getData().get(0); // Pega a primeira música encontrada

            // Cria uma nova entidade de música para salvar no banco
            Musica novaMusica = new Musica(track.getTitle(), track.getArtist().getName());
            return musicaRepository.save(novaMusica);
        } else {
            throw new RuntimeException("Nenhuma música encontrada com o título: " + titulo);
        }
    }*/

    public Musica buscarMusicaPorTitulo(String titulo) throws JSONException {
        String url = "https://api.deezer.com/search?q=" + titulo.replace(" ", "%20");

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        JSONObject jsonResponse = new JSONObject(response);
        if (jsonResponse.has("data") && jsonResponse.getJSONArray("data").length() > 0) {
            JSONObject firstResult = jsonResponse.getJSONArray("data").getJSONObject(0);

            String tituloMusica = firstResult.getString("title");
            String artista = firstResult.getJSONObject("artist").getString("name");
            String deezerId = String.valueOf(firstResult.get("id"));

            return new Musica(tituloMusica, artista, deezerId);
        }


        throw new RuntimeException("Nenhuma música encontrada com o título: " + titulo);
    }
    public Musica buscarEMusicaCadastrar(String titulo) throws JSONException {
        String url = "https://api.deezer.com/search?q=" + titulo.replace(" ", "%20");
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        JSONObject jsonResponse = new JSONObject(response);
        if (jsonResponse.has("data") && jsonResponse.getJSONArray("data").length() > 0) {
            JSONObject firstResult = jsonResponse.getJSONArray("data").getJSONObject(0);

            String tituloMusica = firstResult.getString("title");
            String artista = firstResult.getJSONObject("artist").getString("name");
            String deezerId = String.valueOf(firstResult.get("id"));

            Musica musica = new Musica(tituloMusica, artista, deezerId);

            return musicaRepository.save(musica);
        }

        throw new RuntimeException("Nenhuma música encontrada com o título: " + titulo);
    }
}
