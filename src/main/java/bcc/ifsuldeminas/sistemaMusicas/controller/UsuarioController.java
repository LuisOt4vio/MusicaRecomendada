package bcc.ifsuldeminas.sistemaMusicas.controller;

import bcc.ifsuldeminas.sistemaMusicas.model.entities.Musica;
import bcc.ifsuldeminas.sistemaMusicas.model.entities.Playlist;
import bcc.ifsuldeminas.sistemaMusicas.model.entities.Usuario;
import bcc.ifsuldeminas.sistemaMusicas.service.DeezerTrack;
import bcc.ifsuldeminas.sistemaMusicas.service.DeezerTracksResponse;
import bcc.ifsuldeminas.sistemaMusicas.service.PlaylistService;
import bcc.ifsuldeminas.sistemaMusicas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;


    @Autowired
    private PlaylistService playlistService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService,PlaylistService playlistService) {

        this.usuarioService = usuarioService;
        this.playlistService = playlistService;
    }
    // Endpoint para listar todos os usuários
    @GetMapping
    public ResponseEntity<Iterable<Usuario>> listarUsuarios() {
        Iterable<Usuario> usuarios = usuarioService.listarUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // Endpoint para buscar um usuário pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable long  id) {
        Optional<Usuario> usuario = usuarioService.buscarUsuarioPorId(id);
        if (usuario.isPresent()) {
            return new ResponseEntity<>(usuario.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para salvar ou atualizar um usuário
    @PostMapping
    public ResponseEntity<Usuario> salvarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);
        return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
    }

    // Endpoint para deletar um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable long  id) {
        Optional<Usuario> usuario = usuarioService.buscarUsuarioPorId(id);
        if (usuario.isPresent()) {
            usuarioService.deletarUsuario(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para calcular a idade de um usuário
    @GetMapping("/{id}/idade")
    public ResponseEntity<Integer> calcularIdade(@PathVariable long id) {
        Optional<Usuario> usuario = usuarioService.buscarUsuarioPorId(id);
        if (usuario.isPresent()) {
            int idade = usuarioService.calcularIdade(usuario.get());
            return new ResponseEntity<>(idade, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarUsuario(
            @RequestParam String nome,
            @RequestParam String senha,
            @RequestParam String genero,
            @RequestParam String dataNascimento) {

        try {
            LocalDate data = LocalDate.parse(dataNascimento);
            usuarioService.cadastrarUsuario(nome, senha, genero, data);
            return ResponseEntity.ok("Usuário cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Erro no cadastro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno no servidor.");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String nome, @RequestParam String senha) {
        try {
            boolean isLoggedIn = usuarioService.login(nome, senha);
            if (isLoggedIn) {
                return ResponseEntity.ok("Login realizado com sucesso!");
            } else {
                return ResponseEntity.status(400).body("Login falhou.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno no servidor.");
        }
    }
    @PostMapping("/criar")
    public ResponseEntity<Playlist> criarPlaylist(@RequestParam String nome, @RequestParam String descricao) {
        Playlist playlist = playlistService.criarPlaylist(nome, descricao);
        return ResponseEntity.ok(playlist);
    }
    @PostMapping("/{id}/criar")
    public ResponseEntity<?> criarPlaylist(
            @PathVariable Long id,
            @RequestParam String nome,
            @RequestParam String descricao) {
        try {

            Playlist playlist = playlistService.criarPlaylist(nome, descricao);


            usuarioService.conectarPlaylistAoUsuario(id, playlist);

            return ResponseEntity.ok(playlist);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar a playlist e conectar ao usuário.");
        }
    }

    @PostMapping("/{playlistId}/adicionarMusica/{musicaId}")
    public ResponseEntity<Playlist> adicionarMusicaNaPlaylist(@PathVariable Long playlistId, @PathVariable Long musicaId) {
        Playlist playlist = playlistService.adicionarMusicaNaPlaylist(playlistId, musicaId);
        return ResponseEntity.ok(playlist);
    }
    @PostMapping("/{id}/adiciona/{playlistId}/adicionarMusica/{musicaId}")
    public ResponseEntity<?> adicionarMusicaNaPlaylist(
            @PathVariable Long id,
            @PathVariable Long playlistId,
            @PathVariable Long musicaId) {
        try {
              Playlist playlist = playlistService.adicionarMusicaNaPlaylistDoUsuario(id, playlistId, musicaId);
            return ResponseEntity.ok(playlist);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao adicionar música na playlist.");
        }
    }
    @PostMapping("/{id}/gostaMusica/{musicaId}")
    public ResponseEntity<?> adicionarMusicaAoUsuario(
            @PathVariable Long id,
            @PathVariable Long musicaId) {
        try {
            Usuario usuario = usuarioService.adicionarMusicaAoUsuario(id, musicaId);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao adicionar música ao usuário.");
        }
    }
    @DeleteMapping("/{id}/removerMusica/{musicaId}")
    public ResponseEntity<?> removerMusicaDoUsuario(
            @PathVariable Long id,
            @PathVariable Long musicaId) {
        try {
            usuarioService.removerMusicaDoUsuario(id, musicaId);
            return ResponseEntity.ok("Música removida com sucesso do usuário.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao remover música do usuário.");
        }
    }
    @GetMapping("/{id}/recomendacoes/artistas")
    public ResponseEntity<?> recomendarMusicasPorArtista(@PathVariable Long id) {
        List<Musica> recomendacoes = usuarioService.recomendarMusicasPorArtista(id);
        return ResponseEntity.ok(recomendacoes);
    }
    @GetMapping("/{id}/recomendacoes/usuarios")
    public ResponseEntity<?> recomendarMusicasPorUsuarios(@PathVariable Long id) {
        List<Musica> recomendacoes = usuarioService.recomendarMusicasPorUsuarios(id);
        return ResponseEntity.ok(recomendacoes);
    }



}



