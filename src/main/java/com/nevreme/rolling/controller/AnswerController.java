package com.nevreme.rolling.controller;

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
import com.nevreme.rolling.model.Answer;
import com.nevreme.rolling.service.AnswerService;

@Controller
@RequestMapping("/answer")
public class AnswerController {

	@Autowired
	AnswerService answerService;

	@Autowired
	MapperConfig mapper;

	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public ModelAndView getAnswer(@RequestParam Long id, HttpServletRequest request) throws JsonProcessingException {
		ModelAndView modelAndView = new ModelAndView();
		Answer answer = answerService.findOneEagerly(id);
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
}
