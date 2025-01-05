package bcc.ifsuldeminas.sistemaMusicas.service;

import bcc.ifsuldeminas.sistemaMusicas.model.entities.Genero;
import bcc.ifsuldeminas.sistemaMusicas.model.entities.Musica;
import bcc.ifsuldeminas.sistemaMusicas.repository.GeneroRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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

    public Optional<Genero> buscarGeneroPorId(long id) {
        return generoRepository.findById(id);
    }

    public Iterable<Genero> listarGeneros() {
        return generoRepository.findAll();
    }

    public void deletarGenero(long id) {
        generoRepository.deleteById(id);
    }

    public Genero buscarEAdicionarGeneroPorId(String deezerId) {
        String url = "https://api.deezer.com/genre/" + deezerId;

        RestTemplate restTemplate = new RestTemplate();
        try {
            String response = restTemplate.getForObject(url, String.class);

            org.json.JSONObject jsonResponse = new org.json.JSONObject(response);

            String nome = jsonResponse.getString("name");

            Genero genero = new Genero(nome, deezerId);
            return generoRepository.save(genero);

        } catch (Exception e) {
            throw new RuntimeException("Erro na busca desse genero." + deezerId, e);
        }
    }

    public Genero buscarEAdicionarGeneroPorNome(String nome) {
        String url = "https://api.deezer.com/genre";

        RestTemplate restTemplate = new RestTemplate();
        try {
            String response = restTemplate.getForObject(url, String.class);
            JSONObject jsonResponse = new JSONObject(response);

            JSONArray generos = jsonResponse.getJSONArray("data");

            for (int i = 0; i < generos.length(); i++) {
                JSONObject generoObj = generos.getJSONObject(i);
                String nomeGenero = generoObj.getString("name");

                if (nomeGenero.equalsIgnoreCase(nome)) {
                    String deezerId =  String.valueOf(generoObj.get("id"));

                    Genero genero = new Genero(nomeGenero, deezerId);
                    return generoRepository.save(genero);
                }
            }

            throw new RuntimeException("Gênero não encontrado: " + nome);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar gênero: " + nome, e);
        }
    }
}
