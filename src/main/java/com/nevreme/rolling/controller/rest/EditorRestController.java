package com.nevreme.rolling.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nevreme.rolling.dto.UserDto;
import com.nevreme.rolling.interceptors.MyUserPrincipal;
import com.nevreme.rolling.model.User;
import com.nevreme.rolling.service.AbstractService;
import com.nevreme.rolling.service.PlaylistService;
import com.nevreme.rolling.service.UserService;

@Controller
@RequestMapping("/editor/api/")
public class EditorRestController extends AbstractRestController<User, UserDto, Long> {

	@Autowired
	PlaylistService playlistService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	public EditorRestController(AbstractService<User, Long> repo, UserDto dto) {
		super(repo, dto);
	}
	
	@CrossOrigin(origins="*")
	@RequestMapping(value = "/add", method=RequestMethod.POST)
	public @ResponseBody String addPlaylist(@RequestBody Long[] playlists, @RequestParam(name="userId") Long userId) {
		User user = userService.findOneEagerly(userId);
		for (Long id : playlists) {
			user.getPlaylists().add(playlistService.findOne(id));
		}
		userService.save(user);
		return "";
	}

}
