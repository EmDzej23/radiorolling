package com.nevreme.rolling.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nevreme.rolling.dao.sql.SqlBuilder;
import com.nevreme.rolling.model.Video;
import com.nevreme.rolling.model.VideoState;

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

	public Video findNextVideo(int index) {
		TypedQuery<Video> tq = entityManager
				.createQuery(new SqlBuilder().select(Video.class, true).where("index_num").build(), Video.class);
		tq.setParameter("index_num", ++index);
		if (tq.getResultList() == null || tq.getResultList().size()==0) {
			tq.setParameter("index_num", 1);
		}
		return tq.getSingleResult();
	}
	
	public void insertNewVideo(Video video) {
		Video latestVideo = entityManager.createQuery(new SqlBuilder().select(Video.class, true).order("index_num","DESC").build(), Video.class).setMaxResults(1).getSingleResult();
		video.setIndex_num(latestVideo.getIndex_num()+1);
		entityManager.flush();
		entityManager.merge(video);
	}
	
	public List<Video> updateAllHigherThan(int index) {
		List<Video> videos = entityManager.createQuery("SELECT v FROM Video v WHERE v.index_num > (:index)", Video.class).setParameter("index",index).getResultList();
		for (Video video:videos) {
			video.setIndex_num(video.getIndex_num()-1);
			entityManager.merge(video);
		}
		return videos;
	}
	
	public void updateList(List<Video> videos) {
		for (Video video:videos) {
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

}
