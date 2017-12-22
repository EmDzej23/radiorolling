package com.nevreme.rolling.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nevreme.rolling.dao.VideoDao;
import com.nevreme.rolling.model.Video;

@Service("videoService")
public class VideoService extends AbstractService<Video, Long> {
	@Autowired
	VideoDao dao;
	
	@Autowired
	public VideoService(VideoDao iDao) {
		super(iDao);
	}
	
	public Video findVideoByState(int playing) {
		return dao.findVideoByState(playing);
	}
	
	public Video findNextVideo(int index) {
		return dao.findNextVideo(index);
	}
	
	public void insertNewVideo(Video video) {
		dao.insertNewVideo(video);
	}
	
	public List<Video> updateAllHigherThan(int index) {
		return dao.updateAllHigherThan(index);
	}
	
	public void updateList(List<Video> videos) {
		dao.updateList(videos);
	}
}
