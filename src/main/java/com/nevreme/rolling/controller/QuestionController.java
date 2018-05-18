package com.nevreme.rolling.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/question")
public class QuestionController {

	@RequestMapping(value = { "/","" }, method = RequestMethod.GET)
	public ModelAndView getQuestion(@RequestParam Long id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/question");
		return modelAndView;
	}
}
