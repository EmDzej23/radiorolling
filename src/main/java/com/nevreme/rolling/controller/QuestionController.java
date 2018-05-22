package com.nevreme.rolling.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nevreme.rolling.dao.mapping.MapperConfig;
import com.nevreme.rolling.dto.QuestionDto;
import com.nevreme.rolling.model.Visitor;
import com.nevreme.rolling.service.QuestionService;
import com.nevreme.rolling.service.VisitorService;
import com.nevreme.rolling.service.VoteService;

@Controller
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	QuestionService questionService;
	
	@Autowired
	VoteService voteService;
	
	@Autowired
	VisitorService visitorService;
	
	@Autowired
	MapperConfig mapper;
	
	@RequestMapping(value = { "/","" }, method = RequestMethod.GET)
	public ModelAndView getQuestion(@RequestParam Long id, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		
		Visitor visitor = null;
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals("visitorId")) {
				visitor = visitorService.findByVisitorId(cookie.getValue());
			}
		}
		
		QuestionDto dto =  mapper.getMapper().map(questionService.findOneEagerly(id), QuestionDto.class);
		modelAndView.addObject("dto", dto);
		modelAndView.addObject("username", visitor==null?"":visitor.getUsername());
		modelAndView.setViewName("admin/question");
		return modelAndView;
	}
	
}