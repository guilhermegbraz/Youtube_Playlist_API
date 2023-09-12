package com.estudosdev.YoutubePlaylistAPI.controller.dto.authenticate;

import com.estudosdev.YoutubePlaylistAPI.infra.autentication.UsuarioEntity;

public record LoginDto(String login, String senha) {

    public UsuarioEntity toUsuarioEntity() {
        return new UsuarioEntity(null, this.login, this.senha);
    }
}
