package com.nevreme.rolling.dao;

import com.nevreme.rolling.dao.sql.SqlBuilder;
import com.nevreme.rolling.model.Playlist;
import com.nevreme.rolling.model.Question;
import com.nevreme.rolling.model.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        String sql = new SqlBuilder().select(Question.class, true).fetch("answers").build();
        return entityManager.createQuery(sql, Question.class).getSingleResult();
    }

    @Override
    public List<Question> findAllEagerly() {
        String sql = new SqlBuilder().select(Question.class, true).fetch("answers").build();
        return entityManager.createQuery(sql, Question.class).getResultList();
    }

}
