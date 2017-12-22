package com.nevreme.rolling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nevreme.rolling.model.Video;
import com.nevreme.rolling.model.VideoState;
import com.nevreme.rolling.service.VideoService;
import com.nevreme.rolling.utils.PlaylistUtils;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	VideoService videoService;
	
	@Autowired
	PlaylistUtils playlistUtils;
	
	private boolean started=false;
	
	@RequestMapping(value = { "/","" }, method = RequestMethod.GET)
	public ModelAndView addPost() {
//		Video video = videoService.findVideoByState(VideoState.PLAYING);
//		
//		long time = 0;
//		long currentMilliseconds = System.currentTimeMillis();
//		time = (currentMilliseconds/1000 - video.getStarted().getTime()/1000);
//		System.out.println(started);
//		if (!started) {
//			started = true;
//			playlistUtils.executeJob(1000*(video.getDuration() - time));
//		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/home");
//		modelAndView.addObject("videoUrl", video.getYtId()+"?start="+time);
//		modelAndView.addObject("vidDescription",video.getDescription());
//		modelAndView.addObject("vidDuration",video.getDuration());
		return modelAndView;
	}
}