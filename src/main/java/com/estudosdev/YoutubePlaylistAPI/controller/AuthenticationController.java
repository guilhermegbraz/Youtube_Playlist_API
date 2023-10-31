package com.estudosdev.YoutubePlaylistAPI.controller;

import com.estudosdev.YoutubePlaylistAPI.controller.dto.authenticate.DadosTokenJwtDto;
import com.estudosdev.YoutubePlaylistAPI.controller.dto.authenticate.LoginDto;
import com.estudosdev.YoutubePlaylistAPI.infra.autentication.TokenService;
import com.estudosdev.YoutubePlaylistAPI.infra.autentication.UsuarioEntity;
import com.estudosdev.YoutubePlaylistAPI.infra.autentication.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody LoginDto loginDto) {

        var token = new UsernamePasswordAuthenticationToken(loginDto.login(), loginDto.senha());
        var authentication = manager.authenticate(token);
        var tokenJwt = this.tokenService.gerarToken((UsuarioEntity) authentication.getPrincipal());

        var tokenDTO = new DadosTokenJwtDto(tokenJwt);

        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/cadastro")
    public ResponseEntity cadastrarUsuario(@RequestBody LoginDto loginDto, UriComponentsBuilder uriBuilder) {
        var u = loginDto.toUsuarioEntity();
        u.setSenha(passwordEncoder.encode(loginDto.senha()));
        var usuario = usuarioRepository.save(u);
        return ResponseEntity.ok(usuario);
    }
}
