package com.estudosdev.YoutubePlaylistAPI.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name="categoria")
@Table(name= "categoria")
public class CategoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String titulo;

    @Column(nullable = false, length = 20)
    private String cor;

    @Column(nullable = false, name = "flag_excluido")
    private boolean flagExcluido;

    public CategoriaEntity(String titulo, String cor) {
        this.titulo = titulo;
        this.cor = cor;
        this.flagExcluido = false;
    }
}
