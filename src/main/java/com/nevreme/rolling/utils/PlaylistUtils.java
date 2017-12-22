package com.nevreme.rolling.utils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;

import com.nevreme.rolling.dto.VideoDetailsDto;
import com.nevreme.rolling.model.Video;
import com.nevreme.rolling.model.VideoState;
import com.nevreme.rolling.service.VideoService;

@Component
public class PlaylistUtils {
	private TaskScheduler scheduler = new ConcurrentTaskScheduler();

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	
	@Autowired
	VideoService videoService;

	private boolean started = false;

	public void executeJob(long interval) {
		Timer timer = new Timer();
		System.out.println(System.currentTimeMillis());
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println(System.currentTimeMillis());
				setStarted(true);
				Video currentVideo = videoService.findVideoByState(VideoState.PLAYING);
				currentVideo.setState(VideoState.NOT_PLAYING);
				videoService.save(currentVideo);
				Video nextVideo = videoService.findNextVideo(currentVideo.getIndex_num());
				nextVideo.setStarted(new Timestamp(new Date().getTime()));
				nextVideo.setState(VideoState.PLAYING);
				videoService.save(nextVideo);
				long time = 0;
				long currentMilliseconds = System.currentTimeMillis();
				time = (currentMilliseconds/1000 - nextVideo.getStarted().getTime()/1000);
				VideoDetailsDto videoDto = new VideoDetailsDto();
				videoDto.setVideoDescription(nextVideo.getDescription());
				videoDto.setVideoDuration(""+nextVideo.getDuration());
				videoDto.setVideoUrl(nextVideo.getYtId()+"?start="+time);
				messagingTemplate.convertAndSend("/channel/public", videoDto);
				executeJob(nextVideo.getDuration()*1000);
			}
		}, interval);
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

}
