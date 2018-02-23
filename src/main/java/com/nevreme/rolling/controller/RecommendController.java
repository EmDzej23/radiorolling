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
@RequestMapping("/recommend")
public class RecommendController {
	
	@Autowired
	VideoService videoService;
	
	@Autowired
	PlaylistService playlistService;
	
	@Autowired
	PlaylistUtils playlistUtils;
	
	@RequestMapping(value = { "/","" }, method = RequestMethod.GET)
	public ModelAndView addPost() {
		ModelAndView modelAndView = new ModelAndView();
		Long playlist_id = playlistService.getPlaylistByName("Knjige");
		modelAndView.addObject("playlist_id",playlist_id);
		modelAndView.addObject("vid_id",-1);
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		modelAndView.addObject("ws",System.getProperty("WS")==null?"":System.getProperty("WS"));
		modelAndView.addObject("m_title","Rolling Recommend (Knjige)");
		modelAndView.addObject("m_url","http://radiorolling.com/recommend/");
		modelAndView.addObject("m_desc","Preporuke");
		modelAndView.addObject("m_image",playlistService.findOne(playlist_id).getImage());
		modelAndView.setViewName("admin/recommend");
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
		modelAndView.addObject("m_title","Rolling Texts (Knjige)");
		modelAndView.addObject("m_url","http://radiorolling.com/recommend/");
		modelAndView.addObject("m_desc","Preporuke");
		modelAndView.addObject("m_image",playlistService.findOne(playlist_id).getImage());
		modelAndView.setViewName("admin/recommend");
		return modelAndView;
	}
	
	@RequestMapping(value = "/r", method = RequestMethod.GET)
	public ModelAndView staticalVid(@RequestParam Long id, @RequestParam String plName) {
		ModelAndView modelAndView = new ModelAndView();
		Video video = videoService.findOne(id);
		Long playlist_id = playlistService.getPlaylistByName(plName);
		modelAndView.addObject("playlist_id",playlist_id);
		modelAndView.addObject("vid_id",id);
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		modelAndView.addObject("ws",System.getProperty("WS")==null?"":System.getProperty("WS"));
		modelAndView.addObject("m_title","Rolling Recommend ("+plName+")");
		modelAndView.addObject("m_url","http://radiorolling.com/recommend/r?id="+id+"&plName="+plName);
		modelAndView.addObject("m_desc",video.getDescription());
		modelAndView.addObject("m_image",video.getYtId());
		modelAndView.setViewName("admin/recommend");
		return modelAndView;
	}
	
}
