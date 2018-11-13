package com.nevreme.rolling.controller.rest;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nevreme.rolling.dao.mapping.MapperConfig;
import com.nevreme.rolling.dto.PlaylistDto;
import com.nevreme.rolling.dto.VideoDto;
import com.nevreme.rolling.model.Playlist;
import com.nevreme.rolling.model.Video;
import com.nevreme.rolling.model.VideoState;
import com.nevreme.rolling.service.AbstractService;
import com.nevreme.rolling.service.PlaylistService;
import com.nevreme.rolling.service.VideoService;
import com.nevreme.rolling.utils.PlaylistUtils;

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
	PlaylistUtils playlistUtils;

	@Autowired
	public VideoRestController(AbstractService<Video, Long> repo, VideoDto dto) {
		super(repo, dto);
	}

	@RequestMapping(value = { "/playing", "/playing/" })
	@ResponseBody
	public ResponseEntity<Video> findCurrent() {
		return new ResponseEntity<Video>(videoService.findVideoByState(VideoState.PLAYING), HttpStatus.OK);
	}

	@RequestMapping(value = { "/newVideo", "/newVideo/" })
	@ResponseBody
	public synchronized String insertVideo(@RequestBody VideoDto videoDto, @RequestParam Long pl) {
		Playlist playlist = playlistService.findOne(pl);
		Video video = new Video();
		video.setDescription(videoDto.getDescription());
		video.setPlaylist(playlist);
		video.setDuration(videoDto.getDuration());
		video.setStarted(null);
		video.setState(1);
		video.setOffset(videoDto.getOffset());
		video.setId(null);
		video.setYtId(videoDto.getYtId());
		video.setQuote(videoDto.getQuote());
		if (playlist.getVideos().size() == 0) {
			video.setIndex_num(1);
			video.setState(0);
			videoService.save(video);
			return "{\"status\":\"ok\"}";
		} else {
			videoService.insertNewVideo(video);
			return "{\"status\":\"ok\"}";
		}
	}

	@RequestMapping(value = { "/startPlaylist", "/startPlaylist/" })
	@ResponseBody
	public synchronized String startPlaylist(@RequestParam Long pl) {
		Video video = videoService.findVideoByStateForPlaylist(VideoState.PLAYING, pl);
		Playlist playlist = playlistService.findOne(pl);
		long time = 0;
		if (!playlist.isStart()) {
			playlist.setStart(true);
			playlistService.save(playlist);
			playlistUtils.executeJob(1000 * (video.getDuration() - time), pl);
			video.setStarted(new Timestamp(new Date().getTime()));
			videoService.save(video);
		}
		return "{\"status\":\"ok\"}";
	}

	@RequestMapping(value = { "/startPlaylists", "/startPlaylists/" })
	@ResponseBody
	public synchronized String startPlaylist() {
		List<Playlist> playlists = playlistService.getPLaylstByType(1);
		for (Playlist playlist : playlists) {
			if (!playlist.isStart()) {
				playlist.setStart(true);
				playlistService.save(playlist);
				Video video = videoService.findVideoByStateForPlaylist(VideoState.PLAYING, playlist.getId());
				playlistUtils.executeJob(1000 * (video.getDuration()), playlist.getId());
				video.setStarted(new Timestamp(new Date().getTime()));
				videoService.save(video);
			}
		}
		return "{\"status\":\"ok\"}";
	}

	@CrossOrigin("*")
	@Cacheable("sitecache")
	@RequestMapping(value = { "/getImageFromUrl", "/getImageFromUrl/" })
	@ResponseBody
	public synchronized String getImageFromUrl(@RequestParam String imageUrl) throws IOException {
		URL url = new URL(imageUrl);
		BufferedInputStream bis = new BufferedInputStream(url.openConnection().getInputStream());
		byte[] buffer = new byte[1024];
		int read = 0;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((read = bis.read(buffer, 0, buffer.length)) != -1) {
			baos.write(buffer, 0, read);
		}
		baos.flush();
		StringBuilder sb = new StringBuilder();
		sb.append("data:image/png;base64,");
		sb.append(new String(java.util.Base64.getEncoder().encode(baos.toByteArray())));
		return sb.toString();
	}

	@RequestMapping(value = { "/deleteVideo", "/deleteVideo/" })
	@ResponseBody
	public synchronized ResponseEntity<Set<Video>> deleteVideo(@RequestBody VideoDto videoDto, @RequestParam Long pl) {

		Playlist plist = playlistService.findOne(pl);
		for (Video v : plist.getVideos()) {
			if (v.getId().longValue() == videoDto.getId().longValue()) {
				System.out.println("******************************************************");
				System.out.println("***********************DELETE*************************");
				System.out.println("******************************************************");
				plist.getVideos().remove(v);
				break;
			}
		}
		playlistService.save(plist);
		// videoService.delete(videoDto.getId());
		videoService.updateAllHigherThan(videoDto.getIndex_num(), pl);
		return new ResponseEntity<Set<Video>>(new HashSet<Video>(), HttpStatus.OK);
	}

	@CrossOrigin(value = "*")
	@RequestMapping(value = "/getRecommendedVideo")
	@ResponseBody
	public synchronized ResponseEntity<VideoDto> getRecommendedVideo(@RequestParam int type) {
		Video video = videoService.getRecommendedVideo(type);
		VideoDto dto = mapper.getMapper().map(video, VideoDto.class);
		return new ResponseEntity<VideoDto>(dto, HttpStatus.OK);
	}

	@RequestMapping(value = { "/updatePlaylist", "/updatePlaylist/" })
	@ResponseBody
	public synchronized ResponseEntity<VideoDto[]> updateVideos(@RequestBody VideoDto[] videosDto,
			@RequestParam Long pl) {
		List<Video> vids = new ArrayList<>();
		Playlist p = playlistService.findOne(pl);
		for (VideoDto vdto : videosDto) {
			Video vid = videoService.findOne(vdto.getId());
			vid.setIndex_num(vdto.getIndex_num());
			// mapper.getMapper().map(vdto, Video.class);
			vid.setPlaylist(p);
			vids.add(vid);
		}
		videoService.updateList(vids);
		return new ResponseEntity<VideoDto[]>(videosDto, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = { "/addNewPlaylist", "/addNewPlaylist/" })
	@ResponseBody
	public synchronized ResponseEntity<PlaylistDto> addPlaylist(@RequestBody PlaylistDto playlistDto) {
		Playlist playlist = new Playlist();
		playlist.setImage(playlistDto.getImage());
		playlist.setName(playlistDto.getName());
		playlist.setStart(playlistDto.isStart());
		playlistService.save(playlist);
		return new ResponseEntity<PlaylistDto>(playlistDto, HttpStatus.OK);
	}

}
