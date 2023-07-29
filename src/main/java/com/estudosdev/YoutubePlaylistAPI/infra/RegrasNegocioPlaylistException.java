package com.estudosdev.YoutubePlaylistAPI.infra;

public class RegrasNegocioPlaylistException extends RuntimeException{
    public RegrasNegocioPlaylistException(String mensagem) {
        super(mensagem);
    }
}
