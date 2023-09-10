package com.estudosdev.YoutubePlaylistAPI.controller.dto.video;


import com.estudosdev.YoutubePlaylistAPI.model.entities.VideoEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CadatroVideoDTO
(
    @NotBlank(message = "O campo titulo é obrigatório")
    @Size(min = 1, max = 50)
    String titulo,

    @NotBlank(message = "O campo descricao é obrigatório")
    @Size(min = 1, max = 200)
    String descricao,

    @NotBlank(message = "O campo url é obrigatório")
    @Size(min = 1, max = 80)
    String url,

    @NotNull(message = "O atributo categoriaID é obrigatório")
    Long idCategoria
)
{
    public VideoEntity toVideoEntity() {
        return new VideoEntity(this.titulo, this.descricao, this.url);
    }
}
