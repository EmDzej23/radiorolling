package com.nevreme.rolling.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nevreme.rolling.dto.LinkSuggestionDto;
import com.nevreme.rolling.model.LinkSuggestion;
import com.nevreme.rolling.service.AbstractService;
import com.nevreme.rolling.service.LinkSuggestionService;

@Controller
@RequestMapping("/public/api/suggestions/")
public class LinkSuggestionRestController extends AbstractRestController<LinkSuggestion, LinkSuggestionDto, Long> {

	@Autowired
	public LinkSuggestionRestController(AbstractService<LinkSuggestion, Long> repo, LinkSuggestionDto dto) {
		super(repo, dto);
	}

	@Autowired
	LinkSuggestionService linkService;

	@RequestMapping(value = { "/newLink", "/newLink/" })
	@ResponseBody
	public synchronized String insertCard(@RequestBody LinkSuggestionDto linkDto) {
		LinkSuggestion link = new LinkSuggestion();
		link.setLink(linkDto.getLink());
		link.setLinkType(linkDto.getLinkType());
		linkService.save(link);
		return "{\"status\":\"ok\"}";
	}
}
