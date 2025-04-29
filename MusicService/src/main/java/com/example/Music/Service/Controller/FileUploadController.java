package com.example.Music.Service.Controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.Music.Service.Serv.MusicFileService;

@RestController
@RequestMapping("/api/music/files")
public class FileUploadController {
	private final MusicFileService musicFileService;

    public FileUploadController(MusicFileService musicFileService) {
        this.musicFileService = musicFileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadMusicFile(@RequestParam("file") MultipartFile file) {
        // Generate unique file name
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        // Upload file
        musicFileService.uploadFile(file, fileName);

        // Get file URL
        String fileUrl = musicFileService.getFileUrl(fileName);

        return ResponseEntity.ok("File uploaded successfully. Accessible at: " + fileUrl);
    }


}
