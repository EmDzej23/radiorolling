package com.nevreme.rolling.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nevreme.rolling.dao.mapping.MapperConfig;
import com.nevreme.rolling.dto.AnswerDto;
import com.nevreme.rolling.dto.VideoDto;
import com.nevreme.rolling.model.Answer;
import com.nevreme.rolling.model.Playlist;
import com.nevreme.rolling.model.Video;
import com.nevreme.rolling.service.PlaylistService;
import com.nevreme.rolling.service.VideoService;

@Controller
@RequestMapping("/paper")
public class PaperController {
	
	@Autowired
	PlaylistService playlistService;
	
	@Autowired
	VideoService videoService;
	
	@Autowired
	MapperConfig mapper;

	/*@RequestMapping(value = { "range/", "range" }, method = RequestMethod.GET)
	public ModelAndView getAnswer(@RequestParam Long id, @RequestParam int start, @RequestParam int end, HttpServletRequest request) throws JsonProcessingException {
		ModelAndView modelAndView = new ModelAndView();
		List<Playlist> pls = playlistService.getPLaylstByTypeInRange(7, start, end); 
		modelAndView.addObject("m_title", "Rolling Pitanje: '"+answer.getQuestion().getName()+"'");
		modelAndView.addObject("m_url", "http://radiorolling.com/answer?id="+id);
		modelAndView.addObject("m_desc", answer.getVisitor().getUsername()+": \""+answer.getHtml()+"\"");
		modelAndView.addObject("m_image", answer.getQuestion().getImage());
		modelAndView.addObject("answer", mapper.getMapper().map(answer, AnswerDto.class));
		modelAndView.addObject("name", answer.getQuestion().getName());
		modelAndView.addObject("id", answer.getQuestion().getId());
		modelAndView.setViewName("admin/answer");
		return modelAndView;
	}
	*/
	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public ModelAndView getPaper(@RequestParam Long id, HttpServletRequest request) throws JsonProcessingException {
		ModelAndView modelAndView = new ModelAndView();
		Video video = videoService.findOne(id);
		modelAndView.addObject("m_title", "Rolling Papirići");
		modelAndView.addObject("m_url", "http://radiorolling.com/paper?id="+id);
		modelAndView.addObject("m_desc", "Odlomci iz pesama, citati, izjave...");
		modelAndView.addObject("m_image", video.getYtId());
		modelAndView.addObject("paper", mapper.getMapper().map(video, VideoDto.class));
		modelAndView.addObject("id", video.getId());
		modelAndView.setViewName("admin/paper");
		return modelAndView;
	}
}
