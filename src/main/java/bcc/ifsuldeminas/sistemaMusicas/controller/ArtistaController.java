package bcc.ifsuldeminas.sistemaMusicas.controller;


import bcc.ifsuldeminas.sistemaMusicas.model.entities.Artista;
import bcc.ifsuldeminas.sistemaMusicas.service.ArtistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artistas")
public class ArtistaController {

    @Autowired
    private ArtistaService artistaService;

    @PostMapping
    public Artista criarArtista(@RequestBody Artista artista) {
        return artistaService.salvarArtista(artista);
    }

    @GetMapping
    public List<Artista> listarArtistas() {
        return artistaService.listarArtistas();
    }
}