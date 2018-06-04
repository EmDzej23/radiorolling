package com.nevreme.rolling.dao;

import com.nevreme.rolling.dao.sql.SqlBuilder;
import com.nevreme.rolling.model.Card;
import com.nevreme.rolling.model.Playlist;
import com.nevreme.rolling.model.Question;
import com.nevreme.rolling.model.User;
import com.nevreme.rolling.model.Video;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionDao extends AbstractDao<Question, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public QuestionDao(Question clazz) {
        super(clazz);
    }

    @Override
    public Long count() {
        return null;
    }

    @Override
    public boolean exists(Long primaryKey) {
        return false;
    }

    @Override
    public Question findOneEagerly(Long primaryKey) {
    	TypedQuery<Question> tq = entityManager
				.createQuery(new SqlBuilder().select(Question.class, true).fetch("answers").where("id").build(), Question.class);
    	tq.setParameter("id", primaryKey);
        return tq.getSingleResult();
    }
    
    public Question findOneEagerlyActive() {
    	TypedQuery<Question> tq = entityManager
				.createQuery(new SqlBuilder().select(Question.class, true).fetch("answers").where("active").build(), Question.class);
    	tq.setParameter("active", 1);
        return tq.getSingleResult();
    }
    
    public List<Question> findAllInRange(int start, int max) {
		String sql = new SqlBuilder().select(Question.class, true).order("date", "DESC").build();
		return entityManager.createQuery(sql, Question.class).setFirstResult(start).setMaxResults(max).getResultList();
	}

    @Override
    public List<Question> findAllEagerly() {
        String sql = new SqlBuilder().select(Question.class, true).fetch("answers").build();
        return entityManager.createQuery(sql, Question.class).getResultList();
    }

}
