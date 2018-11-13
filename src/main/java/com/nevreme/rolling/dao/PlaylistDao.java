package com.nevreme.rolling.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nevreme.rolling.dao.sql.SqlBuilder;
import com.nevreme.rolling.model.Playlist;
import com.nevreme.rolling.model.User;

@Repository
public class PlaylistDao extends AbstractDao<Playlist, Long>{

	@Autowired
	public PlaylistDao(Playlist clazz) {
		super(clazz);
	}
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Long getPlaylistByName(String name) {
		TypedQuery<Playlist> tq = entityManager
				.createQuery(new SqlBuilder().select(Playlist.class, true).where("name").build(), Playlist.class);
		tq.setParameter("name", name);
		return tq.getSingleResult().getId();
	}
	
	@Override
	public Playlist findOneEagerly(Long primaryKey) {
		String sql = new SqlBuilder().select(Playlist.class, true).fetch("tags").fetch("videos").where("id").build();
		TypedQuery<Playlist> tq = entityManager.createQuery(sql, Playlist.class);
		tq.setParameter("id", primaryKey);
		return tq.getSingleResult();
	}
	
	public Playlist findOneNoTags(Long primaryKey) {
		String sql = new SqlBuilder().select(Playlist.class, true).fetch("videos").where("id").build();
		TypedQuery<Playlist> tq = entityManager.createQuery(sql, Playlist.class);
		tq.setParameter("id", primaryKey);
		return tq.getSingleResult();
	}
	
	

	@Override
	public Long count() {
		return null;
	}

	@Override
	public boolean exists(Long primaryKey) {
		return false;
	}


	public List<Playlist> getPLaylstByType(int type) {
		TypedQuery<Playlist> tq = entityManager
				.createQuery(new SqlBuilder().select(Playlist.class, true).fetch("videos").fetch("users").where("playlist_type").build(), Playlist.class);
		tq.setParameter("playlist_type", type);
		return tq.getResultList();
	}
	
	public List<Playlist> getPLaylstByTypeInRange(int type, int start, int end) {
		TypedQuery<Playlist> tq = entityManager
				.createQuery(new SqlBuilder().select(Playlist.class, true).fetch("videos").fetch("users").where("playlist_type").build(), Playlist.class);
		tq.setParameter("playlist_type", type);
		tq.setFirstResult(start);
		tq.setMaxResults(end);
		return tq.getResultList();
	}
	
	public List<Playlist> getPLaylstByTypeLazy(int type) {
		TypedQuery<Playlist> tq = entityManager
				.createQuery(new SqlBuilder().select(Playlist.class, true).where("playlist_type").build(), Playlist.class);
		tq.setParameter("playlist_type", type);
		return tq.getResultList();
	}

}
