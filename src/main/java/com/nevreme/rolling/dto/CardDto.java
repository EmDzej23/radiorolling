package com.nevreme.rolling.dto;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

@Component
public class CardDto {
	private Long id;
	private String link;
	private String title;
	private String description;
	private String playlistName;
	private String img;
	private Timestamp date_entered;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPlaylistName() {
		return playlistName;
	}
	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Timestamp getDate_entered() {
		return date_entered;
	}
	public void setDate_entered(Timestamp date_entered) {
		this.date_entered = date_entered;
	}
	
}
