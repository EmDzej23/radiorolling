package com.nevreme.rolling.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nevreme.rolling.dao.mapping.MapperConfig;
import com.nevreme.rolling.dto.VideoDto;
import com.nevreme.rolling.model.Video;
import com.nevreme.rolling.service.PlaylistService;
import com.nevreme.rolling.service.VideoService;
import com.nevreme.rolling.utils.PlaylistUtils;

@Controller
@RequestMapping("/music")
public class HomeController {
	
	@Autowired
	VideoService videoService;
	
	@Autowired
	MapperConfig mapper;
	
	@Autowired
	PlaylistService playlistService;
	
	@Autowired
	PlaylistUtils playlistUtils;
	
	private boolean started=false;
	
	@RequestMapping(value = { "autoplay/","autoplay" }, method = RequestMethod.GET)
	public ModelAndView addPost() {
		ModelAndView modelAndView = new ModelAndView();
		Long playlist_id = playlistService.getPlaylistByName("Rolling");
		modelAndView.addObject("playlist_id",playlist_id);
		modelAndView.addObject("m_title","Rolling Music (Rolling)");
		modelAndView.addObject("m_url","http://radiorolling.com/music/autoplay/Rolling");
		modelAndView.addObject("m_desc","Radio Mode");
		modelAndView.addObject("m_image",playlistService.findOne(playlist_id).getImage());
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		modelAndView.addObject("ws",System.getProperty("WS")==null?"":System.getProperty("WS"));
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}
	
	@RequestMapping(value = { "manualplay/","manualplay" }, method = RequestMethod.GET)
	public ModelAndView statical() {
		ModelAndView modelAndView = new ModelAndView();
		Long playlist_id = playlistService.getPlaylistByName("Rolling");
		modelAndView.addObject("playlist_id",playlist_id);
		modelAndView.addObject("vid_id",-1);
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		modelAndView.addObject("ws",System.getProperty("WS")==null?"":System.getProperty("WS"));
		modelAndView.addObject("m_title","Rolling Music (Rolling)");
		modelAndView.addObject("m_url","http://radiorolling.com/music/manualplay/Rolling");
		modelAndView.addObject("m_desc","Manual Mode");
		modelAndView.addObject("filter","");
		modelAndView.addObject("m_image",playlistService.findOne(playlist_id).getImage());
		modelAndView.setViewName("admin/staticalMusic");
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
		modelAndView.addObject("m_title","Rolling Music ("+name+")");
		modelAndView.addObject("m_url","http://radiorolling.com/music/manualplay/"+name);
		modelAndView.addObject("m_desc","Manual Mode");
		modelAndView.addObject("filter","");
		modelAndView.addObject("m_image",playlistService.findOne(playlist_id).getImage());
		modelAndView.setViewName("admin/staticalMusic");
		return modelAndView;
	}
	
	@RequestMapping(value = "manualplay/vid", method = RequestMethod.GET)
	public ModelAndView staticalVid(@RequestParam String vid, @RequestParam String plName, @RequestParam String filter) throws JsonProcessingException {
		ModelAndView modelAndView = new ModelAndView();
		Long playlist_id = playlistService.getPlaylistByName(plName);
		Video video = videoService.findVideoByYtId(vid, playlist_id);
		modelAndView.addObject("playlist_id",playlist_id);
		modelAndView.addObject("vid_id",vid);
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		modelAndView.addObject("ws",System.getProperty("WS")==null?"":System.getProperty("WS"));
		modelAndView.addObject("m_title","Rolling Music ("+plName+")");
		modelAndView.addObject("m_url","http://radiorolling.com/music/manualplay/vid?vid="+vid+"&plName="+plName);
		modelAndView.addObject("m_desc",video.getDescription());
		modelAndView.addObject("filter",filter);
		modelAndView.addObject("videoDto",new ObjectMapper().writeValueAsString(mapper.getMapper().map(video, VideoDto.class)));
		modelAndView.addObject("m_image","https://i.ytimg.com/vi/"+vid+"/hqdefault.jpg");
		modelAndView.setViewName("admin/staticalMusic");
		return modelAndView;
	}
	
	@RequestMapping(value = { "autoplay/{name}/","autoplay/{name}" }, method = RequestMethod.GET)
	public ModelAndView ids(@PathVariable String name) {
		ModelAndView modelAndView = new ModelAndView();
		Long playlist_id = playlistService.getPlaylistByName(name);
		modelAndView.addObject("playlist_id",playlist_id);
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		modelAndView.addObject("ws",System.getProperty("WS")==null?"":System.getProperty("WS"));
		modelAndView.addObject("m_title","Rolling Music ("+name+")");
		modelAndView.addObject("m_url","http://radiorolling.com/music/autoplay/"+name);
		modelAndView.addObject("m_desc","Radio Mode");
		modelAndView.addObject("m_image",playlistService.findOne(playlist_id).getImage());
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}
	
	@RequestMapping(value = "random", method = RequestMethod.GET)
	public ModelAndView random() throws JsonProcessingException {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		modelAndView.setViewName("admin/random");
		modelAndView.addObject("m_title","Rolling Random Song");
		modelAndView.addObject("m_url","http://radiorolling.com/music/random");
		modelAndView.addObject("m_desc","Listen to random song from our playlists");
		modelAndView.addObject("m_image","http://radiorolling.com/res/images/2018323936/dd.png");
		List<Video> videos = videoService.findAll();
		Random rand = new Random();
		int randomNum = rand.nextInt(videos.size());
		while (videos.get(randomNum).getPlaylist().getPlaylist_type()!=1) {
			randomNum = rand.nextInt(videos.size());
		}
		modelAndView.addObject("vidd",new ObjectMapper().writeValueAsString(mapper.getMapper().map(videos.get(randomNum), VideoDto.class)));
		return modelAndView;
	}
	
	@RequestMapping(value = "scrolling", method = RequestMethod.GET)
	public ModelAndView scrolling() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		modelAndView.setViewName("admin/scrolling");
		modelAndView.addObject("playlist_id","1298");
		modelAndView.addObject("m_title","ScRolling Music");
		modelAndView.addObject("m_url","http://radiorolling.com/music/scrolling");
		modelAndView.addObject("m_desc","Let it ScRoll");
		modelAndView.addObject("m_image","http://radiorolling.com/res/images/20183252342/muzika.jpg");
		return modelAndView;
	}
	
	
	
	
}
