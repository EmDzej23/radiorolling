package com.nevreme.rolling.controller.rest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nevreme.rolling.dto.UserDto;
import com.nevreme.rolling.model.User;
import com.nevreme.rolling.service.AbstractService;
import com.nevreme.rolling.utils.Constants;

@Controller
@RequestMapping("/admin/api/user/")
public class UserRestController extends AbstractRestController<User, UserDto, Long> {

	@Autowired
	public UserRestController(AbstractService<User, Long> repo, UserDto dto) {
		super(repo, dto);
	}
	
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
				File dir = new File(Constants.IMAGE_PATH);
				if (!dir.exists())
					dir.mkdirs();
				Timestamp date = new Timestamp(new Date().getTime());
				String foldName = date.getYear()+""+(date.getMonth()+1)+""+date.getDate()+""+date.getMinutes();
				File dirDate = new File(Constants.IMAGE_PATH+File.separator+foldName);
				if (!dirDate.exists())
					dirDate.mkdirs();
				// Create the file on server
				File serverFile = new File(dirDate.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				System.out.println("Server File Location=" + serverFile.getAbsolutePath());

				return Constants.APP_ROOT+"/res/images/"+foldName+"/"+name;
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name + " because the file was empty.";
		}
	}

	
	
	
	

}
