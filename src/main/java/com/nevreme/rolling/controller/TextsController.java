package com.nevreme.rolling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nevreme.rolling.service.PlaylistService;
import com.nevreme.rolling.service.VideoService;
import com.nevreme.rolling.utils.PlaylistUtils;

@Controller
@RequestMapping("/text")
public class TextsController {
	
	@Autowired
	VideoService videoService;
	
	@Autowired
	PlaylistService playlistService;
	
	@Autowired
	PlaylistUtils playlistUtils;
	
	private boolean started=false;
	
	@RequestMapping(value = { "/","" }, method = RequestMethod.GET)
	public ModelAndView statical() {
		ModelAndView modelAndView = new ModelAndView();
		Long playlist_id = playlistService.getPlaylistByName("Poezija");
		modelAndView.addObject("playlist_id",playlist_id);
		modelAndView.addObject("vid_id",-1);
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		modelAndView.setViewName("admin/text");
		return modelAndView;
	}
	
	@RequestMapping(value = { "/{name}/","/{name}" }, method = RequestMethod.GET)
	public ModelAndView staticalName(@PathVariable String name) {
		ModelAndView modelAndView = new ModelAndView();
		Long playlist_id = playlistService.getPlaylistByName(name);
		modelAndView.addObject("playlist_id",playlist_id);
		modelAndView.addObject("vid_id",-1);
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		modelAndView.setViewName("admin/text");
		return modelAndView;
	}
	
	@RequestMapping(value = "/t", method = RequestMethod.GET)
	public ModelAndView staticalVid(@RequestParam String id, @RequestParam String plName) {
		ModelAndView modelAndView = new ModelAndView();
		Long playlist_id = playlistService.getPlaylistByName(plName);
		modelAndView.addObject("playlist_id",playlist_id);
		modelAndView.addObject("vid_id",id);
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		modelAndView.setViewName("admin/text");
		return modelAndView;
	}
	
	
	
	
}
