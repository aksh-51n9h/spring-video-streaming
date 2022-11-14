package com.akxhdev.videostreaming.services.video;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class VideoServiceImpl implements VideoService {
    // Chunk size in Bs
    private static final int CHUNK_SIZE = 1024000;

    @Override
    public ResponseEntity<?> getVideo(long start, long end) {
        var filePath = "src/main/resources/video.mp4";

        try (var fileInputStream = new FileInputStream(filePath)) {
            var fileSize = getFileSize(filePath);

            var bufferedOutputStream = new ByteArrayOutputStream();
            byte[] data = new byte[CHUNK_SIZE];
            int nRead;
            while ((nRead = fileInputStream.read(data, 0, data.length)) != -1) {
                bufferedOutputStream.write(data, 0, nRead);
            }

            bufferedOutputStream.flush();

            var contentLength = end - start;
            byte[] result = new byte[(int) contentLength];
            System.arraycopy(bufferedOutputStream.toByteArray(), (int) start, result, 0, (int) contentLength);

            return ResponseEntity
                    .status(end >= fileSize ? HttpStatus.OK : HttpStatus.PARTIAL_CONTENT)
                    .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength))
                    .header(HttpHeaders.CONTENT_RANGE, "bytes %s-%s/%s".formatted(start, end, fileSize))// Content Range -> bytes <start>-<end>/<file-size>
                    .body(result);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private Long getFileSize(String path) {
        var file = new File(path);
        return file.length();
    }


    @Override
    public ResponseEntity<?> getVideo(String range) {
        if (range != null) {
            System.out.println(range);
            var start = Long.parseLong(range.substring(range.indexOf("=") + 1, range.lastIndexOf("-")));
            return getVideo(start, start + CHUNK_SIZE);
        }

        return getVideo(0, CHUNK_SIZE);
    }
}
