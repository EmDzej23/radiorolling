package com.nevreme.rolling.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nevreme.rolling.dao.mapping.MapperConfig;
import com.nevreme.rolling.dto.PlaylistDto;
import com.nevreme.rolling.interceptors.MyUserPrincipal;
import com.nevreme.rolling.model.Playlist;
import com.nevreme.rolling.service.AbstractService;
import com.nevreme.rolling.service.PlaylistService;
import com.nevreme.rolling.service.VideoService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/public/api/playlist/")
public class PlaylistRestController extends AbstractRestController<Playlist, PlaylistDto, Long> {

	@Autowired
	MapperConfig mapper;

	@Autowired
	VideoService videoService;

	@Autowired
	PlaylistService playlistService;

	@Autowired
	public PlaylistRestController(AbstractService<Playlist, Long> repo, PlaylistDto dto) {
		super(repo, dto);
	}

	@RequestMapping(value = { "t", "t/" })
	@ResponseBody
	public synchronized String getPlaylist(@RequestParam int type) throws JsonProcessingException {
		List<PlaylistDto> plDto = new ArrayList<>();
		List<Playlist> pls = playlistService.getPLaylstByType(type);
		for (Playlist p : pls) {
			plDto.add(mapper.getMapper().map(p, PlaylistDto.class));
		}
		return new ObjectMapper()
				.writeValueAsString(plDto);
	}

	@RequestMapping(value = {"user", "user/"})
	@ResponseBody
	public synchronized String getUserPlaylist(Authentication authentication) throws JsonProcessingException {
		List<PlaylistDto> plDto = new ArrayList<>();

		MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
		if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
			return new ObjectMapper().writeValueAsString(listAllEagerly());
		} else {
			List<Playlist> pls = principal.getUser().getPlaylists();
			for (Playlist p : pls) {
				plDto.add(mapper.getMapper().map(p, PlaylistDto.class));
			}
		}

		return new ObjectMapper().writeValueAsString(plDto);
	}

}
