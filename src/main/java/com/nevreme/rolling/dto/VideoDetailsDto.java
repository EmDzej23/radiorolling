package com.nevreme.rolling.dto;

import org.springframework.stereotype.Component;

@Component
public class VideoDetailsDto {
	private String plId;
	private String videoUrl;
	private String videoDescription;
	private String videoDuration;
	private String videoQuote;
	private int videoOffset;
	private String plName;
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getVideoDescription() {
		return videoDescription;
	}
	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}
	public String getVideoDuration() {
		return videoDuration;
	}
	public void setVideoDuration(String videoDuration) {
		this.videoDuration = videoDuration;
	}
	public String getVideoQuote() {
		return videoQuote;
	}
	public void setVideoQuote(String videoQuote) {
		this.videoQuote = videoQuote;
	}
	public int getVideoOffset() {
		return videoOffset;
	}
	public void setVideoOffset(int videoOffset) {
		this.videoOffset = videoOffset;
	}
	public String getPlName() {
		return plName;
	}
	public void setPlName(String plName) {
		this.plName = plName;
	}
	public String getPlId() {
		return plId;
	}
	public void setPlId(String plId) {
		this.plId = plId;
	}
	
	
	
}
