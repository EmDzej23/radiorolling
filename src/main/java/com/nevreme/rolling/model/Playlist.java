package com.nevreme.rolling.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Component
@Entity
@Table(name = "playlist")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class , property = "id")
public class Playlist {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private boolean start;
	
	@Column
	private String image;
	
	@Column
	private int playlist_type;
	
	@OneToMany(mappedBy="playlist",cascade=CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
	private Set<Video> videos;

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

	public Set<Video> getVideos() {
		return videos;
	}

	public void setVideos(Set<Video> videos) {
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
