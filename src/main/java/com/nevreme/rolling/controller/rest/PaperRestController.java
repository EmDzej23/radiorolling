package com.nevreme.rolling.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nevreme.rolling.dao.mapping.MapperConfig;
import com.nevreme.rolling.dto.VideoDto;
import com.nevreme.rolling.model.Video;
import com.nevreme.rolling.service.AbstractService;
import com.nevreme.rolling.service.PlaylistService;
import com.nevreme.rolling.service.VideoService;

@Controller
@RequestMapping("/public/api/paper/")
public class PaperRestController extends AbstractRestController<Video, VideoDto, Long> {

	@Autowired
	MapperConfig mapper;

	@Autowired
	VideoService videoService;

	@Autowired
	PlaylistService playlistService;

	@Autowired
	public PaperRestController(AbstractService<Video, Long> repo, VideoDto dto) {
		super(repo, dto);
	}

	@RequestMapping(value = { "/range", "/range/" })
	@CrossOrigin(value="*")
	@ResponseBody
	public ResponseEntity<List<VideoDto>> findInRange(@RequestParam(name = "p") Long p, @RequestParam(name = "start") int start,
			@RequestParam(name = "max") int max) {
		List<VideoDto> dtos = new ArrayList<>();
		for (Video v : videoService.findVideosForPlaylistByRange(p, start, max)) {
			dtos.add(mapper.getMapper().map(v, VideoDto.class));
		}
		return new ResponseEntity<List<VideoDto>>(dtos, HttpStatus.OK);
	}
}
