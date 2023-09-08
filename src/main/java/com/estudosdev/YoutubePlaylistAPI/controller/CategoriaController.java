package com.estudosdev.YoutubePlaylistAPI.controller;

import com.estudosdev.YoutubePlaylistAPI.controller.dto.CategoriaDadosListagem;
import com.estudosdev.YoutubePlaylistAPI.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<Collection<CategoriaDadosListagem>> listarCategorias() {
        List<CategoriaDadosListagem> categorias = this.categoriaService.listar();

        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDadosListagem> detalharCategoria(@PathVariable Long id) {
        Optional<CategoriaDadosListagem> optionalCategoria = this.categoriaService.detalhar(id);
        if (optionalCategoria.isPresent()) return ResponseEntity.ok(optionalCategoria.get());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


}
