package com.akxhdev.videostreaming.services.video;

import org.springframework.http.ResponseEntity;

public interface VideoService {
    /**
     * Reads file as byte array from specified range.
     */
    ResponseEntity<?> getVideo(long start, long end);

    /**
     * Reads whole file as byte array.
     */
    ResponseEntity<?> getVideo(String range);
}
