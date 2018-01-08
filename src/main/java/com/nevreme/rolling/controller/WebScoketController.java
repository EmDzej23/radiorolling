package com.nevreme.rolling.controller;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nevreme.rolling.dto.RequestMessageDto;
import com.nevreme.rolling.dto.VideoDetailsDto;
import com.nevreme.rolling.model.Playlist;
import com.nevreme.rolling.model.Video;
import com.nevreme.rolling.model.VideoState;
import com.nevreme.rolling.service.PlaylistService;
import com.nevreme.rolling.service.VideoService;
import com.nevreme.rolling.utils.PlaylistUtils;

@Controller
public class WebScoketController {
	
	@Autowired
	VideoService videoService;
	
	@Autowired
	PlaylistService service;
	
	@Autowired
	PlaylistUtils playlistUtils;
	
	@MessageMapping("/radio.nowplaying")
	@SendTo("/channel/public")
	public VideoDetailsDto sendMessage(@Payload RequestMessageDto requestMessage) {
		Video video = videoService.findVideoByState(VideoState.PLAYING);
		long time = 0;
//		if (!started) {
//			started = true;
//			playlistUtils.executeJob(1000*(video.getDuration() - time),1L);
//			video.setStarted(new Timestamp(new Date().getTime()));
//			videoService.save(video);
//		}
		long currentMilliseconds = System.currentTimeMillis();
		time = (currentMilliseconds/1000 - video.getStarted().getTime()/1000);
		VideoDetailsDto videoDto = new VideoDetailsDto();
		videoDto.setVideoDescription(video.getDescription());
		videoDto.setVideoDuration(""+video.getDuration());
		videoDto.setVideoUrl(video.getYtId()+"?start="+time);
		return videoDto;
	}
	
	@RequestMapping("/public/nowPlaying/{playlistId}")
	@ResponseBody
	public VideoDetailsDto getVideo(@PathVariable Long playlistId) {
		//TODO: Find video in public playlist - now playing
		Video video = videoService.findVideoByStateForPlaylist(VideoState.PLAYING, playlistId);
		Playlist pl = service.findOne(playlistId);
		long time = 0;
		if (!pl.isStart()) {
			pl.setStart(true);
			service.save(pl);
			playlistUtils.executeJob(1000*(video.getDuration() - time), playlistId);
			video.setStarted(new Timestamp(new Date().getTime()));
			videoService.save(video);
		}
		long currentMilliseconds = System.currentTimeMillis();
		time = (currentMilliseconds/1000 - video.getStarted().getTime()/1000);
		VideoDetailsDto videoDto = new VideoDetailsDto();
		videoDto.setVideoDescription(video.getDescription());
		videoDto.setVideoDuration(""+video.getDuration());
		videoDto.setVideoUrl(video.getYtId()+"?start="+time);
		videoDto.setVideoQuote(video.getQuote());
		return videoDto;
	}
	
}
