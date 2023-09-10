package com.estudosdev.YoutubePlaylistAPI.controller.dto.categoria;

import com.estudosdev.YoutubePlaylistAPI.model.entities.CategoriaEntity;

public record CategoriaDadosListagem(String titulo, String cor) {

    public CategoriaDadosListagem(CategoriaEntity categoriaEntity) {
        this(categoriaEntity.getTitulo(), categoriaEntity.getCor());

    }
}
