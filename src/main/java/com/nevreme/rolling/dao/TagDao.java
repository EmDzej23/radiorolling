package com.nevreme.rolling.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nevreme.rolling.model.Tag;


@Repository
public class TagDao extends AbstractDao<Tag, Long> {

	@Autowired
	public TagDao(Tag clazz) {
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
