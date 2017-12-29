package com.nevreme.rolling.controller.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nevreme.rolling.dao.mapping.MapperConfig;
import com.nevreme.rolling.dto.PlaylistDto;
import com.nevreme.rolling.dto.VideoDetailsDto;
import com.nevreme.rolling.dto.VideoDto;
import com.nevreme.rolling.model.Playlist;
import com.nevreme.rolling.model.Video;
import com.nevreme.rolling.model.VideoState;
import com.nevreme.rolling.service.AbstractService;
import com.nevreme.rolling.service.PlaylistService;
import com.nevreme.rolling.service.VideoService;

@Controller
@RequestMapping("/public/api/video/")
public class VideoRestController extends AbstractRestController<Video, VideoDto, Long> {

	@Autowired
	MapperConfig mapper;
	
	@Autowired
	VideoService videoService;
	
	@Autowired
	PlaylistService playlistService;

	@Autowired
	public VideoRestController(AbstractService<Video, Long> repo, VideoDto dto) {
		super(repo, dto);
	}

	@RequestMapping(value = {"/playing","/playing/"})
	@ResponseBody
	public ResponseEntity<Video> findCurrent() {
		return new ResponseEntity<Video>(videoService.findVideoByState(VideoState.PLAYING), HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/newVideo","/newVideo/"})
	@ResponseBody
	public synchronized String insertVideo(@RequestBody VideoDto videoDto, @RequestParam Long pl) {
		Playlist playlist = playlistService.findOne(pl);
		Video video = new Video();
		video.setDescription(videoDto.getDescription());
		video.setPlaylist(playlist);
		video.setDuration(videoDto.getDuration());
		video.setStarted(null);
		video.setState(1);
		video.setId(null);
		video.setYtId(videoDto.getYtId());
		videoService.insertNewVideo(video);
		return "{\"status\":\"ok\"}";
	}
	
	@RequestMapping(value = {"/deleteVideo","/deleteVideo/"})
	@ResponseBody
	public synchronized ResponseEntity<List<Video>> deleteVideo(@RequestBody VideoDto videoDto, @RequestParam Long pl) {
		videoService.delete(videoDto.getId());
		Playlist plist = playlistService.findOne(pl);
		for (Video v : plist.getVideos()) {
			if (v.getId()==videoDto.getId()) {
				plist.getVideos().remove(v);
				break;
			}
		}
		playlistService.save(plist);
		List<Video> videos = videoService.updateAllHigherThan(videoDto.getIndex_num());
		return new ResponseEntity<List<Video>>(videos, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/updatePlaylist","/updatePlaylist/"})
	@ResponseBody
	public synchronized ResponseEntity<VideoDto[]> updateVideos(@RequestBody VideoDto[] videosDto, @RequestParam Long pl) {
		List<Video> vids = new ArrayList<>();
		Playlist p = playlistService.findOne(pl);
		for (VideoDto vdto : videosDto) {
			Video vid = mapper.getMapper().map(vdto, Video.class);
			vid.setPlaylist(p);
			vids.add(vid);
		}
		videoService.updateList(vids);
		return new ResponseEntity<VideoDto[]>(videosDto, HttpStatus.OK);
	}
	
}
