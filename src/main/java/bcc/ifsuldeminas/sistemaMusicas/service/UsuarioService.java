package bcc.ifsuldeminas.sistemaMusicas.service;

import bcc.ifsuldeminas.sistemaMusicas.model.entities.Usuario;
import bcc.ifsuldeminas.sistemaMusicas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

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


}
