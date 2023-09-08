package com.estudosdev.YoutubePlaylistAPI.service;

import com.estudosdev.YoutubePlaylistAPI.controller.dto.categoria.CadastroCategoriaDto;
import com.estudosdev.YoutubePlaylistAPI.controller.dto.categoria.CategoriaDadosListagem;
import com.estudosdev.YoutubePlaylistAPI.model.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    /**
     * Busca no banco de dados todos os registros não excluidos, converte de Entity para
     * Classe de listagem de dados e devolve a coleção de categorias.
     *
     * @return Lista das categorias encontrada.
     */
    public List<CategoriaDadosListagem> listar() {
        var categorias = this.categoriaRepository.findAllByFlagExcluidoFalse();

        return categorias.stream().map(CategoriaDadosListagem::new).toList();
    }

    public Optional<CategoriaDadosListagem> detalhar(Long id) {

        return this.categoriaRepository.findByIdAndFlagExcluidoFalse(id)
                .map(CategoriaDadosListagem::new);
    }

    public Long cadastrar(CadastroCategoriaDto cadastroCategoriaDto) {
        var categoria = this.categoriaRepository.save(cadastroCategoriaDto.toCategoriaEntity());

        return categoria.getId();
    }
}
