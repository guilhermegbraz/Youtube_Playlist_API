package com.estudosdev.YoutubePlaylistAPI.service;

import com.estudosdev.YoutubePlaylistAPI.controller.dto.categoria.AlterarCategoriaDto;
import com.estudosdev.YoutubePlaylistAPI.controller.dto.categoria.CadastroCategoriaDto;
import com.estudosdev.YoutubePlaylistAPI.controller.dto.categoria.CategoriaDadosListagem;
import com.estudosdev.YoutubePlaylistAPI.controller.dto.video.VideoDadosListagem;
import com.estudosdev.YoutubePlaylistAPI.infra.RegrasNegocioPlaylistException;
import com.estudosdev.YoutubePlaylistAPI.model.repository.CategoriaRepository;
import com.estudosdev.YoutubePlaylistAPI.model.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private VideoRepository videoRepository;

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

    @Transactional
    public CategoriaDadosListagem atualizar(AlterarCategoriaDto alterarCategoriaDto) {
        var optionalCategoria = this.categoriaRepository.findByIdAndFlagExcluidoFalse(alterarCategoriaDto.id());
        if (optionalCategoria.isEmpty())
            throw new RegrasNegocioPlaylistException("Categoria com id " +
                    alterarCategoriaDto.id().toString() + " não encontrada");
       var categoria = optionalCategoria.get();

       if(alterarCategoriaDto.titulo() != null) categoria.setTitulo(alterarCategoriaDto.titulo());
       if (alterarCategoriaDto.cor() != null) categoria.setCor(alterarCategoriaDto.cor());

       return new CategoriaDadosListagem(categoria);
    }

    @Transactional
    public void deletar(Long id) {
        var optionalCategoria = this.categoriaRepository.findByIdAndFlagExcluidoFalse(id);
        if (optionalCategoria.isEmpty())
            throw new RegrasNegocioPlaylistException("Categoria com id " + id + " Não encontrado");
        var categoria = optionalCategoria.get();
        categoria.setFlagExcluido(true);
    }

    public List<VideoDadosListagem> listarVideoPorCategoria(Long idCategoria) {
        var videos = this.videoRepository.findAllByFlagExcluidoFalseAndCategoriaId(idCategoria);

        return videos.stream().map(VideoDadosListagem::new).toList();
    }
}
