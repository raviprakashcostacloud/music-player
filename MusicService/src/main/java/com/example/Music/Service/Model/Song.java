package com.example.Music.Service.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;

@Document(collection = "songs")

public class Song {
	
    @Id
    private String id;
    private String title;
    private String artist;
    private String album;
    private String genere;
    private int duration;
    private String fileUrl;
    
    

    
	public Song() {
		super();
		
	}



	public Song(String id, String title, String artist, String album, String genere, int duration, String fileUrl) {
		super();
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.genere = genere;
		this.duration = duration;
		this.fileUrl = fileUrl;
	}



	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getGenere() {
		return genere;
	}
	public void setGenere(String genere) {
		this.genere = genere;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getFileUrl() {
	    return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
	    this.fileUrl = fileUrl;
	}
	
    
    

}
