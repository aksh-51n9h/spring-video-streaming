package com.akxhdev.videostreaming.controllers;

import com.akxhdev.videostreaming.services.video.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/")
    public ResponseEntity<?> getVideo(@RequestHeader(value = "Range", required = false) String range) {
        return videoService.getVideo(range);
    }
}
