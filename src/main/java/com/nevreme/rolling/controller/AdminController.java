package com.nevreme.rolling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nevreme.rolling.service.PlaylistService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	PlaylistService playlistService;
	
	@RequestMapping(value = { "/playlistEdit","/playlistEdit/" }, method = RequestMethod.GET)
	public ModelAndView addPost() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/playlist");
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		return modelAndView;
	}
	
	@RequestMapping(value = { "/setup","/setup/" }, method = RequestMethod.GET)
	public ModelAndView setup() {
		Long playlist_id = playlistService.getPlaylistByName("Rolling");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		modelAndView.addObject("playlist_id",playlist_id);
		modelAndView.setViewName("admin/playlist");
		return modelAndView;
	}
	
	@RequestMapping(value = "/setupp", method = RequestMethod.GET)
	public ModelAndView setupid(@RequestParam Long id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("playlist_id",id);
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		modelAndView.setViewName("admin/playlist");
		return modelAndView;
	}
	
	@RequestMapping(value = { "/playlistAdd/","/playlistAdd" }, method = RequestMethod.GET)
	public ModelAndView playlistAdd() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/playlistAdd");
		return modelAndView;
	}
}
