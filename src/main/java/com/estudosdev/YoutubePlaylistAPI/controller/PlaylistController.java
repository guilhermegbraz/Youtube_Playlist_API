package com.estudosdev.YoutubePlaylistAPI.controller;

import com.estudosdev.YoutubePlaylistAPI.controller.dto.AtualizarVideoDto;
import com.estudosdev.YoutubePlaylistAPI.controller.dto.CadatroVideoDTO;
import com.estudosdev.YoutubePlaylistAPI.controller.dto.VideoDadosListagem;
import com.estudosdev.YoutubePlaylistAPI.infra.RegrasNegocioPlaylistException;
import com.estudosdev.YoutubePlaylistAPI.service.VideoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    private final VideoService videoService;

    public PlaylistController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    public ResponseEntity listarVideos() {
        var videos = this.videoService.resgatar();
        if(videos.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");

        return ResponseEntity.ok(videos);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalharUmVideo(@PathVariable Long id) {
        var optionalVideo = this.videoService.resgatarUmVideo(id);
        if(optionalVideo.isPresent()) return ResponseEntity.ok(optionalVideo.get());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
    }

    @PostMapping
    public ResponseEntity<?> cadastrarVideo(@RequestBody @Valid CadatroVideoDTO cadastroVideoDTO, UriComponentsBuilder uriBuilder) {
        var idVideoCadastrado = this.videoService.cadastrar(cadastroVideoDTO);
        var uri = uriBuilder.path("/playlist/{id}").buildAndExpand(idVideoCadastrado).toUri();

        return ResponseEntity.created(uri).body("Video cadastrado na playlist");
    }

    @PutMapping
    public ResponseEntity atualizarVideo(@RequestBody @Valid AtualizarVideoDto dadosAtualização) {
        try{
            var videoAtualizado = this.videoService.atualizar(dadosAtualização);

            return ResponseEntity.ok(videoAtualizado);
        } catch (RegrasNegocioPlaylistException exception){
            return ResponseEntity.badRequest().body("O id passado não pertence a nenhum video cadastrado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluirVideo(@PathVariable Long id) {
        try {
            this.videoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RegrasNegocioPlaylistException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

}
