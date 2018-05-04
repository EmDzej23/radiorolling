package com.nevreme.rolling.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nevreme.rolling.model.HtmlTemplate;
import com.nevreme.rolling.model.Playlist;

@Repository
public class HtmlTemplateDao extends AbstractDao<HtmlTemplate, Long>{

	@Autowired
	public HtmlTemplateDao(HtmlTemplate clazz) {
		super(clazz);
	}
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Long count() {
		return null;
	}

	@Override
	public boolean exists(Long primaryKey) {
		return false;
	}

}
