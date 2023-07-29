package com.estudosdev.YoutubePlaylistAPI.service;

import com.estudosdev.YoutubePlaylistAPI.controller.dto.CadatroVideoDTO;
import com.estudosdev.YoutubePlaylistAPI.controller.dto.VideoDadosListagem;
import com.estudosdev.YoutubePlaylistAPI.model.repository.VideoRepository;
import org.springframework.stereotype.Service;

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
        this.videoRepository.findAll().forEach(videoEntity -> listagemVideo.add(new VideoDadosListagem(videoEntity)));

        return listagemVideo;
    }

    public Optional<VideoDadosListagem> resgatarUmVideo(Long id) {
        var optionalVideoEntity = this.videoRepository.findById(id);
        return optionalVideoEntity.map(VideoDadosListagem::new);
    }

    public Long cadastrar(CadatroVideoDTO cadastroVideoDTO) {
        var videoEntity = cadastroVideoDTO.toVideoEntity();
        this.videoRepository.save(videoEntity);

        return videoEntity.getId();
    }
}
