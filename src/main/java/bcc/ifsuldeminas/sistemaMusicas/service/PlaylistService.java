package bcc.ifsuldeminas.sistemaMusicas.service;

import bcc.ifsuldeminas.sistemaMusicas.model.entities.Musica;
import bcc.ifsuldeminas.sistemaMusicas.model.entities.Playlist;
import bcc.ifsuldeminas.sistemaMusicas.repository.MusicaRepository;
import bcc.ifsuldeminas.sistemaMusicas.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    @Autowired
    private MusicaRepository musicaRepository;

    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public Playlist salvarPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public Optional<Playlist> buscarPlaylistPorId(long id) {
        return playlistRepository.findById(id);
    }

    public Iterable<Playlist> listarPlaylists() {
        return playlistRepository.findAll();
    }

    public void deletarPlaylist(long id) {
        playlistRepository.deleteById(id);
    }

    public Playlist criarPlaylist(String nome, String descricao) {
        Playlist playlist = new Playlist(nome, descricao);
        return playlistRepository.save(playlist);
    }

    public Playlist adicionarMusicaNaPlaylist(Long playlistId, Long musicaId) {
        Optional<Playlist> playlistOpt = playlistRepository.findById(playlistId);
        Optional<Musica> musicaOpt = musicaRepository.findById(musicaId);

        if (playlistOpt.isPresent() && musicaOpt.isPresent()) {
            Playlist playlist = playlistOpt.get();
            Musica musica = musicaOpt.get();

            playlist.getMusicas().add(musica);
            return playlistRepository.save(playlist);
        }

        throw new RuntimeException("Desculpe, não conseguimos encontrar.");
    }

}
