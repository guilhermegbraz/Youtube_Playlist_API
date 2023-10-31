package com.estudosdev.YoutubePlaylistAPI.controller;

import com.estudosdev.YoutubePlaylistAPI.controller.dto.video.AtualizarVideoDto;
import com.estudosdev.YoutubePlaylistAPI.controller.dto.video.CadatroVideoDTO;
import com.estudosdev.YoutubePlaylistAPI.controller.dto.video.VideoDadosListagem;
import com.estudosdev.YoutubePlaylistAPI.infra.RegrasNegocioPlaylistException;
import com.estudosdev.YoutubePlaylistAPI.service.VideoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    private final VideoService videoService;

    public PlaylistController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    public ResponseEntity<Page<VideoDadosListagem>> listarVideos(@PageableDefault(size = 20, sort="titulo")
                                                                     Pageable paginacao) {
        var pageVideos= this.videoService.resgatar(paginacao);

        return ResponseEntity.ok(pageVideos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoDadosListagem> detalharUmVideo(@PathVariable Long id) {
        var optionalVideo = this.videoService.resgatarUmVideo(id);
        return optionalVideo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

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

    @GetMapping("/")
    public ResponseEntity<List<VideoDadosListagem>> buscarPorTitulo(
            @RequestParam(name = "search", required = false) String busca) {
        List<VideoDadosListagem> videos = this.videoService.buscarPorTitulo(busca);

        return ResponseEntity.ok(videos);
    }
}
