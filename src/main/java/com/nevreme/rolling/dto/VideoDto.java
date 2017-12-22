package com.nevreme.rolling.dto;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;


@Component
public class VideoDto {
	private Long id;
	private String description;
	private Timestamp started;
	private String ytId;
	private int duration;
	private int index_num;
	private int state;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getStarted() {
		return started;
	}
	public void setStarted(Timestamp started) {
		this.started = started;
	}
	public String getYtId() {
		return ytId;
	}
	public void setYtId(String ytId) {
		this.ytId = ytId;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getIndex_num() {
		return index_num;
	}
	public void setIndex_num(int index_num) {
		this.index_num = index_num;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
}
