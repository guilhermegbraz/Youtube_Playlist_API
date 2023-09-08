package com.estudosdev.YoutubePlaylistAPI.service;

import com.estudosdev.YoutubePlaylistAPI.controller.dto.video.AtualizarVideoDto;
import com.estudosdev.YoutubePlaylistAPI.controller.dto.video.CadatroVideoDTO;
import com.estudosdev.YoutubePlaylistAPI.controller.dto.video.VideoDadosListagem;
import com.estudosdev.YoutubePlaylistAPI.infra.RegrasNegocioPlaylistException;
import com.estudosdev.YoutubePlaylistAPI.model.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {

    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public List<VideoDadosListagem> resgatar() {
        var listagemVideo = new ArrayList<VideoDadosListagem>();
        this.videoRepository.findAllByFlagExcluidoFalse().forEach(videoEntity ->
                listagemVideo.add(new VideoDadosListagem(videoEntity)));

        return listagemVideo;
    }

    public Optional<VideoDadosListagem> resgatarUmVideo(Long id) {
        var optionalVideoEntity = this.videoRepository.findByIdAndFlagExcluidoFalse(id);
        return optionalVideoEntity.map(VideoDadosListagem::new);
    }

    public Long cadastrar(CadatroVideoDTO cadastroVideoDTO) {
        var videoEntity = cadastroVideoDTO.toVideoEntity();
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

        return new VideoDadosListagem(referenciaVideo);
    }

    @Transactional
    public void deletar(Long id) {
        var optionalReferenciaVideo = this.videoRepository.findByIdAndFlagExcluidoFalse(id);
        if(optionalReferenciaVideo.isEmpty()) throw new RegrasNegocioPlaylistException("Video não encontrado");
        var referenciaVideo = optionalReferenciaVideo.get();

        referenciaVideo.setFlagExcluido(true);
    }
}
