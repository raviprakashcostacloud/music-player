package com.example.Music.Service.Controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Music.Service.Dto.SongRequestDto;
import com.example.Music.Service.Dto.SongResponseDto;
import com.example.Music.Service.Model.Song;
import com.example.Music.Service.Serv.SongService;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/music/songs")
public class SongController {

	
	private SongService songService;
	
	public SongController(SongService songService) {
		super();
		this.songService = songService;
	}

	@GetMapping
	public ResponseEntity<Page<Song>> getAllSongs(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size,
	        @RequestParam(defaultValue = "title") String sortBy,
	        @RequestParam(defaultValue = "asc") String sortDir,
	        @RequestParam(required = false) String genere,
	        @RequestParam(required = false) String artist
	) {
	    Page<Song> songs = songService.getAllSongs(page, size, sortBy, sortDir, genere, artist);
	    return ResponseEntity.ok(songs);	
	}
	
	@GetMapping("/{id}")
	public Song getSongById(@PathVariable String id) {
		return songService.getSongById(id);
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<SongResponseDto> updateSong(@PathVariable String id, @RequestBody SongRequestDto requestDto) {
		SongResponseDto updatedSong = songService.updateSong(id, requestDto);
		return ResponseEntity.ok(updatedSong);
	}
	
	@PostMapping
	public SongResponseDto addSong(@RequestBody SongRequestDto requestDto) {
		return songService.addSong(requestDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSong(@PathVariable String id) {
		songService.deleteSong(id);
		return ResponseEntity.noContent().build();
	}
	
	
}
