package com.estudosdev.YoutubePlaylistAPI.controller.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AtualizarVideoDto(
        @NotNull(message = "Obrigatório passar o id do video que será atualizado")
        Long id,
        String titulo,
        String descricao,

        @Nullable
        String url
) {
}
