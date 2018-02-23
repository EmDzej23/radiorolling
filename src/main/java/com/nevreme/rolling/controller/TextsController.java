package com.nevreme.rolling.controller;

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
@RequestMapping("/text")
public class TextsController {
	
	@Autowired
	VideoService videoService;
	
	@Autowired
	PlaylistService playlistService;
	
	@Autowired
	PlaylistUtils playlistUtils;
	
	@RequestMapping(value = { "/","" }, method = RequestMethod.GET)
	public ModelAndView addPost() {
		ModelAndView modelAndView = new ModelAndView();
		Long playlist_id = playlistService.getPlaylistByName("Poezija");
		modelAndView.addObject("playlist_id",playlist_id);
		modelAndView.addObject("vid_id",-1);
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		modelAndView.addObject("ws",System.getProperty("WS")==null?"":System.getProperty("WS"));
		modelAndView.addObject("m_title","Rolling Texts (Poezija)");
		modelAndView.addObject("m_url","http://radiorolling.com/text/");
		modelAndView.addObject("m_desc","Tekstovi, kratke priče i poezija");
		modelAndView.addObject("m_image",playlistService.findOne(playlist_id).getImage());
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
		modelAndView.addObject("ws",System.getProperty("WS")==null?"":System.getProperty("WS"));
		modelAndView.addObject("m_title","Rolling Texts (Poezija)");
		modelAndView.addObject("m_url","http://radiorolling.com/text/");
		modelAndView.addObject("m_desc","Tekstovi, kratke priče i poezija");
		modelAndView.addObject("m_image",playlistService.findOne(playlist_id).getImage());
		modelAndView.setViewName("admin/text");
		return modelAndView;
	}
	
	@RequestMapping(value = "/t", method = RequestMethod.GET)
	public ModelAndView staticalVid(@RequestParam Long text, @RequestParam String plName) {
		ModelAndView modelAndView = new ModelAndView();
		Video video = videoService.findOne(text);
		Long playlist_id = playlistService.getPlaylistByName(plName);
		modelAndView.addObject("playlist_id",playlist_id);
		modelAndView.addObject("vid_id",text);
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		modelAndView.addObject("ws",System.getProperty("WS")==null?"":System.getProperty("WS"));
		modelAndView.addObject("m_title","Rolling Texts ("+plName+")");
		modelAndView.addObject("m_url","http://radiorolling.com/text/t?text="+text+"&plName="+plName);
		modelAndView.addObject("m_desc",video.getDescription());
		modelAndView.addObject("m_image",video.getYtId());
		modelAndView.setViewName("admin/text");
		return modelAndView;
	}
	
	
	
}
