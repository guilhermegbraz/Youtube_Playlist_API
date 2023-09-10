package com.estudosdev.YoutubePlaylistAPI.controller.dto.categoria;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AlterarCategoriaDto(
        @NotNull(message = "Obrigatorio informar o id da categoria que será alterada")
        Long id,

        @Size(min = 0, max = 50, message = "O titulo não deve exceder 50 caracteres")
        String titulo,

        @Size(min = 0, max = 20, message = "O atributo cor não deve exceder 20 caracteres")
        String cor
) {
}
