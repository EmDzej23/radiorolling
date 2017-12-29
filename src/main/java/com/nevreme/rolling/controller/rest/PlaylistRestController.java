package com.nevreme.rolling.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nevreme.rolling.dto.PlaylistDto;
import com.nevreme.rolling.model.Playlist;
import com.nevreme.rolling.service.AbstractService;

@Controller
@RequestMapping("/public/api/playlist/")
public class PlaylistRestController extends AbstractRestController<Playlist, PlaylistDto, Long>{

	@Autowired
	public PlaylistRestController(AbstractService<Playlist, Long> repo, PlaylistDto dto) {
		super(repo, dto);
	}

}
