package bcc.ifsuldeminas.sistemaMusicas.service;

import bcc.ifsuldeminas.sistemaMusicas.model.entities.Artista;
import bcc.ifsuldeminas.sistemaMusicas.model.entities.Genero;
import bcc.ifsuldeminas.sistemaMusicas.model.entities.Musica;
import bcc.ifsuldeminas.sistemaMusicas.repository.ArtistaRepository;
import bcc.ifsuldeminas.sistemaMusicas.repository.GeneroRepository;
import bcc.ifsuldeminas.sistemaMusicas.repository.MusicaRepository;
//import net.minidev.json.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.AbstractByteBuf;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

/*
@Service
public class MusicaService {
/*
    @Autowired
    private MusicaRepository musicaRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private ArtistaRepository artistaRepository;*/

/*
    private final MusicaRepository musicaRepository;
    private final GeneroRepository generoRepository;
    private final ArtistaRepository artistaRepository;
    private final RestTemplate restTemplate;

    public MusicaService(MusicaRepository musicaRepository, GeneroRepository generoRepository, ArtistaRepository artistaRepository) {
        this.musicaRepository = musicaRepository;
        this.generoRepository = generoRepository;
        this.artistaRepository = artistaRepository;
        this.restTemplate = new RestTemplate();
    }

    public void salvarArtistaEGeneros(String artistSpotifyId) {
        // 1. Buscar informações do artista na API do Spotify
        String artistApiUrl = "https://api.spotify.com/v1/artists/" + artistSpotifyId;
        SpotifyArtistResponse artistResponse = restTemplate.getForObject(artistApiUrl, SpotifyArtistResponse.class);

        if (artistResponse != null) {
            // 2. Salvar o artista
            Artista artista = new Artista(artistResponse.getName(), artistResponse.getId(), artistResponse.getExternalUrls().get("spotify"));
            artistaRepository.save(artista);

            // 3. Salvar os gêneros associados ao artista
            List<Genero> generos = new ArrayList<>();
            for (String generoNome : artistResponse.getGenres()) {
                Genero genero = generoRepository.findByNome(generoNome)
                        .orElse(new Genero(generoNome, null));
                generoRepository.save(genero);
                generos.add(genero);
            }
            artista.setGeneros(generos);
            artistaRepository.save(artista);
        }
    }

    public void salvarMusicasPorArtista(String artistSpotifyId) {
        // 1. Buscar músicas do artista na API do Spotify
        String tracksApiUrl = "https://api.spotify.com/v1/artists/" + artistSpotifyId + "/top-tracks?market=US";
        SpotifyTracksResponse tracksResponse = restTemplate.getForObject(tracksApiUrl, SpotifyTracksResponse.class);

        if (tracksResponse != null && tracksResponse.getTracks() != null) {
            for (SpotifyTrack track : tracksResponse.getTracks()) {
                // 2. Criar a música e associar ao artista
                Musica musica = new Musica(track.getName(), track.getId(), null, new ArrayList<>());
                Artista artista = (Artista) artistaRepository.findBySpotifyId(artistSpotifyId).orElseThrow();
                musica.getArtistas().add(artista);

                // 3. Salvar a música no banco de dados
                musicaRepository.save(musica);
            }
        }
    }
*/
   /* @Autowired
    private SpotifyAuthService spotifyAuthService;

    public MusicaService(@Lazy SpotifyAuthService spotifyAuthService) {
        this.spotifyAuthService = spotifyAuthService;
    }*//*
    public Musica salvarMusica(Musica musica) {
        // Salvar os artistas e gêneros associados
        for (Artista artista : musica.getArtistas()) {
            artistaRepository.save(artista);
        }
        for (Genero genero : musica.getGeneros()) {
            generoRepository.save(genero);
        }
        // Salvar a música
        return musicaRepository.save(musica);
    }

    public List<Musica> listarMusicas() {
        return (List<Musica>) musicaRepository.findAll();
    }

    public void salvarMusicas(String artistId) {
    }*/
    /*private static final String SPOTIFY_SEARCH_URL = "https://api.spotify.com/v1/search?q=genre:%s&type=track&limit=10";


    public void buscarESalvarPorGenero(String generoNome) {
        try {
            // Obter o token de autenticação do Spotify
            String token = spotifyAuthService.getSpotifyToken();

            // Configurar a URL com o gênero
            String url = String.format(SPOTIFY_SEARCH_URL, generoNome);

            // Fazer a requisição
            RestTemplate restTemplate = new RestTemplate();
            var headers = new org.springframework.http.HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            headers.set("Content-Type", "application/json");

            var requestEntity = new org.springframework.http.HttpEntity<>(headers);
            var response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, requestEntity, String.class);

            // Processar a resposta
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            JsonNode tracks = rootNode.path("tracks").path("items");
            if (tracks.isArray()) {
                Genero genero = new Genero(generoNome, ""); // Salva o gênero
                genero = generoRepository.save(genero);

                for (JsonNode trackNode : tracks) {
                    // Obter informações da música
                    String titulo = trackNode.path("name").asText();
                    String deezerId = trackNode.path("id").asText();

                    // Obter informações dos artistas
                    List<Artista> artistas = new ArrayList<>();
                    for (JsonNode artistaNode : trackNode.path("artists")) {
                        String nomeArtista = artistaNode.path("name").asText();
                        String idArtista = artistaNode.path("id").asText();
                        String link = artistaNode.path("href").asText();

                        Artista artista = new Artista(nomeArtista, idArtista, link);
                        artista = artistaRepository.save(artista);
                        artistas.add(artista);
                    }

                    // Criar a música e salvar
                    Musica musica = new Musica(titulo, deezerId, List.of(genero), artistas);
                    musicaRepository.save(musica);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
/*

// Exemplo de classes de resposta da API
class SpotifyArtistResponse {
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Map<String, String> getExternalUrls() {
        return externalUrls;
    }

    public void setExternalUrls(Map<String, String> externalUrls) {
        this.externalUrls = externalUrls;
    }

    private String name;
    private List<String> genres;
    private Map<String, String> externalUrls;


    // Getters e setters
}

class SpotifyTracksResponse {
    public List<SpotifyTrack> getTracks() {
        return tracks;
    }

    public void setTracks(List<SpotifyTrack> tracks) {
        this.tracks = tracks;
    }

    private List<SpotifyTrack> tracks;

    // Getters e setters
}

class SpotifyTrack {
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String name;

    // Getters e setters
}*/
@Service
public class MusicaService {

    @Autowired
    private ArtistaRepository artistaRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private MusicaRepository musicaRepository;

    private final String BASE_URL = "https://api.deezer.com";

    public Object salvarArtistaEGeneros(String artistId) {
        RestTemplate restTemplate = new RestTemplate();

        String artistaUrl = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/artist/" + artistId).toUriString();

        var response = restTemplate.getForObject(artistaUrl, DeezerArtistResponse.class);

        if (response == null) {
            throw new RuntimeException("Artista não encontrado.");
        }

        Artista artista = new Artista(response.getName(), response.getId(), response.getLink());

        List<Genero> generos = new ArrayList<>();
        if (response.getGenres() != null && response.getGenres().getData() != null) {
            for (DeezerGenre genre : response.getGenres().getData()) {
                Genero genero = generoRepository.findBySpotifyId(genre.getId());
                if (genero == null) {
                    genero = new Genero(genre.getName(), genre.getId());
                    generoRepository.save(genero);
                }
                generos.add(genero);  // Adiciona cada gênero à lista
            }
        }

        // Aqui, estamos assegurando que todos os gêneros sejam associados ao artista
        artista.setGeneros(generos);
        artistaRepository.save(artista);

        return artista;
    }


    public void salvarMusicasDoArtista(String artistId) {
        RestTemplate restTemplate = new RestTemplate();

        String musicasUrl = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/artist/" + artistId + "/top").toUriString();

        var response = restTemplate.getForObject(musicasUrl, DeezerTracksResponse.class);

        if (response == null || response.getData() == null) {
            throw new RuntimeException("Nenhuma música encontrada para o artista.");
        }

        Optional<Artista> artista = artistaRepository.findBySpotifyId(artistId);

        if (artista.isEmpty()) {
            throw new RuntimeException("Artista não encontrado no banco de dados.");
        }

        for (DeezerTrack track : response.getData()) {
            Musica musica = new Musica(track.getTitle(), track.getId(), new ArrayList<>(), new ArrayList<>());
            musica.getArtistas().add(artista.get());
            musicaRepository.save(musica);
        }
    }

    public void salvarDadosAPartirDeAlbuns(int albumInicio, int albumFim) {
        RestTemplate restTemplate = new RestTemplate();

        for (int albumId = albumInicio; albumId <= albumFim; albumId++) {
            String url = "https://api.deezer.com/album/" + albumId;

            try {
                ResponseEntity<DeezerAlbumResponse> response = restTemplate.getForEntity(url, DeezerAlbumResponse.class);

                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    DeezerAlbumResponse albumData = response.getBody();

                    DeezerAlbumResponse.Artist artistData = albumData.getArtist();
                    Artista artista = salvarArtistaSeNaoExistir(artistData);

                    artista.adicionarAlbumId(String.valueOf(albumId));
                    artistaRepository.save(artista);

                    List<Genero> generos = new ArrayList<>();
                    for (DeezerAlbumResponse.GenreData genreData : albumData.getGenres().getData()) {
                        Genero genero = salvarGeneroSeNaoExistir(genreData);
                        generos.add(genero);
                        artista.adicionarGenero(genero);
                    }
                    artistaRepository.save(artista);

                    for (DeezerAlbumResponse.Track trackData : albumData.getTracks().getData()) {
                        salvarMusicaSeNaoExistir(trackData, artista, generos);
                    }
                }
            } catch (Exception e) {
                System.out.println("Erro ao processar o album de ID " + albumId + ": " + e.getMessage());
            }
        }
    }

    private Artista salvarArtistaSeNaoExistir(DeezerAlbumResponse.Artist artistData) {
        return artistaRepository.findBySpotifyId(artistData.getId().toString())
                .orElseGet(() -> {
                    Artista artista = new Artista();
                    artista.setNome(artistData.getName());
                    artista.setSpotifyId(artistData.getId().toString());
                    artista.setLink(artistData.getLink());
                    return artistaRepository.save(artista);
                });
    }

    private Genero salvarGeneroSeNaoExistir(DeezerAlbumResponse.GenreData genreData) {
        return generoRepository.findByNome(genreData.getName())
                .orElseGet(() -> {
                    Genero genero = new Genero();
                    genero.setNome(genreData.getName());
                    genero.setSpotifyId(genreData.getId().toString());
                    return generoRepository.save(genero);
                });
    }

    private void salvarMusicaSeNaoExistir(DeezerAlbumResponse.Track trackData, Artista artista, List<Genero> generos) {
        if (!musicaRepository.existsBySpotifyId(trackData.getId().toString())) {
            Musica musica = new Musica();
            musica.setTitulo(trackData.getTitle());
            musica.setSpotifyId(trackData.getId().toString());
            musica.getArtistas().add(artista);
            musica.getGeneros().addAll(generos);
            musicaRepository.save(musica);
        }
    }
}





