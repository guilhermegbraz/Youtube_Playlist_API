package com.estudosdev.YoutubePlaylistAPI.service;

import com.estudosdev.YoutubePlaylistAPI.controller.VideoDadosListagem;
import com.estudosdev.YoutubePlaylistAPI.model.repository.VideoRepository;
import org.springframework.http.ResponseEntity;
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
}
