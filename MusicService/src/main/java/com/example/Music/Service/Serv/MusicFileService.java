package com.example.Music.Service.Serv;

import org.springframework.web.multipart.MultipartFile;

public interface MusicFileService {
	  void uploadFile(MultipartFile file, String fileName);
	    String getFileUrl(String fileName);

}
