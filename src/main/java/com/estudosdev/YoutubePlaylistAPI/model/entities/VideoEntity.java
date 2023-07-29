package com.estudosdev.YoutubePlaylistAPI.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Entity(name = "video")
@Table(name = "video")
public class VideoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String url;

    public VideoEntity(String titulo, String descricao, String url) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
    }

}
