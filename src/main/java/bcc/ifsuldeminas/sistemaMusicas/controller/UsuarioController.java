package bcc.ifsuldeminas.sistemaMusicas.controller;

import bcc.ifsuldeminas.sistemaMusicas.model.entities.Usuario;
import bcc.ifsuldeminas.sistemaMusicas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
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


}
