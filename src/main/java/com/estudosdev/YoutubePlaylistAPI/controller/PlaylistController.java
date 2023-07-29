package com.estudosdev.YoutubePlaylistAPI.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    @GetMapping
    public String hello() {
        return "Ola controller !";
    }
}
