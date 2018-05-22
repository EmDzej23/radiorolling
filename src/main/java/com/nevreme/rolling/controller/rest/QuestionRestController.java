package com.nevreme.rolling.controller.rest;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nevreme.rolling.dto.AnswerDto;
import com.nevreme.rolling.dto.QuestionDto;
import com.nevreme.rolling.model.Answer;
import com.nevreme.rolling.model.Question;
import com.nevreme.rolling.model.Visitor;
import com.nevreme.rolling.model.Vote;
import com.nevreme.rolling.service.AbstractService;
import com.nevreme.rolling.service.AnswerService;
import com.nevreme.rolling.service.QuestionService;
import com.nevreme.rolling.service.VisitorService;
import com.nevreme.rolling.service.VoteService;

@Controller
@RequestMapping("/public/api/question/")
public class QuestionRestController extends AbstractRestController<Question, QuestionDto, Long> {
	@Autowired
	private QuestionService questionService;

	@Autowired
	private AnswerService answerService;

	@Autowired
	private VoteService voteService;

	@Autowired
	private VisitorService visitorService;

	@Autowired
	public QuestionRestController(AbstractService<Question, Long> repo, QuestionDto dto) {
		super(repo, dto);
	}

	@RequestMapping(value = { "add", "add/" })
	@ResponseBody
	public synchronized String add(@RequestBody QuestionDto dto) throws JsonProcessingException {
		Question q = new Question();
		q.setName(dto.getName());
		q.setImage(dto.getImage());
		questionService.save(q);
		return "Success";
	}

	@RequestMapping(value = { "like/", "like" }, method = RequestMethod.POST)
	@ResponseBody
	public synchronized String like(@RequestParam Long id, @RequestParam boolean type, HttpServletRequest request) {
		Answer answer = answerService.findOne(id);
		Vote vote = new Vote();
		Visitor visitor = null;
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals("visitorId")) {
				System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
				System.out.println("%%%%%%%%%%%%%% "+cookie.getValue()+" %%%%%%%%%");
				System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
				visitor = visitorService.findByVisitorId(cookie.getValue());
			}
		}
		if (voteService.findVoteByVisitorAndAnswer(visitor.getId(), id) != null) {
			return "NOT OK";
		}
		if (type)
			answer.setUpVotes(answer.getUpVotes() + 1);
		else
			answer.setDownVotes(answer.getDownVotes() + 1);
		vote.setAnswer(answer);
		vote.setVote(type);
		vote.setVisitor(visitor);
		voteService.save(vote);
		return "OK";
	}
	
	@RequestMapping(value = { "addAnswer", "addAnswer/" })
	@ResponseBody
	public synchronized String add(@RequestParam Long id, @RequestParam String username, @RequestBody AnswerDto dto, HttpServletRequest request) throws JsonProcessingException {
		Question q = questionService.findOneEagerly(id);
		Answer answer = new Answer();
		answer.setDownVotes(0);
		answer.setUpVotes(0);
		answer.setQuestion(q);
		answer.setReplyAnswer(null);
		answer.setText(dto.getText());
		answer.setDate(new Timestamp(new Date().getTime()));
		Visitor visitor = null;
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals("visitorId")) {
				visitor = visitorService.findByVisitorId(cookie.getValue());
				visitor.setUsername(username);
			}
		}
		answer.setVisitor(visitor);
		q.getAnswers().add(answer);
		questionService.save(q);
		return "Success";
	}
	
	

}
