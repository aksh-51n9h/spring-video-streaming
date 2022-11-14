package com.akxhdev.videostreaming.services.video;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VideoServiceTest {

    @Autowired
    private VideoService videoService;

    @Test
    public void testGetVideo(){
        var data = videoService.getVideo();
//        System.out.println(data.);
    }
}
