package com.nevreme.rolling.controller.rest;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nevreme.rolling.dto.CardDto;
import com.nevreme.rolling.model.Card;
import com.nevreme.rolling.service.AbstractService;
import com.nevreme.rolling.service.CardService;

@Controller
@RequestMapping("/public/api/card/")
public class CardRestController extends AbstractRestController<Card, CardDto, Long> {

	@Autowired
	public CardRestController(AbstractService<Card, Long> repo, CardDto dto) {
		super(repo, dto);
	}

	@Autowired
	CardService cardService;

	@RequestMapping(value = { "/newCard", "/newCard/" })
	@ResponseBody
	public synchronized String insertCard(@RequestBody CardDto cardDto) {
		Card card = new Card();
		card.setDescription(cardDto.getDescription());
		card.setTitle(cardDto.getTitle());
		card.setPlaylistName(cardDto.getPlaylistName());
		card.setImg(cardDto.getImg());
		card.setLink(cardDto.getLink());
		card.setDate_entered(new Timestamp(new Date().getTime()));
		cardService.save(card);
		return "{\"status\":\"ok\"}";
	}

	@RequestMapping(value = { "/range", "/range/" })
	@ResponseBody
	public ResponseEntity<List<Card>> findCurrent(@RequestParam(name = "start") int start,
			@RequestParam(name = "max") int max) {
		return new ResponseEntity<List<Card>>(cardService.findAllInRange(start, max), HttpStatus.OK);
	}

}
