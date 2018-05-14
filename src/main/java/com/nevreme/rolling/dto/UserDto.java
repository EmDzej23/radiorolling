package com.nevreme.rolling.dto;

import org.springframework.stereotype.Component;

@Component
public class UserDto {
	private int id;
	private String username;
	private String name;
	private String lastName;
	private int active;
	private String image;
//	private Set<Role> roles;
//	private Set<PlaylistDto> playlists;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
}
