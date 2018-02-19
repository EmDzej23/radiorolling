package com.nevreme.rolling.controller;

import org.hibernate.validator.internal.util.IgnoreJava6Requirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nevreme.rolling.model.Video;
import com.nevreme.rolling.service.PlaylistService;
import com.nevreme.rolling.service.VideoService;
import com.nevreme.rolling.utils.PlaylistUtils;

@Controller
@RequestMapping("/video")
public class VideoController {
	
	@Autowired
	VideoService videoService;
	
	@Autowired
	PlaylistService playlistService;
	
	@Autowired
	PlaylistUtils playlistUtils;
	
	private boolean started=false;
	
	@RequestMapping(value = { "autoplay/","autoplay" }, method = RequestMethod.GET)
	public ModelAndView addPost() {
		ModelAndView modelAndView = new ModelAndView();
		Long playlist_id = playlistService.getPlaylistByName("Inserti Iz Filmova");
		modelAndView.addObject("playlist_id",playlist_id);
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		modelAndView.addObject("ws",System.getProperty("WS")==null?"":System.getProperty("WS"));
		modelAndView.addObject("m_title","Rolling Video (Inserti Iz Filmova)");
		modelAndView.addObject("m_url","http://radiorolling.com/video/autoplay/Inserti Iz Filmova");
		modelAndView.addObject("m_desc","Radio Mode");
		modelAndView.addObject("m_image",playlistService.findOne(playlist_id).getImage());
		modelAndView.setViewName("admin/video");
		return modelAndView;
	}
	
	@RequestMapping(value = { "manualplay/","manualplay" }, method = RequestMethod.GET)
	public ModelAndView statical() {
		ModelAndView modelAndView = new ModelAndView();
		Long playlist_id = playlistService.getPlaylistByName("Inserti Iz Filmova");
		modelAndView.addObject("playlist_id",playlist_id);
		modelAndView.addObject("vid_id",-1);
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		modelAndView.addObject("ws",System.getProperty("WS")==null?"":System.getProperty("WS"));
		modelAndView.addObject("m_title","Rolling Video (Inserti Iz Filmova)");
		modelAndView.addObject("m_url","http://radiorolling.com/video/manualplay/Inserti Iz Filmova");
		modelAndView.addObject("m_desc","Manual Mode");
		modelAndView.addObject("m_image",playlistService.findOne(playlist_id).getImage());
		modelAndView.setViewName("admin/staticalVideo");
		return modelAndView;
	}
	
	@RequestMapping(value = { "manualplay/{name}/","manualplay/{name}" }, method = RequestMethod.GET)
	public ModelAndView staticalName(@PathVariable String name) {
		ModelAndView modelAndView = new ModelAndView();
		Long playlist_id = playlistService.getPlaylistByName(name);
		modelAndView.addObject("playlist_id",playlist_id);
		modelAndView.addObject("vid_id",-1);
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		modelAndView.addObject("ws",System.getProperty("WS")==null?"":System.getProperty("WS"));
		modelAndView.addObject("m_title","Rolling Video ("+name+")");
		modelAndView.addObject("m_url","http://radiorolling.com/video/manualplay/"+name);
		modelAndView.addObject("m_desc","Manual Mode");
		modelAndView.addObject("m_image",playlistService.findOne(playlist_id).getImage());
		modelAndView.setViewName("admin/staticalVideo");
		return modelAndView;
	}
	
	@RequestMapping(value = "manualplay/vid", method = RequestMethod.GET)
	public ModelAndView staticalVid(@RequestParam String vid, @RequestParam String plName) {
		ModelAndView modelAndView = new ModelAndView();
		Video video = videoService.findVideoByYtId(vid);
		Long playlist_id = playlistService.getPlaylistByName(plName);
		modelAndView.addObject("playlist_id",playlist_id);
		modelAndView.addObject("vid_id",vid);
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		modelAndView.addObject("ws",System.getProperty("WS")==null?"":System.getProperty("WS"));
		modelAndView.addObject("m_title","Rolling Video ("+plName+")");
		modelAndView.addObject("m_url","http://radiorolling.com/video/manualplay/vid?vid="+vid+"&plName="+plName);
		modelAndView.addObject("m_desc","Video:"+video.getDescription());
		modelAndView.addObject("m_image","https://i.ytimg.com/vi/"+vid+"/hqdefault.jpg");
		modelAndView.setViewName("admin/staticalVideo");
		return modelAndView;
	}
	
	@RequestMapping(value = { "autoplay/{name}/","autoplay/{name}" }, method = RequestMethod.GET)
	public ModelAndView ids(@PathVariable String name) {
		ModelAndView modelAndView = new ModelAndView();
		Long playlist_id = playlistService.getPlaylistByName(name);
		modelAndView.addObject("playlist_id",playlist_id);
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		modelAndView.addObject("ws",System.getProperty("WS")==null?"":System.getProperty("WS"));
		modelAndView.addObject("m_title","Rolling Video ("+name+")");
		modelAndView.addObject("m_url","http://radiorolling.com/video/autoplay/"+name);
		modelAndView.addObject("m_desc","Radio Mode");
		modelAndView.addObject("m_image",playlistService.findOne(playlist_id).getImage());
		modelAndView.setViewName("admin/video");
		return modelAndView;
	}
	
	
}

