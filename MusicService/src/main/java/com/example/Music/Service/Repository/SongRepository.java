package com.example.Music.Service.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.Music.Service.Model.Song;

public interface SongRepository extends MongoRepository<Song, String> {
//	List<Song>findByGenere(String genere);
//	List<Song>findByArtist(String artist);
	
	Page<Song> findByGenereContainingIgnoreCaseAndArtistContainingIgnoreCase(String genere, String artist, Pageable pageable);


}
