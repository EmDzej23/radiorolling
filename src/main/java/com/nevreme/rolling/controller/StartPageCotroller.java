package com.nevreme.rolling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nevreme.rolling.service.PlaylistService;
import com.nevreme.rolling.service.VideoService;
import com.nevreme.rolling.utils.PlaylistUtils;

@Controller
@RequestMapping("/home")
public class StartPageCotroller {
	
	@Autowired
	VideoService videoService;
	
	@Autowired
	PlaylistService playlistService;
	
	@Autowired
	PlaylistUtils playlistUtils;
	
	private boolean started=false;
	
	@RequestMapping(value = { "/","" }, method = RequestMethod.GET)
	public ModelAndView addPost() {
		ModelAndView modelAndView = new ModelAndView();
//		Long playlist_id = playlistService.getPlaylistByName("Inserti Iz Filmova");
//		modelAndView.addObject("playlist_id",playlist_id);
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
//		modelAndView.addObject("ws",System.getProperty("WS")==null?"":System.getProperty("WS"));
		modelAndView.setViewName("startPage");
		return modelAndView;
	}
	
	@RequestMapping(value = { "/sugg","/sugg/" }, method = RequestMethod.GET)
	public ModelAndView sugg() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/linkSuggestions");
		return modelAndView;
	}
	
	@RequestMapping(value = { "/remote","/remote/" }, method = RequestMethod.GET)
	public ModelAndView remote() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/remote");
		return modelAndView;
	}
	
//	@RequestMapping(value = { "{name}/","{name}" }, method = RequestMethod.GET)
//	public ModelAndView ids(@PathVariable String name) {
//		ModelAndView modelAndView = new ModelAndView();
//		Long playlist_id = playlistService.getPlaylistByName(name);
//		modelAndView.addObject("playlist_id",playlist_id);
//		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
//		modelAndView.addObject("ws",System.getProperty("WS")==null?"":System.getProperty("WS"));
//		modelAndView.setViewName("admin/video");
//		return modelAndView;
//	}
	
	
	
	
}

