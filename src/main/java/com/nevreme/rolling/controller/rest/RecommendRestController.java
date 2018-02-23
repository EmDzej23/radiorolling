package com.nevreme.rolling.controller.rest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nevreme.rolling.dto.VideoDto;
import com.nevreme.rolling.model.Video;
import com.nevreme.rolling.service.AbstractService;
import com.nevreme.rolling.service.PlaylistService;
import com.nevreme.rolling.service.VideoService;

@Controller
@RequestMapping("/admin/api/recommend/")
public class RecommendRestController extends AbstractRestController<Video, VideoDto, Long> {

	@Autowired
	VideoService videoService;
	
	@Autowired
	PlaylistService playlistService;


	@Autowired
	public RecommendRestController(AbstractService<Video, Long> repo, VideoDto dto) {
		super(repo, dto);
	}
	
	@CrossOrigin(origins="*")
	@RequestMapping(value = "/add")
	public @ResponseBody String get2PtsShotsForAPlayer(@RequestBody VideoDto videoDto, @RequestParam Long playlist_id) {
		Video video = new Video();
		video.setStarted(new Timestamp(new Date().getTime()));
		video.setDescription(videoDto.getDescription());
		video.setQuote(videoDto.getQuote());
		video.setYtId(videoDto.getYtId());
		video.setPlaylist(playlistService.findOne(playlist_id));
		videoService.insertNewVideo(video);

		return "";
	}
	
	@CrossOrigin(origins="*")
	@RequestMapping(value = "/saveImg")
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile img) throws IOException{
	    return uploadFileHandler(img,img.getOriginalFilename());
	}
	
	public String uploadFileHandler(MultipartFile file, String name) {
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				File dir = new File(com.nevreme.rolling.utils.Constants.IMAGE_PATH);
				if (!dir.exists())
					dir.mkdirs();
				Timestamp date = new Timestamp(new Date().getTime());
				String foldName = (date.getYear()+1900)+""+(date.getMonth()+1)+""+date.getDate()+""+date.getHours()+""+date.getMinutes();
				File dirDate = new File(com.nevreme.rolling.utils.Constants.IMAGE_PATH+File.separator+foldName);
				if (!dirDate.exists())
					dirDate.mkdirs();
				// Create the file on server
				File serverFile = new File(dirDate.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				System.out.println("Server File Location=" + serverFile.getAbsolutePath());

				return System.getProperty("APP_ROOT")+"/res/images/"+foldName+"/"+name;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name + " because the file was empty.";
		}
	}
}


