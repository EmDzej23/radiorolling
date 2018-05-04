package com.nevreme.rolling.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.nevreme.rolling.dao.PlaylistDao;
import com.nevreme.rolling.model.Playlist;
import com.nevreme.rolling.model.Video;

@Service
public class PlaylistService extends AbstractService<Playlist, Long>{

	@Autowired
	PlaylistDao dao;
	
	@Autowired
	public PlaylistService(PlaylistDao iDao) {
		super(iDao);
	}
	
	@Cacheable("sitecache")
	public Long getPlaylistByName(String name) {
		return dao.getPlaylistByName(name);
	}
	
	@Cacheable("sitecache")
	public List<Playlist> getPLaylstByType(int type) {
		return dao.getPLaylstByType(type);
	}
	
	
}
