package com.nevreme.rolling.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nevreme.rolling.dao.sql.SqlBuilder;
import com.nevreme.rolling.model.Card;

@Repository
public class CardDao extends AbstractDao<Card, Long> {

	@Autowired
	public CardDao(Card clazz) {
		super(clazz);
	}
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Card> findAllInRange(int start, int max) {
		String sql = new SqlBuilder().select(Card.class, true).order("date_entered", "DESC").build();
		return entityManager.createQuery(sql, Card.class).setFirstResult(start).setMaxResults(max).getResultList();
	}

	@Override
	public boolean exists(Long primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}

}
