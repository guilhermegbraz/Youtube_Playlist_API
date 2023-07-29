package com.estudosdev.YoutubePlaylistAPI.controller;

import com.estudosdev.YoutubePlaylistAPI.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    private final VideoService videoService;

    public PlaylistController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    public ResponseEntity<List<VideoDadosListagem>> listarVideos() {
        var videos = this.videoService.resgatar();
        if(videos.isEmpty()) return (ResponseEntity<List<VideoDadosListagem>>) ResponseEntity.notFound();

        return ResponseEntity.ok(videos);
    }


}
