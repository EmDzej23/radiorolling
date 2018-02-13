package com.nevreme.rolling.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nevreme.rolling.dao.PlaylistDao;
import com.nevreme.rolling.model.Playlist;

@Service
public class PlaylistService extends AbstractService<Playlist, Long>{

	@Autowired
	PlaylistDao dao;
	
	@Autowired
	public PlaylistService(PlaylistDao iDao) {
		super(iDao);
	}
	
	public Long getPlaylistByName(String name) {
		return dao.getPlaylistByName(name);
	}
	
	public List<Playlist> getPLaylstByType(int type) {
		return dao.getPLaylstByType(type);
	}
	
}
