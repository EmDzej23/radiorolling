package com.nevreme.rolling.dto;

import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class PlaylistDto {

	private Long id;
	private String name;
	private Set<VideoDto> videos;
	private boolean start;
	private String image;
	private int playlist_type;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<VideoDto> getVideos() {
		return videos;
	}
	public void setVideos(Set<VideoDto> videos) {
		this.videos = videos;
	}
	public boolean isStart() {
		return start;
	}
	public void setStart(boolean start) {
		this.start = start;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getPlaylist_type() {
		return playlist_type;
	}
	public void setPlaylist_type(int playlist_type) {
		this.playlist_type = playlist_type;
	}
	
}
