package com.example.Music.Service.Serv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Music.Service.Dto.SongRequestDto;
import com.example.Music.Service.Dto.SongResponseDto;
import com.example.Music.Service.Model.Song;
import com.example.Music.Service.Repository.SongRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class SongServiceImpl implements SongService{
	
	
	private  SongRepository songRepository;
	
	

	public SongServiceImpl(SongRepository songRepository) {
		super();
		this.songRepository = songRepository;
	}

	@Override
	public Page<Song> getAllSongs(int page, int size, String sortBy, String sortDir, String genere, String artist) {
		System.out.println("Searching for: genere = " + genere + ", artist = " + artist);

	    Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
	    Pageable pageable = PageRequest.of(page, size, sort);
	    return songRepository.findByGenereContainingIgnoreCaseAndArtistContainingIgnoreCase(
	            genere != null ? genere : "",
	            artist != null ? artist : "",
	            pageable
	    );
	}

	@Override
	public Song getSongById(String id) {
		
		return songRepository.findById(id).orElseThrow(() -> new RuntimeException("Song Not Found"));
	}
	
	
	private SongResponseDto mapToDto(Song song) {
	    SongResponseDto dto = new SongResponseDto();
	    dto.setId(song.getId());
	    dto.setTitle(song.getTitle());
	    dto.setArtist(song.getArtist());
	    dto.setAlbum(song.getAlbum());
	    dto.setGenere(song.getGenere());
	    dto.setDuration(song.getDuration());
	    dto.setFileUrl(song.getFileUrl());
	    return dto;
	}

	@Override
	public SongResponseDto addSong(SongRequestDto dto) {
	
	      Song song =new Song();
	      song.setTitle(dto.getTitle());
	      song.setArtist(dto.getArtist());
	      song.setAlbum(dto.getAlbum());
	      song.setGenere(dto.getGenere());
	      song.setDuration(dto.getDuration());
	      song.setFileUrl(dto.getFileUrl());
	      
	      Song saved=songRepository.save(song);
	      return mapToDto(saved);
	}

	@Override
	public SongResponseDto updateSong(String id, SongRequestDto requestDto) {
		Song existingSong = songRepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("Song not found with id: " + id));

		    existingSong.setTitle(requestDto.getTitle());
		    existingSong.setArtist(requestDto.getArtist());
		    existingSong.setAlbum(requestDto.getAlbum());
		    existingSong.setGenere(requestDto.getGenere());
		    existingSong.setDuration(requestDto.getDuration());
		    existingSong.setFileUrl(requestDto.getFileUrl());
		    

		    Song updatedSong = songRepository.save(existingSong);

		    return mapToDto(updatedSong);
		
	}

	@Override
	public void deleteSong(String id) {
	    Song song = songRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Song not found with id: " + id));

	    songRepository.delete(song);
	}

	
	

}
