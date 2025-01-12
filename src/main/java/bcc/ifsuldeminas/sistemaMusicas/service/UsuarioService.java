package bcc.ifsuldeminas.sistemaMusicas.service;
import org.neo4j.driver.*;
import bcc.ifsuldeminas.sistemaMusicas.model.entities.Musica;
import bcc.ifsuldeminas.sistemaMusicas.model.entities.Playlist;
import bcc.ifsuldeminas.sistemaMusicas.model.entities.Usuario;
import bcc.ifsuldeminas.sistemaMusicas.repository.MusicaRepository;
import bcc.ifsuldeminas.sistemaMusicas.repository.UsuarioRepository;
import org.neo4j.driver.types.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    @Autowired
    private MusicaRepository musicaRepository;
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, Driver driver) {
        this.usuarioRepository = usuarioRepository;
        this.driver = driver;
    }

    // Listar todos os usuários
    public Iterable<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Buscar um usuário pelo ID
    public Optional<Usuario> buscarUsuarioPorId(long id) {
        return usuarioRepository.findById(id);
    }

    // Salvar ou atualizar um usuário
    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Deletar um usuário pelo ID
    public void deletarUsuario(long  id) {
        usuarioRepository.deleteById(id);
    }

    // Calcular a idade do usuário
    public int calcularIdade(Usuario usuario) {
        return usuario.getIdade();
    }

    public Usuario cadastrarUsuario(String nome, String senha, String genero, LocalDate dataNascimento) throws IllegalArgumentException {

        Optional<Usuario> usuarioExistente = Optional.ofNullable(usuarioRepository.findByNome(nome));
        if (usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário com este nome.");
        }


        Usuario usuario = new Usuario(nome, dataNascimento, genero, senha);
        return usuarioRepository.save(usuario);
    }
    public boolean login(String nome, String senha) {
        Usuario usuario = usuarioRepository.findByNomeAndSenha(nome, senha);
        if (usuario == null) {
            throw new IllegalArgumentException("Nome ou senha incorretos.");
        }
        return true;
    }
    public void conectarPlaylistAoUsuario(Long usuarioId, Playlist playlist) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));


        usuario.adicionarPlaylist(playlist);


        usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public void salvar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
    public Usuario adicionarMusicaAoUsuario(Long usuarioId, Long musicaId) {

        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(
                () -> new IllegalArgumentException("Usuário não encontrado.")
        );

        Musica musica = musicaRepository.findById(musicaId).orElseThrow(
                () -> new IllegalArgumentException("Música não encontrada.")
        );

        usuario.adicionarMusica(musica);


        return usuarioRepository.save(usuario);
    }
    public void removerMusicaDoUsuario(Long usuarioId, Long musicaId) {
        // Verificar se o usuário existe
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(
                () -> new IllegalArgumentException("Usuário não encontrado.")
        );

        // Verificar se a música existe
        Musica musica = musicaRepository.findById(musicaId).orElseThrow(
                () -> new IllegalArgumentException("Música não encontrada.")
        );

        // Remover o relacionamento no banco
        usuarioRepository.removerRelacionamentoMusica(usuarioId, musicaId);

        // Atualizar a entidade em memória
        usuario.removerMusica(musica);
    }
    /*
    public List<Musica> recomendarMusicasPorArtista(Long usuarioId) {
        return usuarioRepository.recomendarMusicasPorArtista(usuarioId);
    }*/
    private final Driver driver;


    /*
    public List<Musica> recomendarMusicasPorArtista(Long usuarioId) {
        List<Musica> recomendacoes = new ArrayList<>();

        try (Session session = driver.session()) {
            String query = """
                MATCH (u:Usuario)-[:ADICIONOU]->(m:Musica)-[:CRIADO_POR]->(a:Artista)<-[:CRIADO_POR]-(m2:Musica)
                WHERE id(u) = $usuarioId AND NOT (u)-[:ADICIONOU]->(m2)
                RETURN DISTINCT m2
                LIMIT 10
                """;

            Result result = session.run(query, Values.parameters("usuarioId", usuarioId));

            result.forEachRemaining(record -> {
                // Aqui convertemos os nós `m2` retornados em objetos `Musica`.
                Node node = record.get("m2").asNode();
                Musica musica = new Musica();
                musica.setId(node.id());
                musica.setTitulo(node.get("titulo").asString(null));
                musica.setSpotifyId(node.get("spotifyId").asString(null));
                musica.setPreview(node.get("preview").asString(null));
                musica.setLink(node.get("link").asString(null));
                recomendacoes.add(musica);
            });
        }

        return recomendacoes;
    }*/

    public List<Musica> recomendarMusicasPorArtista(Long usuarioId) {
        List<Musica> recomendacoes = new ArrayList<>();

        try (Session session = driver.session()) {
            String query = """
            MATCH (u:Usuario)-[:ADICIONOU]->(m:Musica)-[:CRIADO_POR]->(a:Artista)<-[:CRIADO_POR]-(m2:Musica)
            WHERE id(u) = $usuarioId AND NOT (u)-[:ADICIONOU]->(m2)
            RETURN DISTINCT m2
            ORDER BY rand()  // Ordena os resultados de forma aleatória
            LIMIT 3
            """;

            Result result = session.run(query, Values.parameters("usuarioId", usuarioId));

            result.forEachRemaining(record -> {

                Node node = record.get("m2").asNode();
                Musica musica = new Musica();
                musica.setId(node.id());
                musica.setTitulo(node.get("titulo").asString(null));
                musica.setSpotifyId(node.get("spotifyId").asString(null));
                musica.setPreview(node.get("preview").asString(null));
                musica.setLink(node.get("link").asString(null));
                recomendacoes.add(musica);
            });
        }

        return recomendacoes;
    }
    public List<Musica> recomendarMusicasPorUsuarios(Long usuarioId) {
        List<Musica> recomendacoes = new ArrayList<>();

        try (Session session = driver.session()) {
            String query = """
            MATCH (u:Usuario)-[:ADICIONOU]->(m:Musica)<-[:ADICIONOU]-(outro:Usuario)-[:ADICIONOU]->(m2:Musica)
            WHERE id(u) = $usuarioId AND NOT (u)-[:ADICIONOU]->(m2)
            RETURN DISTINCT m2
            ORDER BY rand()
            LIMIT 10
            """;

            Result result = session.run(query, Values.parameters("usuarioId", usuarioId));

            result.forEachRemaining(record -> {
               
                Node node = record.get("m2").asNode();
                Musica musica = new Musica();
                musica.setId(node.id());
                musica.setTitulo(node.get("titulo").asString(null));
                musica.setSpotifyId(node.get("spotifyId").asString(null));
                musica.setPreview(node.get("preview").asString(null));
                musica.setLink(node.get("link").asString(null));
                recomendacoes.add(musica);
            });
        }

        return recomendacoes;
    }

}
