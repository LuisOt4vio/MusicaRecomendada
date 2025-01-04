package bcc.ifsuldeminas.sistemaMusicas.controller;

import bcc.ifsuldeminas.sistemaMusicas.model.entities.Genero;
import bcc.ifsuldeminas.sistemaMusicas.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/generos")
public class GeneroController {

    private final GeneroService generoService;

    @Autowired
    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Genero>> listarGeneros() {
        Iterable<Genero> generos = generoService.listarGeneros();
        return new ResponseEntity<>(generos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genero> buscarGeneroPorId(@PathVariable String id) {
        Optional<Genero> genero = generoService.buscarGeneroPorId(id);
        return genero.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Genero> salvarGenero(@RequestBody Genero genero) {
        Genero generoSalvo = generoService.salvarGenero(genero);
        return new ResponseEntity<>(generoSalvo, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarGenero(@PathVariable String id) {
        Optional<Genero> genero = generoService.buscarGeneroPorId(id);
        if (genero.isPresent()) {
            generoService.deletarGenero(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
