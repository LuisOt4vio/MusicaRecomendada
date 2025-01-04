package bcc.ifsuldeminas.sistemaMusicas.service;

import bcc.ifsuldeminas.sistemaMusicas.model.entities.Usuario;
import bcc.ifsuldeminas.sistemaMusicas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //Criar ou atualizar um usuário
    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    //Buscar um usuário pelo ID
    public Optional<Usuario> buscarUsuarioPorId(String id) {
        return usuarioRepository.findById(id);
    }

    //Deletar um usuário pelo ID
    public void deletarUsuario(String id) {
        usuarioRepository.deleteById(id);
    }

    //Listar todos os usuários
    public Iterable<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    //Exemplo de método para calcular a idade de um usuário
    public int calcularIdade(Usuario usuario) {
        return usuario.getIdade();
    }
}
