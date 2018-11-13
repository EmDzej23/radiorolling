package com.nevreme.rolling.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nevreme.rolling.model.LinkSuggestion;

@Repository
public class LinkSuggestionDao extends AbstractDao<LinkSuggestion, Long>{

	@Autowired
	public LinkSuggestionDao(LinkSuggestion clazz) {
		super(clazz);
	}
	
	@PersistenceContext
	private EntityManager entityManager;

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
