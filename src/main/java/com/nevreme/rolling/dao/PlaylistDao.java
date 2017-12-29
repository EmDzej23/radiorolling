package com.nevreme.rolling.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nevreme.rolling.dao.sql.SqlBuilder;
import com.nevreme.rolling.model.Playlist;

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
	public Long count() {
		return null;
	}

	@Override
	public boolean exists(Long primaryKey) {
		return false;
	}

}
