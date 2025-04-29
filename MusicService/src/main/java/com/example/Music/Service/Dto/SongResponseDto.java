package com.example.Music.Service.Dto;


public class SongResponseDto {
	
	private String id;
    private String title;
    private String artist;
    private String album;
    private String genere;
    private int duration;
    private String fileUrl;
   
    
    
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
