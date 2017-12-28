package com.nevreme.rolling.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@RequestMapping(value = { "/playlistEdit","/playlistEdit/" }, method = RequestMethod.GET)
	public ModelAndView addPost() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/playlist");
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		return modelAndView;
	}
	
	@RequestMapping(value = { "/setup","/setup/" }, method = RequestMethod.GET)
	public ModelAndView setup() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		modelAndView.setViewName("admin/playlist");
		return modelAndView;
	}
}
