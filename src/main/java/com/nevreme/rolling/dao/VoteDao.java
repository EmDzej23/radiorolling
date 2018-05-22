package com.nevreme.rolling.dao;

import com.nevreme.rolling.dao.sql.SqlBuilder;
import com.nevreme.rolling.model.Video;
import com.nevreme.rolling.model.Vote;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VoteDao extends AbstractDao<Vote, Long> {
    @Autowired
    public VoteDao(Vote clazz) {
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
    
    public Vote findVoteByVisitorAndAnswer(Long visitorId,Long answerId) {
    	TypedQuery<Vote> tq = entityManager.createQuery(new SqlBuilder().select(Vote.class, true)
				.wheres(new String[] { "visitor.id", "answer.id" }, new String[] { "visitorId", "answerId" })
				.build(), Vote.class);
		tq.setParameter("visitorId", visitorId);
		tq.setParameter("answerId", answerId);
		return tq.getResultList().isEmpty()?null:tq.getSingleResult();
    }
}
