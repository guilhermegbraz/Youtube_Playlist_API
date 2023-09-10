package com.estudosdev.YoutubePlaylistAPI.controller.dto.categoria;

import com.estudosdev.YoutubePlaylistAPI.model.entities.CategoriaEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroCategoriaDto (
        @NotBlank(message = "O campo titulo é obrigatorio")
        @Size(min = 1, max = 50, message = "O titulo não deve exceder 50 caracteres")
        String titulo,

        @NotBlank(message = "O campo cor é obrigatorio")
        @Size(min= 1, max= 20, message = "O campo cor não deve exceder 20 caracteres")
        String cor
) {
    public CategoriaEntity toCategoriaEntity() {
        return new CategoriaEntity(this.titulo, this.cor);
    }
}
