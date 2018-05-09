package com.nevreme.rolling.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nevreme.rolling.dao.sql.SqlBuilder;
import com.nevreme.rolling.model.Video;

@Repository
public class VideoDao extends AbstractDao<Video, Long> {

	@Autowired
	public VideoDao(Video clazz) {
		super(clazz);
		// TODO Auto-generated constructor stub
	}

	@PersistenceContext
	private EntityManager entityManager;

	public Video findVideoByState(int state) {
		TypedQuery<Video> tq = entityManager
				.createQuery(new SqlBuilder().select(Video.class, true).where("state").build(), Video.class);
		tq.setParameter("state", state);
		return tq.getSingleResult();
	}

	public Video findVideoByStateForPlaylist(int state, Long playlist_id) {
		TypedQuery<Video> tq = entityManager.createQuery(new SqlBuilder().select(Video.class, true)
				.wheres(new String[] { "state", "playlist.id" }, new String[] { "state", "playlist_id" }).build(),
				Video.class);
		tq.setParameter("state", state);
		tq.setParameter("playlist_id", playlist_id);
		return tq.getSingleResult();
	}

	public Video findNextVideo(int index) {
		TypedQuery<Video> tq = entityManager
				.createQuery(new SqlBuilder().select(Video.class, true).where("index_num").build(), Video.class);
		tq.setParameter("index_num", ++index);
		if (tq.getResultList() == null || tq.getResultList().size() == 0) {
			tq.setParameter("index_num", 1);
		}
		return tq.getSingleResult();
	}

	public Video findNextVideoForPlaylist(int index, Long playlist_id) {
		TypedQuery<Video> tq = entityManager.createQuery(new SqlBuilder().select(Video.class, true)
				.wheres(new String[] { "index_num", "playlist.id" }, new String[] { "index_num", "playlist_id" })
				.build(), Video.class);
		tq.setParameter("index_num", ++index);
		tq.setParameter("playlist_id", playlist_id);
		if (tq.getResultList() == null || tq.getResultList().size() == 0) {
			tq.setParameter("index_num", 1);
			tq.setParameter("playlist_id", playlist_id);
		}
		return tq.getSingleResult();
	}
	
	public Video findVideoByYtId(String ytId, Long plId) {
		TypedQuery<Video> tq = entityManager
				.createQuery(new SqlBuilder().select(Video.class, true).wheres(new String[] { "ytId", "playlist.id" }, new String[] { "ytId", "playlist_id" }).build(), Video.class);
		tq.setParameter("ytId", ytId);
		tq.setParameter("playlist_id", plId);
		return tq.getSingleResult();
	}

	public void insertNewVideo(Video video) {
		Video latestVideo = entityManager
				.createQuery(
						new SqlBuilder().select(Video.class, true).order("index_num", "DESC")
								.wheres(new String[] { "playlist.id" }, new String[] { "playlist_id" }).build(),
						Video.class)
				.setParameter("playlist_id", video.getPlaylist().getId()).setMaxResults(1).getSingleResult();
		video.setIndex_num(latestVideo.getIndex_num() + 1);
		entityManager.flush();
		entityManager.merge(video);
	}

	public List<Video> updateAllHigherThan(int index, Long playlist) {
		List<Video> videos = entityManager
				.createQuery("SELECT v FROM Video v WHERE v.index_num > (:index) AND v.playlist.id = (:playlist)",
						Video.class)
				.setParameter("index", index).setParameter("playlist", playlist).getResultList();
		for (Video video : videos) {
			video.setIndex_num(video.getIndex_num() - 1);
			entityManager.merge(video);
		}
		return videos;
	}

	public void updateList(List<Video> videos) {
		for (Video video : videos) {
			entityManager.merge(video);
		}
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(Long primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}

	public Video findRecommendedVideoByPlaylist(int playlist_type) {
		TypedQuery<Video> tq = entityManager.createQuery("SELECT v FROM Video v WHERE v.playlist.playlist_type = (:type) AND v.dailyRecommend = (:daily)",
				Video.class);
		tq.setParameter("daily", 1);
		tq.setParameter("type", playlist_type);
		return tq.getSingleResult();
	}

}
