package com.nevreme.rolling.dto;

import org.springframework.stereotype.Component;

@Component
public class VideoDetailsDto {
	private String videoUrl;
	private String videoDescription;
	private String videoDuration;
	private String videoQuote;
	private int videoOffset;
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
	
	
}
