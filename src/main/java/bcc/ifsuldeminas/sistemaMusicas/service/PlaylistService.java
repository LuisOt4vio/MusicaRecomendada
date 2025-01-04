package bcc.ifsuldeminas.sistemaMusicas.service;

import bcc.ifsuldeminas.sistemaMusicas.model.entities.Playlist;
import bcc.ifsuldeminas.sistemaMusicas.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public Playlist salvarPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public Optional<Playlist> buscarPlaylistPorId(String id) {
        return playlistRepository.findById(id);
    }

    public Iterable<Playlist> listarPlaylists() {
        return playlistRepository.findAll();
    }

    public void deletarPlaylist(String id) {
        playlistRepository.deleteById(id);
    }
}
