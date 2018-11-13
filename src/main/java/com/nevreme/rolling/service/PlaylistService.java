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
	
	@Cacheable("sitecache")
	public List<Playlist> getPLaylstByTypeInRange(int type, int start, int end) {
		return dao.getPLaylstByTypeInRange(type, start, end);
	}
	
	@Cacheable("sitecache")
	public List<Playlist> getPLaylstByTypeLazy(int type) {
		return dao.getPLaylstByTypeLazy(type);
	}
	
	@Cacheable("sitecache")
	public Playlist findOneNoTags(long primaryKey) {
		return dao.findOneNoTags(primaryKey);
	}
	
}
