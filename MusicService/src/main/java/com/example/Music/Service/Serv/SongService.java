package com.example.Music.Service.Serv;

import java.util.List;

import com.example.Music.Service.Dto.SongRequestDto;
import com.example.Music.Service.Dto.SongResponseDto;
import com.example.Music.Service.Model.Song;
import org.springframework.data.domain.Page;

public interface SongService {
	

	Page<Song> getAllSongs(int page, int size, String sortBy, String sortDir, String genere, String artist);

    Song getSongById(String id);
    SongResponseDto addSong(SongRequestDto songRequestDto);
    SongResponseDto updateSong(String id, SongRequestDto requestDto);
    void deleteSong(String id);

}
