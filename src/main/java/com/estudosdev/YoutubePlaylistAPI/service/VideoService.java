package com.estudosdev.YoutubePlaylistAPI.service;

import com.estudosdev.YoutubePlaylistAPI.controller.dto.video.AtualizarVideoDto;
import com.estudosdev.YoutubePlaylistAPI.controller.dto.video.CadatroVideoDTO;
import com.estudosdev.YoutubePlaylistAPI.controller.dto.video.VideoDadosListagem;
import com.estudosdev.YoutubePlaylistAPI.infra.RegrasNegocioPlaylistException;
import com.estudosdev.YoutubePlaylistAPI.model.repository.CategoriaRepository;
import com.estudosdev.YoutubePlaylistAPI.model.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {

    private final VideoRepository videoRepository;

    @Autowired
    private final CategoriaRepository categoriaRepository;

    public VideoService(VideoRepository videoRepository, CategoriaRepository categoriaRepository) {
        this.videoRepository = videoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Page<VideoDadosListagem> resgatar(Pageable pageable) {

        return this.videoRepository.findAllByFlagExcluidoFalse(pageable)
                .map(VideoDadosListagem::new);
    }

    public Optional<VideoDadosListagem> resgatarUmVideo(Long id) {
        var optionalVideoEntity = this.videoRepository.findByIdAndFlagExcluidoFalse(id);
        return optionalVideoEntity.map(VideoDadosListagem::new);
    }

    public Long cadastrar(CadatroVideoDTO cadastroVideoDTO) {
        Long idCategoria = (cadastroVideoDTO.idCategoria() == null) ? 1L : cadastroVideoDTO.idCategoria();
        var categoria = this.categoriaRepository.findByIdAndFlagExcluidoFalse(idCategoria);
        if (categoria.isEmpty()) throw new RegrasNegocioPlaylistException("Esse id não pertence a nenhuma categoria");
        var videoEntity = cadastroVideoDTO.toVideoEntity();
        videoEntity.setCategoria(categoria.get());

        this.videoRepository.save(videoEntity);

        return videoEntity.getId();
    }

    @Transactional
    public VideoDadosListagem atualizar(AtualizarVideoDto dadosAtualizacao) {
        var optionalReferenciaVideo = this.videoRepository.findByIdAndFlagExcluidoFalse(dadosAtualizacao.id());
        if(optionalReferenciaVideo.isEmpty()) throw new RegrasNegocioPlaylistException("Video não encontrado");
        var referenciaVideo = optionalReferenciaVideo.get();

        if(dadosAtualizacao.descricao() != null) referenciaVideo.setDescricao(dadosAtualizacao.descricao());
        if(dadosAtualizacao.titulo() != null) referenciaVideo.setTitulo(dadosAtualizacao.titulo());
        if(dadosAtualizacao.url() != null) referenciaVideo.setUrl(dadosAtualizacao.url());
        if(dadosAtualizacao.idCategoria() != null) {
            var optionalCategoria = this.categoriaRepository.findByIdAndFlagExcluidoFalse(dadosAtualizacao.idCategoria());
            if (optionalCategoria.isPresent()) referenciaVideo.setCategoria(optionalCategoria.get());
            else throw new RegrasNegocioPlaylistException("O id dessa categoria não existe");
        }

        return new VideoDadosListagem(referenciaVideo);
    }

    @Transactional
    public void deletar(Long id) {
        var optionalReferenciaVideo = this.videoRepository.findByIdAndFlagExcluidoFalse(id);
        if(optionalReferenciaVideo.isEmpty()) throw new RegrasNegocioPlaylistException("Video não encontrado");
        var referenciaVideo = optionalReferenciaVideo.get();

        referenciaVideo.setFlagExcluido(true);
    }

    public List<VideoDadosListagem> buscarPorTitulo(String busca) {
        var videos = this.videoRepository.findByFlagExcluidoFalseAndTituloContainingIgnoreCase(busca);

        return videos.stream().map(VideoDadosListagem::new).toList();
    }
}
