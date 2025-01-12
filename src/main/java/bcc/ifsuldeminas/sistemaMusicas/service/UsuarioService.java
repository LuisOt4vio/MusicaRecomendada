package bcc.ifsuldeminas.sistemaMusicas.service;

import bcc.ifsuldeminas.sistemaMusicas.model.entities.Musica;
import bcc.ifsuldeminas.sistemaMusicas.model.entities.Playlist;
import bcc.ifsuldeminas.sistemaMusicas.model.entities.Usuario;
import bcc.ifsuldeminas.sistemaMusicas.repository.MusicaRepository;
import bcc.ifsuldeminas.sistemaMusicas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    @Autowired
    private MusicaRepository musicaRepository;
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
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
}
