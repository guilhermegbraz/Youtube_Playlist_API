package com.estudosdev.YoutubePlaylistAPI.controller;

import com.estudosdev.YoutubePlaylistAPI.controller.dto.categoria.AlterarCategoriaDto;
import com.estudosdev.YoutubePlaylistAPI.controller.dto.categoria.CadastroCategoriaDto;
import com.estudosdev.YoutubePlaylistAPI.controller.dto.categoria.CategoriaDadosListagem;
import com.estudosdev.YoutubePlaylistAPI.infra.RegrasNegocioPlaylistException;
import com.estudosdev.YoutubePlaylistAPI.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categoria")
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

    @PostMapping
    public ResponseEntity<CategoriaDadosListagem> cadastrarCategoria(
            @RequestBody @Valid CadastroCategoriaDto cadastroCategoriaDto
            , UriComponentsBuilder uriBuilder)
    {
        var id = this.categoriaService.cadastrar(cadastroCategoriaDto);
        var uri = uriBuilder.path("/categoria/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).body(this.categoriaService.detalhar(id).get());
    }

    @PutMapping
    public ResponseEntity alterarCategoria(@RequestBody @Valid AlterarCategoriaDto alterarCategoriaDto) {
        try {
            CategoriaDadosListagem categoriaAtualizada = this.categoriaService.atualizar(alterarCategoriaDto);
            return ResponseEntity.ok(categoriaAtualizada);
        } catch (RegrasNegocioPlaylistException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarCategoria(@PathVariable Long id) {
        try {
            this.categoriaService.deletar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RegrasNegocioPlaylistException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

}
