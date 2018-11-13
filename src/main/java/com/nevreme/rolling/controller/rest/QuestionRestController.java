package com.nevreme.rolling.controller.rest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nevreme.rolling.dao.mapping.MapperConfig;
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
	MapperConfig mapper;

	@Autowired
	public QuestionRestController(AbstractService<Question, Long> repo, QuestionDto dto) {
		super(repo, dto);
	}

	@RequestMapping(value = { "add", "add/" })
	@ResponseBody
	public synchronized String add(@RequestBody QuestionDto dto) throws JsonProcessingException {
		Question active = questionService.findOneEagerlyActive();
//		if (active)
		active.setActive(0);
		questionService.save(active);
		Question q = new Question();
		q.setName(dto.getName());
		q.setImage(dto.getImage());
		q.setActive(1);
		questionService.save(q);
		return "Success";
	}

	@CrossOrigin(value="*")
	@RequestMapping(value = { "like/", "like" }, method = RequestMethod.POST)
	@ResponseBody
	public synchronized ResponseEntity<AnswerDto>like(@RequestParam String id, @RequestParam boolean type, HttpServletRequest request) {
		Answer answer = answerService.findOneEagerly(Long.parseLong(id));
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
		if (voteService.findVoteByVisitorAndAnswer(visitor.getId(), Long.parseLong(id)) != null) {
			return new ResponseEntity<AnswerDto>(new AnswerDto(),HttpStatus.BAD_REQUEST);
		}
		if (type)
			answer.setUpVotes(answer.getUpVotes() + 1);
		else
			answer.setDownVotes(answer.getDownVotes() + 1);
		vote.setAnswer(answer);
		vote.setVote(type);
		vote.setVisitor(visitor);
		
		voteService.save(vote);
		answer.getVotes().add(vote);
		return new ResponseEntity<AnswerDto>(mapper.getMapper().map(answer, AnswerDto.class),HttpStatus.OK);
	}
	
	@CrossOrigin(value="*")
	@RequestMapping(value = { "addAnswer", "addAnswer/" })
	@ResponseBody
	public ResponseEntity<List<AnswerDto>> add(@RequestParam Long id, @RequestParam String username, @RequestBody AnswerDto dto, HttpServletRequest request) throws JsonProcessingException {
		Question q = questionService.findOneEagerly(id);
		Answer answer = new Answer();
		answer.setDownVotes(0);
		answer.setUpVotes(0);
		answer.setQuestion(q);
		answer.setHtml(dto.getHtml());
		answer.setReplyAnswer(null);
		answer.setText(dto.getText());
		answer.setDate(new Timestamp(new Date().getTime()));
		Visitor visitor = null;
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals("visitorId")) {
				visitor = visitorService.findByVisitorId(cookie.getValue());
				if (visitor==null) {
					visitor = new Visitor();
					visitor.setVisitorId(cookie.getValue());
					visitor.setUsername(username);
					visitor = visitorService.save(visitor);
					break;
				}
				
			}
		}
		visitor.setUsername(username);
		answer.setVisitor(visitor);
		q.getAnswers().add(answer);
		try {
			questionService.save(q);
		}
		catch(Exception e) {
			return new ResponseEntity<List<AnswerDto>>(new ArrayList<>(),HttpStatus.OK);
		}
		List<AnswerDto> dtos = new ArrayList<>();
		q.getAnswers().forEach(i->dtos.add(mapper.getMapper().map(i, AnswerDto.class)));
		return new ResponseEntity<List<AnswerDto>>(dtos,HttpStatus.OK);
	}
	
	@CrossOrigin(value="*")
	@RequestMapping(value = { "active/ac", "active/ac/" })
	@ResponseBody
	public synchronized ResponseEntity<QuestionDto>active(@RequestParam Long lidl) {
		Question q = questionService.findOneEagerlyActive();
		return new ResponseEntity<QuestionDto>(mapper.getMapper().map(q, QuestionDto.class),HttpStatus.OK);
	}
	
	

}
