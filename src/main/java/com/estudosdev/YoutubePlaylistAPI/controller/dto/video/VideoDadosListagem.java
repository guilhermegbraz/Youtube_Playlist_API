package com.estudosdev.YoutubePlaylistAPI.controller.dto.video;

import com.estudosdev.YoutubePlaylistAPI.controller.dto.categoria.CategoriaDadosListagem;
import com.estudosdev.YoutubePlaylistAPI.model.entities.VideoEntity;

import java.util.List;

public record VideoDadosListagem(String titulo, String descricao, String url, CategoriaDadosListagem categoria) {

    public VideoDadosListagem(VideoEntity videoEntity) {
        this(videoEntity.getTitulo(), videoEntity.getDescricao(), videoEntity.getUrl(),
                new CategoriaDadosListagem(videoEntity.getCategoria()));
    }

}
