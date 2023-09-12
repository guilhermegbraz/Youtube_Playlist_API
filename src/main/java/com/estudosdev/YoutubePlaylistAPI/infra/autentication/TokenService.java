package com.estudosdev.YoutubePlaylistAPI.infra.autentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("{api.security.token.secrete}")
    private String secrete;
    public String gerarToken(UsuarioEntity usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secrete);
            return JWT.create()
                    .withIssuer("API PlaylistYoutube")
                    .withSubject(usuario.getLogin())
                    .withSubject(usuario.getId().toString())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token jwt", exception);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
