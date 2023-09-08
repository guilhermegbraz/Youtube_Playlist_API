package com.estudosdev.YoutubePlaylistAPI.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "video")
@Table(name = "video")
public class VideoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String titulo;

    @Column(nullable = false)
    @NotNull
    private String descricao;

    @Column(nullable = false)
    @NotNull
    private String url;

    @Column(name = "flag_excluido")
    @NotNull
    private boolean flagExcluido;

    public VideoEntity(String titulo, String descricao, String url) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
        this.flagExcluido = false;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
